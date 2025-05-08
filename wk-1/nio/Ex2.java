package nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;

/**
 * Exercise 2: A simple file copier program using NIO.
 * This class provides functionality to copy contents from one file to another
 * using Java NIO's channels and buffers for efficient I/O operations.
 */
public class Ex2 {

    /**
     * Copies the contents of a source file to a destination file using NIO.
     * 
     * @param sourcePath      Path to the source file
     * @param destinationPath Path to the destination file
     * @throws IOException If an I/O error occurs during the copy operation
     */
    public static void copyFile(String sourcePath, String destinationPath) throws IOException {
        // Create Path objects for source and destination files
        Path source = Path.of(sourcePath);
        Path destination = Path.of(destinationPath);

        // Buffer for transferring data between files
        // Using a 8KB buffer size for efficient transfer
        ByteBuffer buffer = ByteBuffer.allocate(8192);

        // Open both files using try-with-resources to ensure proper resource cleanup
        try (
            // Open source file for reading
            FileChannel sourceChannel = FileChannel.open(source, StandardOpenOption.READ);

            // Open destination file for writing, creating it if it doesn't exist
            // and truncating it if it does
            FileChannel destinationChannel = FileChannel.open(destination, 
                EnumSet.of(
                    StandardOpenOption.CREATE, 
                    StandardOpenOption.WRITE, 
                    StandardOpenOption.TRUNCATE_EXISTING)
            )
        ) {
            // Read from source and write to destination until end of file
            int bytesRead;
            while ((bytesRead = sourceChannel.read(buffer)) != -1) {
                // Flip buffer to prepare for writing
                buffer.flip();

                // Write data from buffer to destination file
                destinationChannel.write(buffer);

                // Clear buffer to prepare for next read
                buffer.clear();
            }

            System.out.println("File copied successfully from " + sourcePath + " to " + destinationPath);
        } catch (IOException e) {
            // Propagate the exception after logging
            System.err.println("Error copying file: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Main method to demonstrate file copying functionality.
     * 
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        // Example usage
        String sourceFile = "output.txt"; // Using the file created by Ex1
        String destinationFile = "copied_output.txt";

        try {
            copyFile(sourceFile, destinationFile);
        } catch (IOException e) {
            System.err.println("File copy operation failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
