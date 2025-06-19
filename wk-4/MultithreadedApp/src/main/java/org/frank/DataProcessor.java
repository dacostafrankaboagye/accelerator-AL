package org.frank;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * Processes numerical data concurrently using Callable interface
 * Sample output: "Task 1 processed 1000 items, sum: 500500 [Thread: pool-1-thread-1]"
 */
public class DataProcessor implements Callable<String> {
    private final int taskId;
    private final List<Integer> data;
    
    public DataProcessor(int taskId, List<Integer> data) {
        this.taskId = taskId;
        this.data = data;
    }
    
    @Override
    public String call() throws Exception {
        // Simulate real-world processing delays (network I/O, disk operations, complex calculations)
        // Random delay between 100-500ms to show concurrent execution
        TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(100, 500));
        
        // Process data: calculate sum (represents any computational work)
        // For dataset [1,2,3...n], sum = n*(n+1)/2
        long sum = data.stream().mapToLong(Integer::longValue).sum();
        
        // Return result showing task completion with thread identification
        // Demonstrates which thread handled which task for concurrency visualization
        return String.format("Task %d processed %d items, sum: %d [Thread: %s]", 
                taskId, data.size(), sum, Thread.currentThread().getName());
    }
}