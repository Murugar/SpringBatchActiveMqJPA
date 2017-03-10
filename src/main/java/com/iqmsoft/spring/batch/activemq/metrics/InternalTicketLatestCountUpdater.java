
package com.iqmsoft.spring.batch.activemq.metrics;

import java.util.concurrent.atomic.AtomicReference;

import com.iqmsoft.spring.batch.activemq.model.Ticket;

/**
 * Calculate how many of the latest tickets is coming from internal source in a thread safe way.
 */
public class InternalTicketLatestCountUpdater implements MetricUpdater<Ticket>, MetricProvider {

    private AtomicReference<InternalTicketLatestCount> internalTicketLatestCount = new AtomicReference<>(new InternalTicketLatestCount());

    @Override
    public void updateWith(final Ticket ticket) {
        internalTicketLatestCount.updateAndGet(value -> value.add(ticket.getDate(), ticket.getType()));
    }

    @Override
    public Object getMetricValue() {
        return internalTicketLatestCount.get().evaluate();
    }

    @Override
    public String getMetricName() {
        return "Latest internal ticket count";
    }


}
