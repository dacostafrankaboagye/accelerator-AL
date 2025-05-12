package org.frank.designpatterns.decorator;

import java.util.Base64;

/**
 * Decorator that adds encryption to a message.
 * This is a "ConcreteDecorator" in the Decorator pattern.
 */
public class EncryptionDecorator extends MessageDecorator {
    
    private String encryptionAlgorithm;
    
    /**
     * Constructor for EncryptionDecorator.
     * 
     * @param decoratedMessage The message to decorate
     * @param encryptionAlgorithm The encryption algorithm to use
     */
    public EncryptionDecorator(Message decoratedMessage, String encryptionAlgorithm) {
        super(decoratedMessage);
        this.encryptionAlgorithm = encryptionAlgorithm;
    }
    
    @Override
    public String getContent() {
        // Return the decrypted content
        String encryptedContent = super.getContent();
        return decrypt(encryptedContent);
    }
    
    @Override
    public void setContent(String content) {
        // Encrypt the content before setting it
        String encryptedContent = encrypt(content);
        super.setContent(encryptedContent);
    }
    
    @Override
    public boolean send() {
        System.out.println("Sending encrypted message using " + encryptionAlgorithm + " algorithm");
        return super.send();
    }
    
    @Override
    public String getDescription() {
        return super.getDescription() + " with " + encryptionAlgorithm + " encryption";
    }
    
    @Override
    public int getSize() {
        // Encrypted content might be larger than the original
        return super.getSize() + getEncryptionOverhead();
    }
    
    /**
     * Encrypt the content.
     * 
     * @param content The content to encrypt
     * @return The encrypted content
     */
    private String encrypt(String content) {
        System.out.println("Encrypting message with " + encryptionAlgorithm);
        
        // This is a simple simulation of encryption using Base64 encoding
        // In a real application, you would use a proper encryption algorithm
        if ("Base64".equalsIgnoreCase(encryptionAlgorithm)) {
            return Base64.getEncoder().encodeToString(content.getBytes());
        } else if ("ROT13".equalsIgnoreCase(encryptionAlgorithm)) {
            return rot13(content);
        } else {
            // Default to a simple character shift for demonstration
            return shiftChars(content, 1);
        }
    }
    
    /**
     * Decrypt the content.
     * 
     * @param encryptedContent The encrypted content
     * @return The decrypted content
     */
    private String decrypt(String encryptedContent) {
        System.out.println("Decrypting message with " + encryptionAlgorithm);
        
        // This is a simple simulation of decryption
        if ("Base64".equalsIgnoreCase(encryptionAlgorithm)) {
            try {
                return new String(Base64.getDecoder().decode(encryptedContent));
            } catch (IllegalArgumentException e) {
                // If the content is not properly Base64 encoded, return it as is
                return encryptedContent;
            }
        } else if ("ROT13".equalsIgnoreCase(encryptionAlgorithm)) {
            return rot13(encryptedContent);
        } else {
            // Default to a simple character shift for demonstration
            return shiftChars(encryptedContent, -1);
        }
    }
    
    /**
     * Get the overhead added by encryption.
     * 
     * @return The overhead in bytes
     */
    private int getEncryptionOverhead() {
        // This is a simple estimation of the overhead
        if ("Base64".equalsIgnoreCase(encryptionAlgorithm)) {
            return (int) (super.getSize() * 0.33); // Base64 adds about 33% overhead
        } else {
            return 16; // Assume a fixed overhead for other algorithms
        }
    }
    
    /**
     * Apply ROT13 encryption/decryption (shift each letter by 13 places).
     * 
     * @param text The text to encrypt/decrypt
     * @return The encrypted/decrypted text
     */
    private String rot13(String text) {
        StringBuilder result = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (c >= 'a' && c <= 'z') {
                result.append((char) ('a' + (c - 'a' + 13) % 26));
            } else if (c >= 'A' && c <= 'Z') {
                result.append((char) ('A' + (c - 'A' + 13) % 26));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }
    
    /**
     * Shift each character in the text by the specified amount.
     * 
     * @param text The text to shift
     * @param shift The amount to shift
     * @return The shifted text
     */
    private String shiftChars(String text, int shift) {
        StringBuilder result = new StringBuilder();
        for (char c : text.toCharArray()) {
            result.append((char) (c + shift));
        }
        return result.toString();
    }
    
    /**
     * Get the encryption algorithm.
     * 
     * @return The encryption algorithm
     */
    public String getEncryptionAlgorithm() {
        return encryptionAlgorithm;
    }
}