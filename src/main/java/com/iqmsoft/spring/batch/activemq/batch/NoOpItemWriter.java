
package com.iqmsoft.spring.batch.activemq.batch;

import org.springframework.batch.item.support.AbstractItemStreamItemWriter;
import org.springframework.batch.item.ItemStreamWriter;

import java.util.List;

/**
 * Dummy {@link ItemStreamWriter}
 *
 * @param <T> Item type
 */
public class NoOpItemWriter<T> extends AbstractItemStreamItemWriter<T> {

    @Override
    public void write(List<? extends T> items) throws Exception {
        // Do nothing
    }
}
