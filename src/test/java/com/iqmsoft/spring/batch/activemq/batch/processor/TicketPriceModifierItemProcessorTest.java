
 
package com.iqmsoft.spring.batch.activemq.batch.processor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.iqmsoft.spring.batch.activemq.batch.processor.TicketUpdateItemProcessor;
import com.iqmsoft.spring.batch.activemq.model.Ticket;
import com.iqmsoft.spring.batch.activemq.repository.TicketRepository;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TicketPriceModifierItemProcessorTest {

    public static final String CONTENT = "content";
    public static final String TAG = "tag";
    @Mock
    private TicketRepository ticketRepository;

    private TicketUpdateItemProcessor processor;

    @Before
    public void setup() {
        processor = new TicketUpdateItemProcessor(ticketRepository);
    }

    @Test
    public void testProcessWithoutUpdate() throws Exception {
        // given
        final Ticket ticket = new Ticket();
        ticket.setContent(CONTENT);
        ticket.setTag(TAG);
        when(ticketRepository.findByTag(TAG)).thenReturn(null);

        // when
        final Ticket processedTicket = processor.process(ticket);

        // then
        assertEquals(CONTENT, processedTicket.getContent());
    }

    @Test
    public void testProcessWithUpdate() throws Exception {
        // given
        final Ticket ticket = new Ticket();
        ticket.setContent(CONTENT);
        ticket.setTag(TAG);
        final Ticket ticketOld = new Ticket();
        ticketOld.setContent("old content");
        when(ticketRepository.findByTag(TAG)).thenReturn(ticketOld);

        // when
        final Ticket processedTicket = processor.process(ticket);

        // then
        assertEquals(processedTicket, ticketOld);
        assertEquals(CONTENT, processedTicket.getContent());
    }
}