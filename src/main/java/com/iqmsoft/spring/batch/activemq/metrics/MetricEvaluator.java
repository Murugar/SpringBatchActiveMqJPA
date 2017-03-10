
package com.iqmsoft.spring.batch.activemq.metrics;

/**
 * Interface for evaluation of metric value.
 */
public interface MetricEvaluator<T> {
    T evaluate();
}
