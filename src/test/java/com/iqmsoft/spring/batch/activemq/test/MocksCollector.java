package com.iqmsoft.spring.batch.activemq.test;

import org.mockito.internal.listeners.CollectCreatedMocks;
import org.mockito.internal.progress.MockingProgress;
import org.mockito.internal.progress.ThreadSafeMockingProgress;

import java.util.LinkedList;
import java.util.List;

/**
 * Helper test class to collect all mocks.
 */
public class MocksCollector {
    private final List<Object> createdMocks;

    public MocksCollector() {
        createdMocks = new LinkedList<>();
        final MockingProgress progress = new ThreadSafeMockingProgress();
        progress.setListener(new CollectCreatedMocks(createdMocks));
    }

    public Object[] getMocks() {
        return createdMocks.toArray();
    }
}