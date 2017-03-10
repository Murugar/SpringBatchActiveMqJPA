
 

package com.iqmsoft.spring.batch.activemq.application.configuration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.jms.JmsItemWriter;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.iqmsoft.spring.batch.activemq.batch.FilterItemReaderAdapter;
import com.iqmsoft.spring.batch.activemq.batch.TicketImportJobExecutionListener;
import com.iqmsoft.spring.batch.activemq.batch.TicketReaderFactory;
import com.iqmsoft.spring.batch.activemq.batch.processor.MetricItemProcessor;
import com.iqmsoft.spring.batch.activemq.batch.processor.TicketUpdateItemProcessor;
import com.iqmsoft.spring.batch.activemq.function.TicketPredicate;
import com.iqmsoft.spring.batch.activemq.metrics.InternalTicketLatestCountUpdater;
import com.iqmsoft.spring.batch.activemq.metrics.MetricProvider;
import com.iqmsoft.spring.batch.activemq.metrics.TicketCounterUpdater;
import com.iqmsoft.spring.batch.activemq.model.Ticket;
import com.iqmsoft.spring.batch.activemq.model.enums.TicketType;
import com.iqmsoft.spring.batch.activemq.repository.TicketRepository;

import static com.iqmsoft.spring.batch.activemq.function.TicketPredicate.hasType;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Configuration
    static class MapBatchConfigurer extends DefaultBatchConfigurer {
        @Override
        protected JobRepository createJobRepository() throws Exception {
            MapJobRepositoryFactoryBean factory = new MapJobRepositoryFactoryBean();
            factory.afterPropertiesSet();
            return factory.getObject();
        }
    }

    @Bean
    @Qualifier("jpaTransactionManagerForBatch")
    public PlatformTransactionManager jpaTransactionManager() {
        return new JpaTransactionManager();
    }

    @Bean
    public TicketReaderFactory ticketReaderFactory() {
        return new TicketReaderFactory();
    }


    @Bean
    @StepScope
    public ItemStreamReader<Ticket> ticketReader(final TicketReaderFactory ticketReaderFactory,
                                                 final @Value("file:#{jobParameters['input.file.name']}") Resource resource) {
        return new FilterItemReaderAdapter<>(ticketReaderFactory.createReader(resource), TicketPredicate.hasTodayDate());
    }

    @Bean
    public ItemWriter<Ticket> ticketWriter(final TicketRepository repository,
                                           final JmsTemplate jmsTemplate) {
        final CompositeItemWriter<Ticket> writer = new CompositeItemWriter<>();
        final RepositoryItemWriter<Ticket> repositoryItemWriter = new RepositoryItemWriter<>();
        repositoryItemWriter.setRepository(repository);
        repositoryItemWriter.setMethodName("saveAndFlush");
        final JmsItemWriter<Ticket> jmsItemWriter = new JmsItemWriter();
        jmsItemWriter.setJmsTemplate(jmsTemplate);
        final List<ItemWriter<? super Ticket>> delegates = Stream.of(
                repositoryItemWriter,
                jmsItemWriter)
                .collect(Collectors.toList());
        writer.setDelegates(delegates);
        return writer;
    }

    @Bean
    public TicketCounterUpdater sumEvaluator() {
        return new TicketCounterUpdater();
    }

    @Bean
    public InternalTicketLatestCountUpdater internalTicketLatestCountUpdater() {
        return new InternalTicketLatestCountUpdater();
    }

    @Bean
    public ItemProcessor<Ticket, Ticket> importTicketProcessor(final TicketRepository ticketRepository,
                                                               final @Value("${ticket.metrics.count}") String countTicketType,
                                                               final TicketCounterUpdater ticketCounterUpdater,
                                                               final InternalTicketLatestCountUpdater internalTicketLatestCountUpdater) {
        final List<ItemProcessor<Ticket, Ticket>> delegates = Stream.of(
                new TicketUpdateItemProcessor(ticketRepository),
                new MetricItemProcessor<>(hasType(TicketType.valueOf(countTicketType)), ticketCounterUpdater),
                new MetricItemProcessor<>(ticket -> true, internalTicketLatestCountUpdater))
                .collect(Collectors.toList());
        final CompositeItemProcessor<Ticket, Ticket> processor = new CompositeItemProcessor<>();
        processor.setDelegates(delegates);
        return processor;
    }

    @Bean
    public TicketImportJobExecutionListener ticketJobExecutionListener(final TicketCounterUpdater ticketCounterUpdater,
                                                                       final InternalTicketLatestCountUpdater internalTicketLatestCountUpdater) {
        final List<MetricProvider> metrics = Stream.of(
                ticketCounterUpdater,
                internalTicketLatestCountUpdater)
                .collect(Collectors.toList());
        return new TicketImportJobExecutionListener(metrics);
    }

    @Bean
    public Job importTicketsJob(final JobBuilderFactory jobs,
                                final Step importTicketStep,
                                final TicketImportJobExecutionListener ticketImportJobExecutionListener) {
        return jobs.get("importTicketsJob")
                .incrementer(new RunIdIncrementer())
                .listener(ticketImportJobExecutionListener)
                .flow(importTicketStep)
                .end()
                .build();
    }

    @Bean
    public Step importTicketStep(final StepBuilderFactory stepBuilderFactory,
                                 @Qualifier("jpaTransactionManagerForBatch")
                                 final PlatformTransactionManager jpaTransactionManager,
                                 final @Value("${ticket.chunk.size}") int chunkSize,
                                 final ItemReader<Ticket> ticketReader,
                                 final ItemWriter<Ticket> ticketWriter,
                                 final ItemProcessor<Ticket, Ticket> importTicketProcessor) {
        return stepBuilderFactory.get("importTicketStep")
                .<Ticket, Ticket>chunk(chunkSize)
                .reader(ticketReader)
                .processor(importTicketProcessor)
                .writer(ticketWriter)
                .transactionManager(jpaTransactionManager)
                .build();
    }
}
