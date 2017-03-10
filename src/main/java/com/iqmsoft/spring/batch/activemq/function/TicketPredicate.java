
package com.iqmsoft.spring.batch.activemq.function;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.function.Predicate;

import com.iqmsoft.spring.batch.activemq.model.Ticket;
import com.iqmsoft.spring.batch.activemq.model.enums.TicketType;

/**
 * Ticket predicates.
 */
public class TicketPredicate {

    /**
     * Check {@link Ticket} type.
     *
     * @param ticketType Ticket type.
     * @return True when {@link Ticket} type match provided ticket type.
     */
    public static Predicate<Ticket> hasType(final TicketType ticketType) {
        return ticket -> ticket.getType().equals(ticketType);
    }

    /**
     * Check if {@link Ticket} date is a Today date.
     *
     * @return True when {@link Ticket} is Today date.
     */
    public static Predicate<Ticket> hasTodayDate() {
        final LocalDate today = LocalDate.now();
        return ticket -> {
            final LocalDate date = Instant.ofEpochMilli(ticket.getDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
            return date.isEqual(today);
        };
    }
}
