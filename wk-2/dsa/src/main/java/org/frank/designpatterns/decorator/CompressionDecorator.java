package org.frank.designpatterns.decorator;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Decorator that adds compression to a message.
 * This is a "ConcreteDecorator" in the Decorator pattern.
 */
public class CompressionDecorator extends MessageDecorator {
    
    private String compressionAlgorithm;
    private int compressionLevel; // 1 (low) to 9 (high)
    
    /**
     * Constructor for CompressionDecorator.
     * 
     * @param decoratedMessage The message to decorate
     * @param compressionAlgorithm The compression algorithm to use
     * @param compressionLevel The compression level (1-9)
     */
    public CompressionDecorator(Message decoratedMessage, String compressionAlgorithm, int compressionLevel) {
        super(decoratedMessage);
        this.compressionAlgorithm = compressionAlgorithm;
        this.compressionLevel = Math.max(1, Math.min(9, compressionLevel)); // Ensure level is between 1 and 9
        super.setContent(compress(decoratedMessage.getContent()));
    }
    
    /**
     * Constructor for CompressionDecorator with default compression level (5).
     * 
     * @param decoratedMessage The message to decorate
     * @param compressionAlgorithm The compression algorithm to use
     */
    public CompressionDecorator(Message decoratedMessage, String compressionAlgorithm) {
        this(decoratedMessage, compressionAlgorithm, 5);
    }
    
    @Override
    public String getContent() {
        // Return the decompressed content
        String compressedContent = super.getContent();
        return decompress(compressedContent);
    }
    
    @Override
    public void setContent(String content) {
        // Compress the content before setting it
        String compressedContent = compress(content);
        super.setContent(compressedContent);
    }
    
    @Override
    public boolean send() {
        System.out.println("Sending compressed message using " + compressionAlgorithm + 
                " algorithm (level " + compressionLevel + ")");
        return super.send();
    }
    
    @Override
    public String getDescription() {
        return super.getDescription() + " with " + compressionAlgorithm + 
                " compression (level " + compressionLevel + ")";
    }
    
    @Override
    public int getSize() {
        // Return the size of the labeled compressed content
        String labeledContent = "CompAlgo -=== " + compressionAlgorithm + " === " + super.getContent();
        return labeledContent.length();
    }
    
    /**
     * Compress the content.
     * 
     * @param content The content to compress
     * @return The compressed content
     */
    private String compress(String content) {
        System.out.println("Compressing message with " + compressionAlgorithm +
                " (level " + compressionLevel + ")");
        return "CompAlgo -=== " + compressionAlgorithm + " === " + content;
    }
    
    /**
     * Decompress the content.
     * 
     * @param compressedContent The compressed content
     * @return The decompressed content
     */
    private String decompress(String compressedContent) {
        String prefix = "CompAlgo -=== " + compressionAlgorithm + " === ";

        if (compressedContent.startsWith(prefix)) {
            return compressedContent.substring(prefix.length());
        } else {
            System.err.println("Invalid compression format. Skipping decompression.");
            return compressedContent;
        }
    }
    
    /**
     * Get the compression algorithm.
     * 
     * @return The compression algorithm
     */
    public String getCompressionAlgorithm() {
        return compressionAlgorithm;
    }
    
    /**
     * Get the compression level.
     * 
     * @return The compression level (1-9)
     */
    public int getCompressionLevel() {
        return compressionLevel;
    }
}