

package com.iqmsoft.spring.batch.activemq.batch.processor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.iqmsoft.spring.batch.activemq.batch.processor.MetricItemProcessor;
import com.iqmsoft.spring.batch.activemq.metrics.MetricUpdater;

import java.util.function.Predicate;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MetricItemProcessorTest {

    @Mock
    private Predicate<Object> predicate;

    @Mock
    private MetricUpdater<Object> metricUpdater;

    private MetricItemProcessor<Object> processor;

    @Before
    public void setup() {
        processor = new MetricItemProcessor(predicate, metricUpdater);
    }

    @Test
    public void testProcessWithoutMetricUpdate() throws Exception {
        // given
        final Object item = mock(Object.class);
        when(predicate.test(item)).thenReturn(false);

        // when
        final Object processedItem = processor.process(item);

        // then
        assertThat(processedItem, is(item));
        verify(metricUpdater, never()).updateWith(item);
    }

    @Test
    public void testProcessWithMetricUpdate() throws Exception {
        // given
        final Object item = mock(Object.class);
        when(predicate.test(item)).thenReturn(true);

        // when
        final Object processedItem = processor.process(item);

        // then
        assertThat(processedItem, is(item));
        verify(metricUpdater).updateWith(item);
    }
}