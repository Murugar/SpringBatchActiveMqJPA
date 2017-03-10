
package com.iqmsoft.spring.batch.activemq.batch;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.core.io.ClassPathResource;

import com.iqmsoft.spring.batch.activemq.batch.TicketReaderFactory;
import com.iqmsoft.spring.batch.activemq.model.Ticket;

import java.sql.Date;
import java.time.LocalDate;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class TicketReaderFactoryTest {

    private TicketReaderFactory factory = new TicketReaderFactory();

    @Test
    public void testCreateReaderAndReadCorrectData() throws Exception {
        // given
        final ExecutionContext executionContext = mock(ExecutionContext.class);
        final LocalDate date = LocalDate.of(2015, 12, 20);

        // when
        final ItemStreamReader<Ticket> reader = factory.createReader(new ClassPathResource("tickets.csv"));
        final Ticket ticket;

        try {
            reader.open(executionContext);
            ticket = reader.read();
        } finally {
            reader.close();
        }

        // then
        assertThat(ticket, notNullValue());
        assertThat(ticket.getTag(), equalTo("Ticket_0"));
        assertThat(ticket.getDate(), equalTo(Date.valueOf(date)));
        assertThat(ticket.getContent(), equalTo("Test ticket"));
    }

    @Test(expected = FlatFileParseException.class)
    public void testCreateReaderAndFailToRead() throws Exception {
        // given
        final ExecutionContext executionContext = mock(ExecutionContext.class);

        // when
        final ItemStreamReader<Ticket> reader = factory.createReader(new ClassPathResource("tickets-fail.csv"));

        try {
            reader.open(executionContext);
            System.out.println(reader.read());
        } finally {
            reader.close();
        }

        // then
    }


}