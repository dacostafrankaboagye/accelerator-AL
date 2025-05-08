package org.frank.learningspark.rdd;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;

import java.util.Arrays;
import java.util.List;

/**
 * A basic example demonstrating RDD operations in Apache Spark.
 * This example shows:
 * 1. Creating an RDD from a collection
 * 2. Applying transformations (map, filter)
 * 3. Performing actions (collect, count, reduce)
 */
public class BasicRDDExample {

    public static void main(String[] args) {
        // Step 1: Configure Spark
        SparkConf conf = new SparkConf()
                .setAppName("Basic RDD Example")
                .setMaster("local[*]"); // Use all available cores on local machine
        
        // Step 2: Create a JavaSparkContext
        JavaSparkContext sc = new JavaSparkContext(conf);
        
        try {
            // Step 3: Create an RDD from a collection
            List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
            JavaRDD<Integer> numbersRDD = sc.parallelize(numbers);

            System.out.println("+++++++++++++++++++++++++++++++++++++++");
            System.out.println("Original RDD: " + numbersRDD.collect());
            
            // Step 4: Apply transformations
            
            // Example 1: Map transformation - Square each number
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

            System.out.println("===================================");
            System.out.println("Squared RDD: " + squaredRDD.collect());

            
            // Example 2: Filter transformation - Keep only even numbers
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

            System.out.println("===================================");
            System.out.println("Even numbers RDD: " + evenRDD.collect());

            
            // Step 5: Perform actions
            
            // Count action
            long count = numbersRDD.count();
            System.out.println("Count: " + count);
            
            // Reduce action - Sum all numbers
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
            
            System.out.println("Sum: " + sum);
            
            // Example of chaining transformations and actions
            int sumOfSquaresOfEvenNumbers = numbersRDD
                    .filter(x -> x % 2 == 0)  // Keep only even numbers
                    .map(x -> x * x)          // Square each number
                    .reduce((a, b) -> a + b); // Sum them up

            System.out.println("===================================");
            System.out.println("Sum of squares of even numbers: " + sumOfSquaresOfEvenNumbers);

            
        } finally {
            // Step 6: Stop the SparkContext
            sc.stop();
        }
    }
}