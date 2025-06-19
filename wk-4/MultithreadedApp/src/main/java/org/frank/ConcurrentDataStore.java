package org.frank;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Thread-safe data store using concurrent collections
 * Demonstrates lock-free data structures for high-performance concurrent access
 * Sample: Multiple threads updating counters and storing results simultaneously
 */
public class ConcurrentDataStore {
    // Thread-safe map allowing concurrent read/write operations
    // No explicit synchronization needed - internally handles thread safety
    private final ConcurrentHashMap<String, AtomicLong> counters = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, String> results = new ConcurrentHashMap<>();
    
    /**
     * Atomically increment counter for given key
     * Sample usage: increment("task-1") from multiple threads safely
     */
    public void incrementCounter(String key) {
        // computeIfAbsent: thread-safe operation to create counter if not exists
        // AtomicLong: provides lock-free atomic increment operations
        counters.computeIfAbsent(key, k -> new AtomicLong(0)).incrementAndGet();
    }
    
    /**
     * Get current counter value (thread-safe read)
     * Sample output: returns current count without blocking other operations
     */
    public long getCounter(String key) {
        AtomicLong counter = counters.get(key);
        return counter != null ? counter.get() : 0;
    }
    
    /**
     * Store processing result (thread-safe write)
     * Sample: storeResult("task-1", "Processed 50000 items")
     */
    public void storeResult(String key, String result) {
        // put() operation is thread-safe in ConcurrentHashMap
        // Multiple threads can write different keys simultaneously
        results.put(key, result);
    }
    
    /**
     * Retrieve result (thread-safe read)
     * Sample output: "Task task-1: Processed 50000 items, Operations: 1250"
     */
    public String getResult(String key) {
        String result = results.get(key);
        long operations = getCounter(key);
        return String.format("Task %s: %s, Operations: %d", key, result, operations);
    }
    
    /**
     * Get all stored results for summary
     * Sample: displays all concurrent processing results
     */
    public void printAllResults() {
        System.out.println("\n=== Concurrent Processing Results ===");
        results.keySet().forEach(key -> System.out.println(getResult(key)));
    }
}