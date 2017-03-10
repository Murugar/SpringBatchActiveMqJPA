
 

package com.iqmsoft.spring.batch.activemq.batch.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.iqmsoft.spring.batch.activemq.model.Ticket;
import com.iqmsoft.spring.batch.activemq.repository.TicketRepository;

import java.util.Optional;

/**
 * Import {@link Ticket} item processor.
 */
public class TicketUpdateItemProcessor implements ItemProcessor<Ticket, Ticket> {
    private static final Logger log = LoggerFactory.getLogger(TicketUpdateItemProcessor.class);

    private final TicketRepository ticketRepository;

    public TicketUpdateItemProcessor(final TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public Ticket process(final Ticket item) throws Exception {
        log.debug("processing : {}", item);
        final Ticket ticket = Optional
                .ofNullable(ticketRepository.findByTag(item.getTag()))
                .map(i -> {
                    i.setContent(item.getContent());
                    return i;
                })
                .orElse(item);
        log.debug("using : {}", ticket);
        return ticket;
    }
}
