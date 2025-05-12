package org.frank.designpatterns.strategy.compression;

import java.io.File;
import java.io.IOException;

/**
 * Context class that uses a CompressionStrategy to perform compression operations.
 * This class demonstrates the Strategy pattern by allowing the compression algorithm
 * to be changed at runtime.
 */
public class CompressionContext {
    
    private CompressionStrategy strategy;
    
    /**
     * Constructor that accepts a compression strategy.
     * 
     * @param strategy The compression strategy to use
     */
    public CompressionContext(CompressionStrategy strategy) {
        this.strategy = strategy;
    }
    
    /**
     * Set a new compression strategy.
     * 
     * @param strategy The new compression strategy to use
     */
    public void setStrategy(CompressionStrategy strategy) {
        this.strategy = strategy;
    }
    
    /**
     * Execute the compression operation using the current strategy.
     * 
     * @param sourceFile The file to compress
     * @param destinationPath The path where the compressed file will be saved
     * @return The compressed file
     * @throws IOException If an I/O error occurs
     */
    public File compress(File sourceFile, String destinationPath) throws IOException {
        return strategy.compress(sourceFile, destinationPath);
    }
    
    /**
     * Execute the decompression operation using the current strategy.
     * 
     * @param compressedFile The compressed file
     * @param destinationPath The path where the decompressed file will be saved
     * @return The decompressed file
     * @throws IOException If an I/O error occurs
     */
    public File decompress(File compressedFile, String destinationPath) throws IOException {
        return strategy.decompress(compressedFile, destinationPath);
    }
    
    /**
     * Get the name of the current compression strategy.
     * 
     * @return The name of the current compression strategy
     */
    public String getStrategyName() {
        return strategy.getName();
    }
    
    /**
     * Get the file extension used by the current compression strategy.
     * 
     * @return The file extension (e.g., ".zip", ".gz")
     */
    public String getStrategyFileExtension() {
        return strategy.getFileExtension();
    }
}