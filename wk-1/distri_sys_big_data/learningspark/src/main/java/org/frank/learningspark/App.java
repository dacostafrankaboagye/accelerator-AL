package org.frank.learningspark;

import org.frank.learningspark.dataframe.BasicDataFrameExample;
import org.frank.learningspark.rdd.BasicRDDExample;
import org.frank.learningspark.wordcount.CountWordsExample;

import java.io.File;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        System.out.println("Welcome to Learning Spark!");
        System.out.println("==========================");

        // Display menu and get user choice
        int choice = displayMenuAndGetChoice();

        // Execute the chosen example
        switch (choice) {
            case 1:
                System.out.println("\nRunning RDD Example...\n");
                BasicRDDExample.main(args);
                break;
            case 2:
                System.out.println("\nRunning DataFrame Example...\n");
                BasicDataFrameExample.main(args);
                break;
            case 3:
                System.out.println("\nRunning Word Count Example...\n");
                CountWordsExample.main(args);
                break;
            case 4:
                System.out.println("\nDisplaying Project Structure...\n");
                displayProjectStructure();
                break;
            default:
                System.out.println("Invalid choice. Exiting...");
                break;
        }
    }

    /**
     * Displays the menu and gets the user's choice
     * @return The user's choice
     */
    private static int displayMenuAndGetChoice() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please select an example to run:");
        System.out.println("1. Basic RDD Example");
        System.out.println("2. Basic DataFrame Example");
        System.out.println("3. Word Count Example");
        System.out.println("4. Display Project Structure");

        System.out.print("\nEnter your choice (1-4): ");

        try {
            return scanner.nextInt();
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * Displays the project structure
     */
    private static void displayProjectStructure() {
        System.out.println("Displaying folder structure:");

        // Get the current directory
        String currentDir = System.getProperty("user.dir");
        File rootDir = new File(currentDir);

        // Display the folder structure
        displayFolderStructure(rootDir, 0);
    }

    /**
     * Recursively displays the folder structure starting from the given directory
     * @param dir The directory to display
     * @param level The indentation level
     */
    private static void displayFolderStructure(File dir, int level) {
        // Create indentation based on level
        String indent = "";
        for (int i = 0; i < level; i++) {
            indent += "  ";
        }

        // Print the current directory name
        System.out.println(indent + "├── " + dir.getName());

        // Get all files and directories in the current directory
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    // Recursively display subdirectories
                    displayFolderStructure(file, level + 1);
                } else {
                    // Display files
                    System.out.println(indent + "  ├── " + file.getName());
                }
            }
        }
    }
}
