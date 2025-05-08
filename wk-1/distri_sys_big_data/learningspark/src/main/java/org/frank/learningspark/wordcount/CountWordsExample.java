package org.frank.learningspark.wordcount;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * A word count example demonstrating RDD operations in Apache Spark.
 * This example shows:
 * 1. Reading text from a file
 * 2. Splitting text into words
 * 3. Counting occurrences of each word
 * 4. Sorting results by count
 */
public class CountWordsExample {

    private static final Pattern SPACE = Pattern.compile("\\s+");

    public static void main(String[] args) {
        // Step 1: Configure Spark
        SparkConf conf = new SparkConf()
                .setAppName("Word Count Example")
                .setMaster("local[*]"); // Use all available cores on local machine
        
        // Step 2: Create a JavaSparkContext
        JavaSparkContext sc = new JavaSparkContext(conf);
        
        try {
            System.out.println("+++++++++++++++++++++++++++++++++++++++");
            System.out.println("Word Count Example");
            System.out.println("+++++++++++++++++++++++++++++++++++++++");
            
            // Step 3: Load the input text file
            JavaRDD<String> lines = sc.textFile("src/main/resources/sample-text.txt");
            
            System.out.println("Input text file:");
            List<String> linesList = lines.collect();
            for (String line : linesList) {
                System.out.println(line);
            }
            
            System.out.println("===================================");
            
            // Step 4: Split each line into words
            JavaRDD<String> words = lines.flatMap(line -> 
                Arrays.asList(SPACE.split(line.toLowerCase().replaceAll("[^a-zA-Z\\s]", ""))).iterator()
            );
            
            // Step 5: Count each word
            JavaPairRDD<String, Integer> wordCounts = words
                    .filter(word -> !word.isEmpty()) // Filter out empty strings
                    .mapToPair(word -> new Tuple2<>(word, 1))
                    .reduceByKey((count1, count2) -> count1 + count2);
            
            // Step 6: Sort by count in descending order
            JavaPairRDD<String, Integer> sortedCounts = wordCounts
                    .mapToPair(pair -> new Tuple2<>(pair._2(), pair._1()))
                    .sortByKey(false)
                    .mapToPair(pair -> new Tuple2<>(pair._2(), pair._1()));
            
            // Step 7: Collect and print the results
            System.out.println("Word counts (sorted by frequency):");
            List<Tuple2<String, Integer>> output = sortedCounts.collect();
            for (Tuple2<String, Integer> tuple : output) {
                System.out.println(tuple._1() + ": " + tuple._2());
            }
            
        } finally {
            // Step 8: Stop the SparkContext
            sc.stop();
        }
    }
}