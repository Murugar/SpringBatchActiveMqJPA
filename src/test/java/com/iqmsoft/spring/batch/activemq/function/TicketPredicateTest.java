
package com.iqmsoft.spring.batch.activemq.function;

import org.junit.Test;

import com.iqmsoft.spring.batch.activemq.function.TicketPredicate;
import com.iqmsoft.spring.batch.activemq.model.Ticket;
import com.iqmsoft.spring.batch.activemq.model.enums.TicketType;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TicketPredicateTest {

    @Test
    public void testHasType() throws Exception {
        // given
        final Ticket ticket = new Ticket();
        ticket.setType(TicketType.EXTERNAL);

        // when

        // then
        assertTrue(TicketPredicate.hasType(TicketType.EXTERNAL).test(ticket));
    }

    @Test
    public void testDoesNotHaveAName() throws Exception {
        // given
        final Ticket ticket = new Ticket();
        ticket.setType(TicketType.INTERNAL);

        // when

        // then
        assertFalse(TicketPredicate.hasType(TicketType.EXTERNAL).test(ticket));
    }

    @Test
    public void testHasTodayDate() throws Exception {
        // given
        final Ticket ticket = new Ticket();
        final LocalDate date = LocalDate.now();
        ticket.setDate(Date.valueOf(date));

        // when

        // then
        assertTrue(TicketPredicate.hasTodayDate().test(ticket));
    }

    @Test
    public void testHasNotATodayDate() throws Exception {
        // given
        final Ticket ticket = new Ticket();
        final LocalDate date = LocalDate.of(2015, 12, 19);
        ticket.setDate(Date.valueOf(date));

        // when

        // then
        assertFalse(TicketPredicate.hasTodayDate().test(ticket));
    }
}