package org.frank.designpatterns.strategy.compression;

import java.io.File;
import java.io.IOException;

/**
 * Strategy interface for different compression algorithms.
 * This interface defines the contract for all compression strategies.
 */
public interface CompressionStrategy {
    
    /**
     * Compress a file using the strategy's algorithm.
     * 
     * @param sourceFile The file to compress
     * @param destinationPath The path where the compressed file will be saved
     * @return The compressed file
     * @throws IOException If an I/O error occurs
     */
    File compress(File sourceFile, String destinationPath) throws IOException;
    
    /**
     * Decompress a file using the strategy's algorithm.
     * 
     * @param compressedFile The compressed file
     * @param destinationPath The path where the decompressed file will be saved
     * @return The decompressed file
     * @throws IOException If an I/O error occurs
     */
    File decompress(File compressedFile, String destinationPath) throws IOException;
    
    /**
     * Get the name of the compression algorithm.
     * 
     * @return The name of the compression algorithm
     */
    String getName();
    
    /**
     * Get the file extension used by this compression algorithm.
     * 
     * @return The file extension (e.g., ".zip", ".gz")
     */
    String getFileExtension();
}