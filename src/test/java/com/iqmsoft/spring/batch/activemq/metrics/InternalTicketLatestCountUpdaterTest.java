
package com.iqmsoft.spring.batch.activemq.metrics;

import org.junit.Before;
import org.junit.Test;

import com.iqmsoft.spring.batch.activemq.metrics.InternalTicketLatestCountUpdater;
import com.iqmsoft.spring.batch.activemq.model.Ticket;
import com.iqmsoft.spring.batch.activemq.model.enums.TicketType;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class InternalTicketLatestCountUpdaterTest {
    private InternalTicketLatestCountUpdater updater;

    @Before
    public void setup() {
        updater = new InternalTicketLatestCountUpdater();
    }

    @Test
    public void testGetInitialMetricValue() throws Exception {
        // given
        // when
        // then
        assertEquals(0L, updater.getMetricValue());
    }

    @Test
    public void testGetEvaluateMetricValue() throws Exception {
        // given

        // when
        for (int i = 1; i < 200; i++) {
            final LocalDate date = LocalDate.of(2015, 1 + i / 28, i % 28 + 1);
            final Ticket ticket = new Ticket();
            ticket.setType(i % 2 == 0 ? TicketType.EXTERNAL : TicketType.INTERNAL);
            ticket.setDate(Date.valueOf(date));
            updater.updateWith(ticket);
        }

        // then
        assertEquals(50L, updater.getMetricValue());
    }

    @Test
    public void testGetMetricName() throws Exception {
        // given
        // when
        // then
        assertEquals("Latest internal ticket count", updater.getMetricName());
    }
}