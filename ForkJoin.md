- ForkJoinPool = executor service used to run the ForkJoinTask
- ForkJoinTask = Abstract class - represents a task that can be executed in a ForkJoinPool
	- Implementations
		- RecursiveTask<V>: return value
		- RecursiveAction: no return value
- related
	- invoke(task) = starts the tasks and waits for the results (synchronous)
	- execute(task) = Starts the task asynchronously (no result expected).
	- submit(task) = Starts the task asynchronously and returns a Future


```text

ForkJoinPool
  |
  |-- invokes --> ForkJoinTask (abstract)
                    |
                    |-- RecursiveAction  (no return)
                    |-- RecursiveTask<V> (returns V)
```




























- Implementing Recursive Task

	- override the compute

```java

class SumTask extends RecursiveTask<Long> {

    @Override
    protected Long compute() {
        return 0L;
    }
}

```

-  Full Example

```java
public class App {
    public static void main(String[] args) {
        long[] numbers = new long[100];
        for(int i=0; i<numbers.length; i++){numbers[i] = i;}
        SumTask task = new SumTask(numbers, 0, numbers.length);
        ForkJoinPool fjp = new ForkJoinPool();
        long totalResult = fjp.invoke(task);
        System.out.printf("Total result: %d\n", totalResult);
    }
}
class SumTask extends RecursiveTask<Long> {
    private static final int THRESHOLD = 10;
    private long[] numbers;
    private int start, end;
    public SumTask(long[] numbers, int start, int end){
        this.numbers = numbers;
        this.start = start;
        this.end = end;
    }
    @Override
    protected Long compute() {
        // if it is less than our specified threshold, then, it should go ahead and execute
        if(end - start <= THRESHOLD){
            long sum = 0;
            for(int i=start; i<end; i++){sum += numbers[i];}
            return sum;
        }
        // else - we break down the task
        else {
            int middle = (start + end) / 2;
            SumTask left = new SumTask(numbers, start, middle);
            SumTask right = new SumTask(numbers, middle, end);
            left.fork(); // Submits the task to be run asynchronously ; execute the left asynchronously
            long rightResult = right.compute();  // Core method of your logic.
            long leftResult = left.join(); // Waits for the result of the forked task.
            return leftResult + rightResult;
        }
    }
}

```