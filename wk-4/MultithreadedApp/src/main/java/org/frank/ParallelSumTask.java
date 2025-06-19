package org.frank;

import java.util.concurrent.RecursiveTask;

/**
 * Fork/Join task for parallel array sum calculation
 * Demonstrates divide-and-conquer approach with work-stealing
 * Sample: Array[100000] splits into chunks of 10000, processed across multiple cores
 */
public class ParallelSumTask extends RecursiveTask<Long> {
    private static final int THRESHOLD = 10000; // Split threshold for optimal performance
    private final int[] array;
    private final int start;
    private final int end;
    
    public ParallelSumTask(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }
    
    public ParallelSumTask(int[] array) {
        this(array, 0, array.length);
    }
    
    @Override
    protected Long compute() {
        int length = end - start;
        
        // Base case: process small chunks sequentially
        // Avoids overhead of creating too many small tasks
        if (length <= THRESHOLD) {
            return computeSequentially();
        }
        
        // Divide: split task into two subtasks
        // Fork/Join framework automatically distributes across available cores
        int mid = start + length / 2;
        ParallelSumTask leftTask = new ParallelSumTask(array, start, mid);
        ParallelSumTask rightTask = new ParallelSumTask(array, mid, end);
        
        // Fork left task to run in parallel (non-blocking)
        // Work-stealing: idle threads can steal this task
        leftTask.fork();
        
        // Compute right task in current thread (blocking)
        // Efficient use of current thread while left task runs in parallel
        Long rightResult = rightTask.compute();
        
        // Join: wait for left task completion and get result
        // Combines results from parallel execution
        Long leftResult = leftTask.join();
        
        // Conquer: merge results from both subtasks
        return leftResult + rightResult;
    }
    
    /**
     * Sequential computation for small chunks
     * Sample output: processes 10000 elements in single thread
     */
    private Long computeSequentially() {
        long sum = 0;
        for (int i = start; i < end; i++) {
            sum += array[i]; // Simple sum, could be any CPU-intensive operation
        }
        return sum;
    }
}