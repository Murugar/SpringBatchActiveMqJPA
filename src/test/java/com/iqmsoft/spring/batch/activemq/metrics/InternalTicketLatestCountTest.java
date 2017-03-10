
package com.iqmsoft.spring.batch.activemq.metrics;

import org.junit.Before;
import org.junit.Test;

import com.iqmsoft.spring.batch.activemq.metrics.InternalTicketLatestCount;
import com.iqmsoft.spring.batch.activemq.model.enums.TicketType;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class InternalTicketLatestCountTest {
    private InternalTicketLatestCount internalTicketLatestCount;

    @Before
    public void setup() {
        internalTicketLatestCount = new InternalTicketLatestCount();
    }

    @Test
    public void testGetInitialValue() throws Exception {
        // given
        // when
        // then
        assertEquals(0L, (long) internalTicketLatestCount.evaluate());
    }

    @Test
    public void testGetEvaluatedValue() throws Exception {
        // given
        // when
        for (int i = 1; i < 20; i++) {
            final LocalDate date = LocalDate.of(2015, 1, i);
            internalTicketLatestCount.add(Date.valueOf(date), i % 2 == 0 ? TicketType.EXTERNAL : TicketType.INTERNAL);
        }

        // then
        assertEquals(10L, (long) internalTicketLatestCount.evaluate());
    }
}