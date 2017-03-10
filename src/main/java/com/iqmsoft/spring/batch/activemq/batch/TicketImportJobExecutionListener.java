

package com.iqmsoft.spring.batch.activemq.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;

import com.iqmsoft.spring.batch.activemq.metrics.MetricProvider;

import java.util.List;

/**
 * Ticket import job execution stage monitor.
 */
public class TicketImportJobExecutionListener extends JobExecutionListenerSupport {
    private static final Logger log = LoggerFactory.getLogger(TicketImportJobExecutionListener.class);

    private final List<MetricProvider> metrics;

    public TicketImportJobExecutionListener(final List<MetricProvider> metrics) {
        this.metrics = metrics;
    }

    @Override
    public void beforeJob(final JobExecution jobExecution) {
        log.debug("Ticket import job started.");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.debug("Ticket import job completed.");
            metrics.stream()
                    .map(metric -> String.format("%s = %s", metric.getMetricName(), metric.getMetricValue()))
                    .forEach(log::debug);
        }
    }
}
