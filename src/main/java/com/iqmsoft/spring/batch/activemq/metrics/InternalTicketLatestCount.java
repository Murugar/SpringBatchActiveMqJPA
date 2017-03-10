
package com.iqmsoft.spring.batch.activemq.metrics;


import java.util.Date;
import java.util.NavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

import com.iqmsoft.spring.batch.activemq.model.enums.TicketType;

/**
 * Latest internal tickets counter. Evaluates how many of latest 100 tickets is coming from internal source.
 */
public class InternalTicketLatestCount implements MetricEvaluator<Long> {
    private static final int MAX_ITEMS = 100;

    private final NavigableMap<Date, TicketType> latest = new ConcurrentSkipListMap<>();

    public InternalTicketLatestCount add(final Date date, final TicketType ticketType) {
        if (latest.size() < MAX_ITEMS) {
            latest.put(date, ticketType);
        } else if (latest.firstKey().before(date)) {
            latest.remove(latest.firstKey());
            latest.put(date, ticketType);
        }

        return this;
    }

    @Override
    public Long evaluate() {
        return latest.values().stream()
                .filter(TicketType.INTERNAL::equals)
                .map(ticket -> 1L)
                .reduce(0L, Math::addExact);
    }
}
