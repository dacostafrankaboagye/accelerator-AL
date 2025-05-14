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
        super.setContent(encrypt(decoratedMessage.getContent()));

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
        return "Alg -=== " + encryptionAlgorithm + " === " + content;
    }
    
    /**
     * Decrypt the content.
     * 
     * @param encryptedContent The encrypted content
     * @return The decrypted content
     */
    private String decrypt(String encryptedContent) {
        String prefix = "Alg -=== " + encryptionAlgorithm + " === ";
        if (encryptedContent.startsWith(prefix)) {
            return encryptedContent.substring(prefix.length());
        }
        return encryptedContent;
    }
    
    /**
     * Get the overhead added by encryption.
     * 
     * @return The overhead in bytes
     */
    private int getEncryptionOverhead() {
        return ("Alg -=== " + encryptionAlgorithm + " === ").getBytes().length;
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