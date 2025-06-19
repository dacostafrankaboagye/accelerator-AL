package org.frank;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Main application demonstrating multithreaded data processing
 * Sample output shows tasks completing in different order due to concurrent execution
 */
public class MultithreadedProcessor {
    
    public static void main(String[] args) {
        System.out.println("Multithreaded Data Processor - Exercise Series");
        
        // Exercise 3.2: Threading Basics
        runThreadingBasics();
        
        // Exercise 3.3: Advanced Concurrency
        runAdvancedConcurrency();
    }
    
    private static void runThreadingBasics() {
        System.out.println("\n=== Exercise 3.2: Threading Basics ===");
        
        // Create thread pool with 4 worker threads
        // More tasks (6) than threads (4) demonstrates task queuing and thread reuse
        ExecutorService executor = Executors.newFixedThreadPool(4);
        List<Future<String>> futures = new ArrayList<>();
        
        try {
            // Submit 6 concurrent tasks with increasing dataset sizes
            // Tasks will be distributed across 4 threads, some threads will handle multiple tasks
            for (int i = 1; i <= 6; i++) {
                List<Integer> dataset = generateDataset(i * 1000); // 1K, 2K, 3K, 4K, 5K, 6K items
                DataProcessor processor = new DataProcessor(i, dataset);
                futures.add(executor.submit(processor)); // Non-blocking submission
            }
            
            // Collect results in submission order (blocking calls)
            // Sample output order may vary due to different processing times:
            // Task 1 processed 1000 items, sum: 500500 [Thread: pool-1-thread-2]
            // Task 3 processed 3000 items, sum: 4501500 [Thread: pool-1-thread-1]
            // Task 2 processed 2000 items, sum: 2001000 [Thread: pool-1-thread-4]
            for (Future<String> future : futures) {
                System.out.println(future.get()); // Blocks until task completes
            }
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            // Proper cleanup: shutdown executor and wait for tasks to complete
            executor.shutdown();
            try {
                if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                    executor.shutdownNow(); // Force shutdown if tasks don't complete
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }
    
    private static void runAdvancedConcurrency() {
        System.out.println("\n=== Exercise 3.3: Advanced Concurrency ===");
        
        // Fork/Join Framework demonstration
        demonstrateForkJoin();
        
        // Concurrent Collections demonstration
        demonstrateConcurrentCollections();
    }
    
    /**
     * Demonstrates Fork/Join framework for parallel processing
     * Sample output shows performance comparison between sequential and parallel execution
     */
    private static void demonstrateForkJoin() {
        System.out.println("\n--- Fork/Join Framework ---");
        
        // Create large dataset for meaningful parallel processing
        // Array size chosen to show clear performance benefits
        int[] largeArray = new int[100_000];
        for (int i = 0; i < largeArray.length; i++) {
            largeArray[i] = i + 1; // Values 1 to 100,000
        }
        
        // Sequential processing for comparison
        long startTime = System.currentTimeMillis();
        long sequentialSum = 0;
        for (int value : largeArray) {
            sequentialSum += value;
        }
        long sequentialTime = System.currentTimeMillis() - startTime;
        
        // Parallel processing using Fork/Join
        // Uses common ForkJoinPool (typically matches CPU core count)
        startTime = System.currentTimeMillis();
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        ParallelSumTask task = new ParallelSumTask(largeArray);
        Long parallelSum = forkJoinPool.invoke(task); // Blocks until completion
        long parallelTime = System.currentTimeMillis() - startTime;
        
        // Results comparison - both sums should be identical
        // Performance difference shows Fork/Join efficiency on multi-core systems
        System.out.printf("Array size: %d elements%n", largeArray.length);
        System.out.printf("Sequential sum: %d (Time: %d ms)%n", sequentialSum, sequentialTime);
        System.out.printf("Parallel sum: %d (Time: %d ms)%n", parallelSum, parallelTime);
        System.out.printf("Speedup: %.2fx%n", (double) sequentialTime / parallelTime);
    }
    
    /**
     * Demonstrates concurrent collections with multiple threads
     * Sample output shows thread-safe operations without explicit synchronization
     */
    private static void demonstrateConcurrentCollections() {
        System.out.println("\n--- Concurrent Collections ---");
        
        ConcurrentDataStore dataStore = new ConcurrentDataStore();
        ExecutorService executor = Executors.newFixedThreadPool(4);
        
        try {
            // Submit multiple tasks that concurrently access shared data store
            // Each task simulates processing work while updating shared counters
            for (int i = 1; i <= 4; i++) {
                final int taskId = i;
                executor.submit(() -> {
                    String taskKey = "task-" + taskId;
                    
                    // Simulate processing work with concurrent data updates
                    for (int j = 0; j < 1000; j++) {
                        dataStore.incrementCounter(taskKey); // Thread-safe increment
                        
                        // Simulate some processing work
                        if (j % 100 == 0) {
                            try {
                                Thread.sleep(1); // Brief pause to simulate work
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                                return;
                            }
                        }
                    }
                    
                    // Store final result - thread-safe write operation
                    dataStore.storeResult(taskKey, 
                        String.format("Processed %d operations [Thread: %s]", 
                            1000, Thread.currentThread().getName()));
                });
            }
            
            // Wait for all tasks to complete
            executor.shutdown();
            executor.awaitTermination(10, TimeUnit.SECONDS);
            
            // Display results - all operations completed safely without data corruption
            dataStore.printAllResults();
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Generates sequential integer dataset [1, 2, 3, ..., size]
     * Simulates real datasets like customer IDs, transaction amounts, sensor readings
     */
    private static List<Integer> generateDataset(int size) {
        List<Integer> data = new ArrayList<>(size);
        for (int i = 1; i <= size; i++) {
            data.add(i);
        }
        return data; // Sum formula: size * (size + 1) / 2
    }
}