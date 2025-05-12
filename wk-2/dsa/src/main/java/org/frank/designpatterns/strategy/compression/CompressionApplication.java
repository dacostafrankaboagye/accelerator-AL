package org.frank.designpatterns.strategy.compression;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Demo application that demonstrates the Strategy pattern with different compression algorithms.
 * Users can choose between different compression strategies at runtime.
 */
public class CompressionApplication {

    public static void main(String[] args) {
        // Create the compression strategies
        CompressionStrategy zipStrategy = new ZipStrategy();
        CompressionStrategy gzipStrategy = new GzipStrategy();
        
        // Create the context with an initial strategy
        CompressionContext context = new CompressionContext(zipStrategy);
        
        // Create temporary directories for compression and decompression
        String tempDir = System.getProperty("java.io.tmpdir");
        String compressedDir = tempDir + File.separator + "compressed";
        String decompressedDir = tempDir + File.separator + "decompressed";
        
        try {
            // Create directories if they don't exist
            Files.createDirectories(Paths.get(compressedDir));
            Files.createDirectories(Paths.get(decompressedDir));
            
            Scanner scanner = new Scanner(System.in);
            boolean exit = false;
            
            while (!exit) {
                System.out.println("\n=== Compression Application ===");
                System.out.println("Current compression strategy: " + context.getStrategyName());
                System.out.println("\nChoose an option:");
                System.out.println("1. Use ZIP Compression");
                System.out.println("2. Use GZIP Compression");
                System.out.println("3. Compress a file");
                System.out.println("4. Decompress a file");
                System.out.println("5. Exit");
                System.out.print("Enter your choice (1-5): ");
                
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline
                
                switch (choice) {
                    case 1:
                        context.setStrategy(zipStrategy);
                        System.out.println("Switched to " + context.getStrategyName() + " strategy.");
                        break;
                    case 2:
                        context.setStrategy(gzipStrategy);
                        System.out.println("Switched to " + context.getStrategyName() + " strategy.");
                        break;
                    case 3:
                        System.out.print("Enter the path of the file to compress: ");
                        String filePath = scanner.nextLine();
                        File fileToCompress = new File(filePath);
                        
                        if (!fileToCompress.exists() || !fileToCompress.isFile()) {
                            System.out.println("File does not exist or is not a valid file.");
                            break;
                        }
                        
                        try {
                            File compressedFile = context.compress(fileToCompress, compressedDir);
                            System.out.println("File compressed successfully: " + compressedFile.getAbsolutePath());
                        } catch (IOException e) {
                            System.out.println("Error compressing file: " + e.getMessage());
                        }
                        break;
                    case 4:
                        System.out.print("Enter the path of the file to decompress: ");
                        String compressedFilePath = scanner.nextLine();
                        File fileToDecompress = new File(compressedFilePath);
                        
                        if (!fileToDecompress.exists() || !fileToDecompress.isFile()) {
                            System.out.println("File does not exist or is not a valid file.");
                            break;
                        }
                        
                        try {
                            File decompressedFile = context.decompress(fileToDecompress, decompressedDir);
                            System.out.println("File decompressed successfully: " + decompressedFile.getAbsolutePath());
                        } catch (IOException e) {
                            System.out.println("Error decompressing file: " + e.getMessage());
                        }
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
            
        } catch (IOException e) {
            System.out.println("Error creating temporary directories: " + e.getMessage());
        }
    }
}