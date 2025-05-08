# RDD Operations Example

This example demonstrates the basic operations on Resilient Distributed Datasets (RDDs), which are the fundamental data structure in Apache Spark.

## What are RDDs?

RDDs (Resilient Distributed Datasets) are the primary abstraction in Spark. An RDD is an immutable, partitioned collection of elements that can be operated on in parallel. RDDs can be created from Hadoop InputFormats (such as HDFS files) or by transforming other RDDs.

Key characteristics of RDDs:
- **Resilient**: Fault-tolerant with the ability to recompute missing or damaged partitions
- **Distributed**: Data is distributed across multiple nodes in a cluster
- **Dataset**: A collection of partitioned data with primitive values or values of defined types

## RDD Operations

There are two types of operations you can perform on RDDs:

1. **Transformations**: Create a new RDD from an existing one (e.g., `map`, `filter`, `flatMap`)
2. **Actions**: Return a value to the driver program after running a computation on the RDD (e.g., `count`, `collect`, `reduce`)

Transformations are lazy, meaning they don't compute their results right away. Instead, they remember the transformations applied to the base dataset. Transformations are only computed when an action requires a result to be returned to the driver program.

## Example Code Walkthrough

The `BasicRDDExample.java` file demonstrates the following operations:

### 1. Setting up Spark Context

```java
SparkConf conf = new SparkConf()
        .setAppName("Basic RDD Example")
        .setMaster("local[*]");
JavaSparkContext sc = new JavaSparkContext(conf);
```

This creates a Spark configuration and a JavaSparkContext, which is the entry point for Spark functionality.

### 2. Creating an RDD

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
JavaRDD<Integer> numbersRDD = sc.parallelize(numbers);
```

Here, we create an RDD from a Java collection using the `parallelize` method.

### 3. Transformations

#### Map Transformation

```java
JavaRDD<Integer> squaredRDD = numbersRDD.map(
        new Function<Integer, Integer>() {
            @Override
            public Integer call(Integer x) {
                return x * x;
            }
        }
);

// Using Java 8 lambda (alternative to the above)
// JavaRDD<Integer> squaredRDD = numbersRDD.map(x -> x * x);
```

The `map` transformation applies a function to each element in the RDD and returns a new RDD with the results.

#### Filter Transformation

```java
JavaRDD<Integer> evenRDD = numbersRDD.filter(
        new Function<Integer, Boolean>() {
            @Override
            public Boolean call(Integer x) {
                return x % 2 == 0;
            }
        }
);

// Using Java 8 lambda (alternative to the above)
// JavaRDD<Integer> evenRDD = numbersRDD.filter(x -> x % 2 == 0);
```

The `filter` transformation returns a new RDD containing only the elements that satisfy a predicate function.

### 4. Actions

#### Count Action

```java
long count = numbersRDD.count();
```

The `count` action returns the number of elements in the RDD.

#### Reduce Action

```java
Integer sum = numbersRDD.reduce(
        new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer a, Integer b) {
                return a + b;
            }
        }
);

// Using Java 8 lambda (alternative to the above)
// Integer sum = numbersRDD.reduce((a, b) -> a + b);
```

The `reduce` action aggregates the elements of the RDD using a function that takes two arguments and returns one.

### 5. Chaining Operations

```java
int sumOfSquaresOfEvenNumbers = numbersRDD
        .filter(x -> x % 2 == 0)  // Keep only even numbers
        .map(x -> x * x)          // Square each number
        .reduce((a, b) -> a + b); // Sum them up
```

This demonstrates how to chain multiple transformations and actions together.

## Running the Example

To run this example:

1. Make sure you have Java 8 or higher and Maven installed
2. Navigate to the `rdd-operations` directory
3. Run the following commands:

```bash
mvn clean package
spark-submit --class org.frank.learningspark.rdd.BasicRDDExample target/rdd-operations-1.0-SNAPSHOT.jar
```

## Expected Output

```
Original RDD: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
Squared RDD: [1, 4, 9, 16, 25, 36, 49, 64, 81, 100]
Even numbers RDD: [2, 4, 6, 8, 10]
Count: 10
Sum: 55
Sum of squares of even numbers: 220
```

## Additional RDD Operations

Beyond what's shown in this example, RDDs support many other operations:

- **flatMap**: Similar to map, but each input item can be mapped to 0 or more output items
- **distinct**: Returns a new RDD containing distinct elements from the source RDD
- **union**, **intersection**, **subtract**: Set operations on RDDs
- **sortBy**: Returns an RDD sorted by the specified key
- **groupBy**: Groups the data in the RDD by key

## Key Takeaways

1. RDDs are the fundamental data structure in Spark
2. Transformations are lazy and create new RDDs
3. Actions trigger computation and return results
4. RDDs provide a rich set of operations for data processing
5. Java 8 lambda expressions can make Spark code more concise