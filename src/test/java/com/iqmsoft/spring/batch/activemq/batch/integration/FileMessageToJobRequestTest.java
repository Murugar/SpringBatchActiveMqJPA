
package com.iqmsoft.spring.batch.activemq.batch.integration;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.batch.core.Job;
import org.springframework.batch.integration.launch.JobLaunchRequest;
import org.springframework.messaging.Message;

import com.iqmsoft.spring.batch.activemq.batch.integration.FileMessageToJobRequest;

import java.io.File;
import java.time.Clock;
import java.time.Instant;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FileMessageToJobRequestTest {
    public static final String PARAMETER = "PARAMETER";
    public static final long TIMESTAMP = 123456L;
    public static final String PATH = "PATH";

    private FileMessageToJobRequest transformer;

    @Mock
    private Job job;
    @Mock
    private Clock clock;

    @Before
    public void setup() {
        transformer = new FileMessageToJobRequest(job, clock, PARAMETER);
    }

    @Test
    public void testMessageFileToJobLaunchRequestConversion() {
        // given
        final Message<File> message = mock(Message.class);
        final File file = mock(File.class);
        when(file.getAbsolutePath()).thenReturn(PATH);
        when(message.getPayload()).thenReturn(file);
        final Instant timestamp = Instant.ofEpochSecond(TIMESTAMP);
        when(clock.instant()).thenReturn(timestamp);

        // when
        final JobLaunchRequest jobLaunchRequest = transformer.toRequest(message);

        // then
        assertThat(jobLaunchRequest.getJob(), is(job));
        assertThat(jobLaunchRequest.getJobParameters().getLong(FileMessageToJobRequest.TIMESTAMP_PARAMETER), equalTo(TIMESTAMP));
        assertThat(jobLaunchRequest.getJobParameters().getString(PARAMETER), equalTo(PATH));
    }

}