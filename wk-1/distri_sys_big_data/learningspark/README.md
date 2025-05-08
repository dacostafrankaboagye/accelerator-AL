# Learning Apache Spark with Java

This repository provides a beginner-friendly introduction to Apache Spark using Java. It contains simple examples to help you understand the basics of Spark programming.

## What is Apache Spark?

Apache Spark is an open-source, distributed computing system designed for fast, in-memory data processing. It was developed to overcome the limitations of Hadoop MapReduce, offering:

- **Speed**: Spark can be up to 100x faster than Hadoop for large-scale data processing by leveraging in-memory computing.
- **Ease of Use**: Spark offers simple APIs in Java, Scala, Python, and R.
- **Generality**: Spark comes with libraries for SQL (Spark SQL), machine learning (MLlib), graph processing (GraphX), and stream processing (Spark Streaming).
- **Fault Tolerance**: Spark provides resilient distributed datasets (RDDs) that can recover from node failures automatically.

## Key Concepts in Apache Spark

### 1. Spark Architecture

Spark follows a master-worker architecture:

- **Driver Program**: Contains the main function and creates the SparkContext
- **Cluster Manager**: Allocates resources (like YARN, Mesos, or Spark's standalone manager)
- **Executors**: Processes that run computations and store data on worker nodes

### 2. Resilient Distributed Datasets (RDDs)

RDDs are Spark's fundamental data structure:

- Immutable, distributed collections of objects
- Can be created from Hadoop InputFormats or by transforming other RDDs
- Operations: Transformations (lazy) and Actions (eager)

### 3. DataFrames and Datasets

Higher-level abstractions built on top of RDDs:

- **DataFrames**: Distributed collection of data organized into named columns (like a table)
- **Datasets**: Extension of DataFrames with type-safety and object-oriented interface

### 4. Spark SQL

Allows querying data via SQL as well as the DataFrame API:

- Integrates with various data sources
- Provides a unified interface for working with structured data

### 5. Spark Streaming

Enables processing of live data streams:

- Divides the stream into micro-batches for processing
- Provides high-level APIs similar to the core Spark API

## Getting Started with Spark

### Prerequisites

- Java 8 or higher
- Maven or another build tool
- Basic understanding of Java programming

### Setting Up a Spark Project

This repository uses Maven for dependency management. The key Spark dependencies are:

```xml
<dependency>
    <groupId>org.apache.spark</groupId>
    <artifactId>spark-core_2.12</artifactId>
    <version>3.3.2</version>
</dependency>

<dependency>
    <groupId>org.apache.spark</groupId>
    <artifactId>spark-sql_2.12</artifactId>
    <version>3.3.2</version>
</dependency>
```

### Creating a SparkSession

In Spark applications, you start by creating a SparkSession:

```java
SparkSession spark = SparkSession.builder()
    .appName("My Spark Application")
    .master("local[*]")  // Use "local[*]" for local development
    .getOrCreate();
```

## Examples in this Repository

This repository contains three beginner-friendly examples:

1. **RDD Operations**: Learn the basics of RDDs with simple transformations and actions.
   - Located in `src/main/java/org/frank/learningspark/rdd/BasicRDDExample.java`

2. **DataFrame Operations**: Explore structured data processing with DataFrames.
   - Located in `src/main/java/org/frank/learningspark/dataframe/BasicDataFrameExample.java`

3. **Word Count Example**: A classic Spark example that counts word occurrences in a text file.
   - Located in `src/main/java/org/frank/learningspark/wordcount/CountWordsExample.java`

## Project Structure

The project has been restructured to have all examples in a single module:

```
src/main/java/org/frank/learningspark/
├── App.java                  # Main application with menu to run examples
├── rdd/                      # RDD examples
│   └── BasicRDDExample.java  # Basic RDD operations
├── dataframe/                # DataFrame examples
│   └── BasicDataFrameExample.java  # Basic DataFrame operations
└── wordcount/                # Word count examples
    └── CountWordsExample.java  # Word count operations
```

## Running the Examples

You can run the examples using Maven:

```bash
# Compile the project
mvn clean package

# Run the main application
mvn exec:java -Dexec.mainClass="org.frank.learningspark.App"
```

This will display a menu where you can choose which example to run:
1. Basic RDD Example
2. Basic DataFrame Example
3. Word Count Example
4. Displaying Project Structure

Or directly from your IDE by running the `org.frank.learningspark.App` class.

## Additional Resources

- [Apache Spark Official Documentation](https://spark.apache.org/docs/latest/)
- [Spark Programming Guide](https://spark.apache.org/docs/latest/programming-guide.html)
- [Spark Java API](https://spark.apache.org/docs/latest/api/java/index.html)

## License

This project is licensed under the MIT License - see the LICENSE file for details.
