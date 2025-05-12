package org.frank.designpatterns.strategy;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Demo application that demonstrates the Strategy pattern with different sorting algorithms.
 * Users can choose between different sorting strategies at runtime.
 */
public class SortingApplication {

    public static void main(String[] args) {
        // Create the sorting strategies
        SortingStrategy bubbleSort = new BubbleSortStrategy();
        SortingStrategy selectionSort = new SelectionSortStrategy();
        
        // Create the context with an initial strategy
        SortingContext context = new SortingContext(bubbleSort);
        
        // Sample array to sort
        int[] array = {64, 34, 25, 12, 22, 11, 90};
        
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        
        while (!exit) {
            System.out.println("\n=== Sorting Application ===");
            System.out.println("Current array: " + Arrays.toString(array));
            System.out.println("Current sorting strategy: " + context.getStrategyName());
            System.out.println("\nChoose an option:");
            System.out.println("1. Use Bubble Sort");
            System.out.println("2. Use Selection Sort");
            System.out.println("3. Sort the array");
            System.out.println("4. Enter a new array");
            System.out.println("5. Exit");
            System.out.print("Enter your choice (1-5): ");
            
            int choice = scanner.nextInt();
            
            switch (choice) {
                case 1:
                    context.setStrategy(bubbleSort);
                    System.out.println("Switched to Bubble Sort strategy.");
                    break;
                case 2:
                    context.setStrategy(selectionSort);
                    System.out.println("Switched to Selection Sort strategy.");
                    break;
                case 3:
                    System.out.println("Sorting array using " + context.getStrategyName() + "...");
                    long startTime = System.nanoTime();
                    int[] sortedArray = context.executeSort(array);
                    long endTime = System.nanoTime();
                    System.out.println("Sorted array: " + Arrays.toString(sortedArray));
                    System.out.println("Sorting time: " + (endTime - startTime) + " nanoseconds");
                    break;
                case 4:
                    System.out.print("Enter the number of elements: ");
                    int n = scanner.nextInt();
                    array = new int[n];
                    System.out.println("Enter " + n + " integers:");
                    for (int i = 0; i < n; i++) {
                        array[i] = scanner.nextInt();
                    }
                    System.out.println("New array: " + Arrays.toString(array));
                    break;
                case 5:
                    exit = true;
                    System.out.println("Exiting application. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        
        scanner.close();
    }
}