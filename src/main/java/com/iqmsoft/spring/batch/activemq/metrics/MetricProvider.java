

package com.iqmsoft.spring.batch.activemq.metrics;

/**
 * Metric value provider.
 */
public interface MetricProvider {
    Object getMetricValue();

    String getMetricName();
}
