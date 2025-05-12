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
        // Compressed content should be smaller than the original
        // This is a rough estimation
        double compressionRatio = getCompressionRatio();
        return (int) (super.getSize() * compressionRatio);
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
        
        if ("GZIP".equalsIgnoreCase(compressionAlgorithm)) {
            try {
                ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
                try (GZIPOutputStream gzipStream = new GZIPOutputStream(byteStream)) {
                    gzipStream.write(content.getBytes());
                }
                // Base64 encode the compressed data for text representation
                return Base64.getEncoder().encodeToString(byteStream.toByteArray());
            } catch (IOException e) {
                System.err.println("Error compressing data: " + e.getMessage());
                return content; // Return original content if compression fails
            }
        } else if ("RLE".equalsIgnoreCase(compressionAlgorithm)) {
            // Simple Run-Length Encoding for demonstration
            return runLengthEncode(content);
        } else {
            // Default to a simple character removal for demonstration
            return removeSpaces(content);
        }
    }
    
    /**
     * Decompress the content.
     * 
     * @param compressedContent The compressed content
     * @return The decompressed content
     */
    private String decompress(String compressedContent) {
        System.out.println("Decompressing message with " + compressionAlgorithm);
        
        if ("GZIP".equalsIgnoreCase(compressionAlgorithm)) {
            try {
                byte[] compressedBytes = Base64.getDecoder().decode(compressedContent);
                ByteArrayInputStream byteStream = new ByteArrayInputStream(compressedBytes);
                try (GZIPInputStream gzipStream = new GZIPInputStream(byteStream)) {
                    ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = gzipStream.read(buffer)) > 0) {
                        resultStream.write(buffer, 0, length);
                    }
                    return resultStream.toString();
                }
            } catch (Exception e) {
                System.err.println("Error decompressing data: " + e.getMessage());
                return compressedContent; // Return compressed content if decompression fails
            }
        } else if ("RLE".equalsIgnoreCase(compressionAlgorithm)) {
            // Simple Run-Length Decoding for demonstration
            return runLengthDecode(compressedContent);
        } else {
            // No decompression needed for space removal
            return compressedContent;
        }
    }
    
    /**
     * Get the compression ratio based on the algorithm and level.
     * 
     * @return The compression ratio (compressed size / original size)
     */
    private double getCompressionRatio() {
        // This is a simple estimation of the compression ratio
        if ("GZIP".equalsIgnoreCase(compressionAlgorithm)) {
            // Higher compression level means better compression (smaller ratio)
            return 0.5 - (compressionLevel - 1) * 0.05;
        } else if ("RLE".equalsIgnoreCase(compressionAlgorithm)) {
            return 0.7; // Assume RLE achieves about 30% compression
        } else {
            return 0.9; // Assume space removal achieves about 10% compression
        }
    }
    
    /**
     * Simple Run-Length Encoding implementation.
     * 
     * @param text The text to encode
     * @return The encoded text
     */
    private String runLengthEncode(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        
        StringBuilder result = new StringBuilder();
        char currentChar = text.charAt(0);
        int count = 1;
        
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) == currentChar) {
                count++;
            } else {
                result.append(count).append(currentChar);
                currentChar = text.charAt(i);
                count = 1;
            }
        }
        
        result.append(count).append(currentChar);
        return result.toString();
    }
    
    /**
     * Simple Run-Length Decoding implementation.
     * 
     * @param text The text to decode
     * @return The decoded text
     */
    private String runLengthDecode(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        
        StringBuilder result = new StringBuilder();
        int i = 0;
        
        while (i < text.length()) {
            // Find the count (one or more digits)
            int countEnd = i;
            while (countEnd < text.length() && Character.isDigit(text.charAt(countEnd))) {
                countEnd++;
            }
            
            if (countEnd == text.length()) {
                break; // Malformed RLE string
            }
            
            int count = Integer.parseInt(text.substring(i, countEnd));
            char c = text.charAt(countEnd);
            
            for (int j = 0; j < count; j++) {
                result.append(c);
            }
            
            i = countEnd + 1;
        }
        
        return result.toString();
    }
    
    /**
     * Remove spaces from the text as a simple compression method.
     * 
     * @param text The text to compress
     * @return The compressed text
     */
    private String removeSpaces(String text) {
        return text.replaceAll("\\s+", "");
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