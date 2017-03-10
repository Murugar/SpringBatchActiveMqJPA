
package com.iqmsoft.spring.batch.activemq.metrics;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicReference;

import com.iqmsoft.spring.batch.activemq.model.Ticket;

/**
 * Calculate number of tickets in a thread safe way.
 */
public class TicketCounterUpdater implements MetricUpdater<Ticket>, MetricProvider {

    private AtomicReference<BigDecimal> ticketCount = new AtomicReference<>(BigDecimal.ZERO);

    @Override
    public void updateWith(final Ticket ticket) {
        ticketCount.updateAndGet(value -> value.add(BigDecimal.ONE));
    }

    @Override
    public Object getMetricValue() {
        return ticketCount.get();
    }

    @Override
    public String getMetricName() {
        return "Count";
    }


}
