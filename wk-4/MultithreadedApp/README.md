# MultithreadedApp

A multithreaded Java application that processes large datasets in parallel.

## Project Structure
```
src/
├── main/java/org/frank/
│   ├── MultithreadedProcessor.java    # Main application
│   ├── DataProcessor.java             # Data processing logic
│   └── util/                          # Utility classes
└── test/java/org/frank/               # Unit tests
```

## Exercises

### Exercise 3.1: Memory Management Fundamentals

#### Garbage Collection in Java

**How Garbage Collection Works:**
- **Automatic Memory Management**: Java's GC automatically reclaims memory occupied by objects no longer reachable by the application
- **Generational Collection**: Objects are categorized into generations (Young, Old, Permanent/Metaspace)
  - **Young Generation**: New objects, frequent GC cycles (Eden space + Survivor spaces)
  - **Old Generation**: Long-lived objects, less frequent GC cycles
  - **Metaspace** (Java 8+): Class metadata storage
- **Mark and Sweep**: GC marks reachable objects, then sweeps unreachable ones
- **Different GC Algorithms**: G1GC, Parallel GC, ZGC, etc., each optimized for different scenarios

**Common Pitfalls to Avoid:**

1. **Memory Leaks**:
   - **Static Collections**: Objects stored in static collections never get GC'd
   - **Event Listeners**: Unregistered listeners hold references to objects
   - **Inner Classes**: Non-static inner classes hold implicit references to outer class
   - **ThreadLocal Variables**: Not properly cleaned up after thread completion

2. **Performance Issues**:
   - **Excessive Object Creation**: Creating unnecessary objects in loops
   - **Large Object Retention**: Holding references to large objects longer than needed
   - **Improper Collection Sizing**: Using default sizes for collections that grow significantly

3. **Multithreading-Specific Issues**:
   - **Thread-Local Memory Leaks**: ThreadLocal not cleaned up properly
   - **Shared Mutable State**: Can lead to memory visibility issues and retention

**Best Practices for Multithreaded Applications**:
- Use thread-safe collections or proper synchronization
- Clean up ThreadLocal variables with `remove()`
- Avoid sharing large mutable objects between threads
- Use object pools for frequently created/destroyed objects
- Monitor GC performance with tools like JVisualVM or GCEasy

### Exercise 3.2: Threading Basics

**Objective**: Create a simple multithreaded application that performs data processing tasks concurrently using threads.

**Sample Dataset**: 
The application generates six numerical datasets of varying sizes (1,000 to 6,000 integers each), representing different workloads that might be encountered in real-world scenarios like processing customer records, financial transactions, or sensor data.

**How It Works**:
- **Thread Pool Management**: Uses a fixed thread pool of 4 worker threads to handle concurrent processing
- **Task Distribution**: Each dataset is assigned to a separate processing task that can run independently
- **Concurrent Execution**: Multiple datasets are processed simultaneously, with threads picking up available tasks from the queue
- **Processing Simulation**: Each task performs mathematical operations (sum calculations) while simulating realistic processing delays
- **Result Aggregation**: Results from all concurrent tasks are collected and displayed, showing which thread handled each dataset

**Key Threading Concepts Demonstrated**:
- **ExecutorService**: Manages thread lifecycle and task scheduling automatically
- **Callable Interface**: Enables tasks to return results and handle exceptions properly
- **Future Objects**: Provide thread-safe mechanism to retrieve results from concurrent operations
- **Thread Safety**: Ensures data integrity when multiple threads access shared resources
- **Resource Management**: Proper shutdown procedures prevent thread leaks and ensure clean application termination

**Real-World Applications**: This pattern is commonly used for batch processing, data analysis, web scraping, file processing, and any scenario where independent tasks can benefit from parallel execution.

### Exercise 3.3: Advanced Concurrency

**Objective**: Explore advanced concurrency features in Java including Fork/Join Framework and Concurrent Collections.

**Fork/Join Framework**:
The application implements a divide-and-conquer approach using ForkJoinPool to process large datasets by recursively splitting them into smaller chunks. This framework is particularly effective for CPU-intensive tasks that can be broken down into independent subtasks, automatically balancing work across available processor cores.

**Sample Dataset**: 
Large numerical arrays (100,000+ elements) that benefit from parallel processing. The framework recursively divides arrays until reaching a threshold size, then processes chunks in parallel and merges results.

**Concurrent Collections**:
Demonstrates thread-safe data structures like ConcurrentHashMap that allow multiple threads to read and write simultaneously without explicit synchronization. This is crucial for scenarios where multiple threads need to share and update common data structures.

**How It Works**:
- **Work Stealing**: Fork/Join framework uses work-stealing algorithm where idle threads steal tasks from busy threads' queues
- **Recursive Decomposition**: Large tasks are recursively split into smaller subtasks until reaching manageable size
- **Parallel Execution**: Subtasks execute concurrently across multiple CPU cores
- **Result Merging**: Results from parallel subtasks are combined to produce final output
- **Thread-Safe Collections**: Multiple threads safely access shared data without race conditions or data corruption

**Key Advanced Concepts Demonstrated**:
- **ForkJoinTask**: Base class for tasks that can be split and executed in parallel
- **RecursiveTask**: Specific implementation for tasks that return results
- **Work-Stealing Queues**: Efficient task distribution mechanism
- **ConcurrentHashMap**: Lock-free hash table for concurrent access
- **Atomic Operations**: Thread-safe operations without explicit locking

**Performance Benefits**: Fork/Join framework can significantly improve performance for CPU-bound tasks on multi-core systems, while concurrent collections eliminate bottlenecks caused by synchronized access to shared data structures.
