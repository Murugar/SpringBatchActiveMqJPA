
package com.iqmsoft.spring.batch.activemq.batch;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;

import com.iqmsoft.spring.batch.activemq.batch.TicketImportJobExecutionListener;
import com.iqmsoft.spring.batch.activemq.metrics.MetricProvider;

import java.util.Arrays;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TicketJobExecutionListenerTest {

    @Mock
    private MetricProvider metricProvider = mock(MetricProvider.class);

    private TicketImportJobExecutionListener listener;

    @Before
    public void setup() {
        listener = new TicketImportJobExecutionListener(Arrays.asList(metricProvider));
    }

    @Test
    public void testBeforeJob() throws Exception {
        // given
        final JobExecution jobExecution = mock(JobExecution.class);

        // when
        listener.beforeJob(jobExecution);

        // then
        verifyNoMoreInteractions(metricProvider);
    }

    @Test
    public void testAfterJobCompleted() throws Exception {
        // given
        final JobExecution jobExecution = mock(JobExecution.class);
        when(jobExecution.getStatus()).thenReturn(BatchStatus.COMPLETED);

        // when
        listener.afterJob(jobExecution);

        // then
        verify(metricProvider).getMetricName();
        verify(metricProvider).getMetricValue();
    }

    @Test
    public void testAfterJobNotCompleted() throws Exception {
        // given
        final JobExecution jobExecution = mock(JobExecution.class);
        when(jobExecution.getStatus()).thenReturn(BatchStatus.STARTED);

        // when
        listener.afterJob(jobExecution);

        // then
        verifyNoMoreInteractions(metricProvider);
    }
}