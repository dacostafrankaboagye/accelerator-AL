# DataFrame Operations Example

This example demonstrates how to work with DataFrames in Apache Spark, which are a distributed collection of data organized into named columns.

## What are DataFrames?

DataFrames in Spark are similar to tables in a relational database or DataFrames in Python's pandas library. They provide a more user-friendly API than RDDs and offer significant performance optimizations through Spark's Catalyst optimizer.

Key features of DataFrames:
- **Structured data**: Data is organized into named columns, like a table
- **Schema**: Each DataFrame has a schema that defines the column names and types
- **Optimized execution**: Spark SQL's Catalyst optimizer improves performance
- **Language interoperability**: Available in Java, Scala, Python, and R

## DataFrame vs RDD

While RDDs are the fundamental data structure in Spark, DataFrames offer several advantages:

| Feature | RDD | DataFrame |
|---------|-----|-----------|
| Data Organization | Distributed collection of objects | Distributed collection of structured data |
| Schema | No schema enforcement | Schema-based |
| Optimization | Basic optimizations | Advanced optimizations via Catalyst |
| API | Functional programming API | Domain-specific language API |
| Ease of Use | More verbose | More concise, SQL-like operations |

## Example Code Walkthrough

The `BasicDataFrameExample.java` file demonstrates three main aspects of working with DataFrames:

### 1. Creating a DataFrame from a Collection

```java
List<Person> people = Arrays.asList(
        new Person("John", 30),
        new Person("Alice", 25),
        new Person("Bob", 32),
        new Person("Emma", 28)
);

Dataset<Row> peopleDF = spark.createDataFrame(people, Person.class);
```

This approach uses a Java class (Person) with getters and setters to define the structure of the data. Spark uses reflection to determine the schema.

### 2. Creating a DataFrame with a Defined Schema

```java
StructType schema = new StructType(new StructField[] {
        new StructField("id", DataTypes.IntegerType, false, Metadata.empty()),
        new StructField("name", DataTypes.StringType, false, Metadata.empty()),
        new StructField("department", DataTypes.StringType, true, Metadata.empty()),
        new StructField("salary", DataTypes.DoubleType, true, Metadata.empty())
});

List<Row> employees = Arrays.asList(
        RowFactory.create(1, "John Doe", "Engineering", 85000.0),
        RowFactory.create(2, "Jane Smith", "Marketing", 72000.0),
        // ... more rows
);

Dataset<Row> employeesDF = spark.createDataFrame(employees, schema);
```

This approach explicitly defines the schema and creates rows of data. It's useful when:
- You don't have a Java class that matches your data structure
- You need more control over column types and nullability
- You're working with dynamic schemas

### 3. Basic DataFrame Operations

The example demonstrates several common DataFrame operations:

#### Reading Data from a CSV File

```java
Dataset<Row> df = spark.read()
        .option("header", "true")
        .option("inferSchema", "true")
        .csv("dataframe-operations/src/main/resources/sales.csv");
```

#### Selecting Columns

```java
df.select("product", "price").show();
```

#### Filtering Rows

```java
df.filter(df.col("category").equalTo("Electronics")).show();
```

#### Sorting Data

```java
df.sort(functions.desc("price")).show();
```

#### Grouping and Aggregation

```java
df.groupBy("category")
        .agg(functions.avg("price").as("avg_price"),
             functions.sum("quantity").as("total_quantity"))
        .show();
```

#### Adding Calculated Columns

```java
df.withColumn("total_value", df.col("price").multiply(df.col("quantity")))
        .show();
```

### 4. Using SQL with DataFrames

Spark SQL allows you to run SQL queries on DataFrames by registering them as temporary views:

```java
df.createOrReplaceTempView("sales");

spark.sql("SELECT category, SUM(price * quantity) as total_value " +
          "FROM sales GROUP BY category ORDER BY total_value DESC")
        .show();
```

## Running the Example

To run this example:

1. Make sure you have Java 8 or higher and Maven installed
2. Navigate to the `dataframe-operations` directory
3. Run the following commands:

```bash
mvn clean package
spark-submit --class org.frank.learningspark.dataframe.BasicDataFrameExample target/dataframe-operations-1.0-SNAPSHOT.jar
```

## Expected Output

The example will display several DataFrames and their transformations, including:
- A people DataFrame with name and age columns
- An employees DataFrame with id, name, department, and salary columns
- A sales DataFrame with various operations applied (filtering, sorting, grouping, etc.)

## Additional DataFrame Operations

Beyond what's shown in this example, DataFrames support many other operations:

- **join**: Combine two DataFrames based on a join condition
- **union**: Combine rows from two DataFrames with the same schema
- **distinct**: Remove duplicate rows
- **dropDuplicates**: Remove duplicate rows based on specific columns
- **pivot**: Reshape data from rows to columns
- **explode**: Convert array elements to individual rows
- **window functions**: Perform calculations across a set of rows

## Key Takeaways

1. DataFrames provide a structured, tabular data abstraction in Spark
2. They can be created from various sources (Java objects, explicit schemas, files)
3. DataFrames offer both a method-based API and SQL query capabilities
4. They provide powerful operations for data transformation and analysis
5. Spark's optimizer automatically improves the execution of DataFrame operations