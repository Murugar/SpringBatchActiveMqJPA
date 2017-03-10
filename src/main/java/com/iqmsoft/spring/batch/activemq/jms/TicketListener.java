
package com.iqmsoft.spring.batch.activemq.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.iqmsoft.spring.batch.activemq.model.Ticket;
/**
 * This is Ticket listener to simulate external tool receiving processed tickets.
 */
@Component
public class TicketListener {
    private static final Logger log = LoggerFactory.getLogger(TicketListener.class);

    @JmsListener(destination = "${ticket.queue}" )
    public void onNewTicket(final Ticket ticket) {
        log.debug("onNewTicket : {}", ticket);
    }
}
