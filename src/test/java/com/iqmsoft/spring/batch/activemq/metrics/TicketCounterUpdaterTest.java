package com.iqmsoft.spring.batch.activemq.metrics;

import org.junit.Before;
import org.junit.Test;

import com.iqmsoft.spring.batch.activemq.metrics.TicketCounterUpdater;
import com.iqmsoft.spring.batch.activemq.model.Ticket;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class TicketCounterUpdaterTest {

    private TicketCounterUpdater updater;

    @Before
    public void setup() {
        updater = new TicketCounterUpdater();
    }

    @Test
    public void testGetInitialMetricValue() throws Exception {
        // given
        // when
        // then
        assertEquals(BigDecimal.ZERO, updater.getMetricValue());
    }

    @Test
    public void testGetEvaluateMetricValue() throws Exception {
        // given
        final Ticket ticket = new Ticket();

        // when
        updater.updateWith(ticket);
        updater.updateWith(ticket);

        // then
        assertEquals(BigDecimal.valueOf(2), updater.getMetricValue());
    }

    @Test
    public void testGetMetricName() throws Exception {
        // given
        // when
        // then
        assertEquals("Count", updater.getMetricName());
    }
}