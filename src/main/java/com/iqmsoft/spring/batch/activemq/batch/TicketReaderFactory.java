


package com.iqmsoft.spring.batch.activemq.batch;

import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.core.io.Resource;

import com.iqmsoft.spring.batch.activemq.model.Ticket;

import java.beans.PropertyEditor;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * {@link Ticket} data {@link ItemStreamReader} factory.
 */
public class TicketReaderFactory {

    public static final String[] TICKET_FILE_CSV_FIELDS = new String[]{"tag", "date", "content", "type"};
    public static final String DATE_FORMAT = "dd-MM-yyyy";

    public ItemStreamReader<Ticket> createReader(final Resource source) {

        final FlatFileItemReader<Ticket> reader = new FlatFileItemReader<>();
        reader.setResource(source);
        final DefaultLineMapper<Ticket> lineMapper = new DefaultLineMapper<>();
        final DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames(TICKET_FILE_CSV_FIELDS);
        lineMapper.setLineTokenizer(lineTokenizer);
        final BeanWrapperFieldSetMapper<Ticket> fieldMapper = new BeanWrapperFieldSetMapper<>();
        fieldMapper.setTargetType(Ticket.class);
        final DateFormat df = new SimpleDateFormat(DATE_FORMAT);
        final Map<Class, PropertyEditor> customEditors = Stream.of(
                new AbstractMap.SimpleEntry<>(Date.class, new CustomDateEditor(df, false)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        fieldMapper.setCustomEditors(customEditors);
        lineMapper.setFieldSetMapper(fieldMapper);
        reader.setLineMapper(lineMapper);
        return reader;
    }
}
