
package com.iqmsoft.spring.batch.activemq.metrics;

/**
 * Interface for metrics update.
 */
public interface MetricUpdater<T> {
    void updateWith(T item);
}
