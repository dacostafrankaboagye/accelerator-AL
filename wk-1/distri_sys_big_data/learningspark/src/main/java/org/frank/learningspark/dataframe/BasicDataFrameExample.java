package org.frank.learningspark.dataframe;

import org.apache.spark.sql.*;
import org.apache.spark.sql.types.*;

import java.util.Arrays;
import java.util.List;

/**
 * A basic example demonstrating DataFrame operations in Apache Spark.
 * This example shows:
 * 1. Creating a DataFrame from a collection
 * 2. Creating a DataFrame with a schema
 * 3. Performing basic DataFrame operations
 * 4. Using SQL queries on DataFrames
 */
public class BasicDataFrameExample {

    public static void main(String[] args) {
        // Step 1: Create a SparkSession
        SparkSession spark = SparkSession.builder()
                .appName("Basic DataFrame Example")
                .master("local[*]") // Use all available cores on local machine
                .getOrCreate();

        try {
            // Example 1: Create a DataFrame from a collection of data
            createDataFrameFromCollection(spark);

            // Example 2: Create a DataFrame with a defined schema
            createDataFrameWithSchema(spark);

            // Example 3: Perform basic DataFrame operations
            performBasicOperations(spark);

        } finally {
            // Stop the SparkSession
            spark.stop();
        }
    }

    /**
     * Example 1: Create a DataFrame from a collection of data
     */
    private static void createDataFrameFromCollection(SparkSession spark) {
        System.out.println("Example 1: Creating a DataFrame from a collection");
        
        // Create a list of Person objects
        List<Person> people = Arrays.asList(
                new Person("John", 30),
                new Person("Alice", 25),
                new Person("Bob", 32),
                new Person("Emma", 28)
        );
        
        // Convert the list to a DataFrame
        Dataset<Row> peopleDF = spark.createDataFrame(people, Person.class);
        
        // Show the DataFrame
        System.out.println("People DataFrame:");
        peopleDF.show();
        
        // Print the schema
        System.out.println("DataFrame Schema:");
        peopleDF.printSchema();
        
        System.out.println();
    }

    /**
     * Example 2: Create a DataFrame with a defined schema
     */
    private static void createDataFrameWithSchema(SparkSession spark) {
        System.out.println("Example 2: Creating a DataFrame with a defined schema");
        
        // Define the schema
        StructType schema = new StructType(new StructField[] {
                new StructField("id", DataTypes.IntegerType, false, Metadata.empty()),
                new StructField("name", DataTypes.StringType, false, Metadata.empty()),
                new StructField("department", DataTypes.StringType, true, Metadata.empty()),
                new StructField("salary", DataTypes.DoubleType, true, Metadata.empty())
        });
        
        // Create data rows
        List<Row> employees = Arrays.asList(
                RowFactory.create(1, "John Doe", "Engineering", 85000.0),
                RowFactory.create(2, "Jane Smith", "Marketing", 72000.0),
                RowFactory.create(3, "David Johnson", "Engineering", 95000.0),
                RowFactory.create(4, "Lisa Brown", "Finance", 78000.0),
                RowFactory.create(5, "Michael Wilson", "HR", 65000.0)
        );
        
        // Create DataFrame with the schema
        Dataset<Row> employeesDF = spark.createDataFrame(employees, schema);
        
        // Show the DataFrame
        System.out.println("Employees DataFrame:");
        employeesDF.show();
        
        // Print the schema
        System.out.println("DataFrame Schema:");
        employeesDF.printSchema();
        
        // Register the DataFrame as a temporary view for SQL queries
        employeesDF.createOrReplaceTempView("employees");
        
        // Run a SQL query
        System.out.println("SQL Query Result - Engineers with salary > 90000:");
        spark.sql("SELECT * FROM employees WHERE department = 'Engineering' AND salary > 90000")
                .show();
        
        System.out.println();
    }

    /**
     * Example 3: Perform basic DataFrame operations
     */
    private static void performBasicOperations(SparkSession spark) {
        System.out.println("Example 3: Performing basic DataFrame operations");
        
        // Create a simple DataFrame
        Dataset<Row> df = spark.read()
                .option("header", "true")
                .option("inferSchema", "true")
                .csv("src/main/resources/sales.csv");
        
        // If the file doesn't exist, create a sample DataFrame
        if (df.isEmpty()) {
            List<Row> salesData = Arrays.asList(
                    RowFactory.create(1, "Product A", "Electronics", 100, 499.99),
                    RowFactory.create(2, "Product B", "Electronics", 50, 599.99),
                    RowFactory.create(3, "Product C", "Clothing", 200, 29.99),
                    RowFactory.create(4, "Product D", "Clothing", 150, 39.99),
                    RowFactory.create(5, "Product E", "Home", 75, 199.99),
                    RowFactory.create(6, "Product F", "Home", 100, 299.99),
                    RowFactory.create(7, "Product G", "Electronics", 30, 699.99),
                    RowFactory.create(8, "Product H", "Clothing", 80, 49.99)
            );
            
            StructType salesSchema = new StructType(new StructField[] {
                    new StructField("id", DataTypes.IntegerType, false, Metadata.empty()),
                    new StructField("product", DataTypes.StringType, false, Metadata.empty()),
                    new StructField("category", DataTypes.StringType, false, Metadata.empty()),
                    new StructField("quantity", DataTypes.IntegerType, false, Metadata.empty()),
                    new StructField("price", DataTypes.DoubleType, false, Metadata.empty())
            });
            
            df = spark.createDataFrame(salesData, salesSchema);
        }
        
        // Show the DataFrame
        System.out.println("Sales DataFrame:");
        df.show();
        
        // Select specific columns
        System.out.println("Selected columns (product, price):");
        df.select("product", "price").show();
        
        // Filter rows
        System.out.println("Filtered rows (category = Electronics):");
        df.filter(df.col("category").equalTo("Electronics")).show();
        
        // Sort by price (descending)
        System.out.println("Sorted by price (descending):");
        df.sort(functions.desc("price")).show();
        
        // Group by category and calculate average price
        System.out.println("Group by category with average price:");
        df.groupBy("category")
                .agg(functions.avg("price").as("avg_price"),
                     functions.sum("quantity").as("total_quantity"))
                .show();
        
        // Add a new column with total value (price * quantity)
        System.out.println("Adding a calculated column (total_value):");
        df.withColumn("total_value", df.col("price").multiply(df.col("quantity")))
                .show();
        
        // Register as a temporary view for SQL
        df.createOrReplaceTempView("sales");
        
        // Use SQL to query the data
        System.out.println("SQL Query - Total value by category:");
        spark.sql("SELECT category, SUM(price * quantity) as total_value " +
                  "FROM sales GROUP BY category ORDER BY total_value DESC")
                .show();
    }

    /**
     * Person class for Example 1
     */
    public static class Person implements java.io.Serializable {
        private String name;
        private int age;
        
        public Person() {}
        
        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }
        
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
        public int getAge() {
            return age;
        }
        
        public void setAge(int age) {
            this.age = age;
        }
    }
}