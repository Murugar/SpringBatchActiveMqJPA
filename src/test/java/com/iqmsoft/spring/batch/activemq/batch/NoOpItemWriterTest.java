
package com.iqmsoft.spring.batch.activemq.batch;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.iqmsoft.spring.batch.activemq.batch.NoOpItemWriter;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class NoOpItemWriterTest {

    private NoOpItemWriter<Object> writer;

    @Before
    public void setup() {
        writer = new NoOpItemWriter<>();
    }

    @Test
    public void testWriteDoNothing() throws Exception {
        // given
        List<?> list = mock(List.class);
        // when

        writer.write(list);
        // then

        verifyNoMoreInteractions(list);
    }
}