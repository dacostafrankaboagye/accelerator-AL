package org.frank.designpatterns.decorator;

/**
 * Example class demonstrating the usage of the Message decorators.
 */
public class MessageDecoratorExample {
    
    public static void main(String[] args) {
        System.out.println("Message Decorator Example");
        System.out.println("------------------------");
        
        // Example 1: Basic message without decoration
        System.out.println("\nExample 1: Basic message without decoration");
        
        Message message = new TextMessage("Hello, this is a test message.", "user@example.com");
        System.out.println("Message Description: " + message.getDescription());
        System.out.println("Message Content: " + message.getContent());
        System.out.println("Message Size: " + message.getSize() + " bytes");
        message.send();
        
        // Example 2: Message with encryption
        System.out.println("\nExample 2: Message with encryption");
        
        Message encryptedMessage = new EncryptionDecorator(
                new TextMessage("This message is confidential.", "secure@example.com"),
                "Base64");
        
        System.out.println("Encrypted Message Description: " + encryptedMessage.getDescription());
        System.out.println("Encrypted Message Content (decrypted for display): " + encryptedMessage.getContent());
        System.out.println("Encrypted Message Size: " + encryptedMessage.getSize() + " bytes");
        encryptedMessage.send();
        
        // Example 3: Message with compression
        System.out.println("\nExample 3: Message with compression");
        
        Message compressedMessage = new CompressionDecorator(
                new TextMessage("This is a very long message that will be compressed to save bandwidth. " +
                        "Compression is especially useful for large messages with repetitive content. " +
                        "The longer the message, the more effective compression becomes.", "recipient@example.com"),
                "GZIP", 7);
        
        System.out.println("Compressed Message Description: " + compressedMessage.getDescription());
        System.out.println("Compressed Message Content (decompressed for display): " + compressedMessage.getContent());
        System.out.println("Compressed Message Size: " + compressedMessage.getSize() + " bytes");
        compressedMessage.send();
        
        // Example 4: Message with multiple decorators
        System.out.println("\nExample 4: Message with multiple decorators");
        
        // First compress, then encrypt
        Message secureCompressedMessage = new EncryptionDecorator(
                new CompressionDecorator(
                        new TextMessage("This message is both compressed and encrypted for maximum security and efficiency.",
                                "important@example.com"),
                        "GZIP"),
                "ROT13");
        
        System.out.println("Secure Compressed Message Description: " + secureCompressedMessage.getDescription());
        System.out.println("Secure Compressed Message Content (processed for display): " + 
                secureCompressedMessage.getContent());
        System.out.println("Secure Compressed Message Size: " + secureCompressedMessage.getSize() + " bytes");
        secureCompressedMessage.send();
        
        // Example 5: Different order of decorators
        System.out.println("\nExample 5: Different order of decorators");
        
        // First encrypt, then compress
        Message compressedSecureMessage = new CompressionDecorator(
                new EncryptionDecorator(
                        new TextMessage("This message is encrypted first, then compressed. " +
                                "The order of decorators can affect the result.",
                                "test@example.com"),
                        "Base64"),
                "RLE");
        
        System.out.println("Compressed Secure Message Description: " + compressedSecureMessage.getDescription());
        System.out.println("Compressed Secure Message Content (processed for display): " +
                compressedSecureMessage.getContent());
//        System.out.println("Compressed Secure Message Size: " + compressedSecureMessage.getSize() + " bytes");
//        compressedSecureMessage.send();
        
        // Example 6: Demonstrating dynamic nature of decorators
        System.out.println("\nExample 6: Demonstrating dynamic nature of decorators");
        
        // Create a basic message
        Message dynamicMessage = new TextMessage("This message will be decorated dynamically.", "dynamic@example.com");
        System.out.println("Original Message: " + dynamicMessage.getDescription());
        
        // Add encryption at runtime
        dynamicMessage = new EncryptionDecorator(dynamicMessage, "ROT13");
        System.out.println("After adding encryption: " + dynamicMessage.getDescription());
        
        // Add compression at runtime
        dynamicMessage = new CompressionDecorator(dynamicMessage, "RLE", 9);
        System.out.println("After adding compression: " + dynamicMessage.getDescription());
        
        // Send the fully decorated message
        dynamicMessage.send();
        
        System.out.println("\nDecorator Pattern Benefits:");
        System.out.println("1. Adds responsibilities to objects dynamically at runtime");
        System.out.println("2. Provides a flexible alternative to subclassing for extending functionality");
        System.out.println("3. Allows for a combination of behaviors through multiple decorators");
        System.out.println("4. Follows the Single Responsibility Principle by separating concerns");
        System.out.println("5. Follows the Open/Closed Principle by allowing extension without modification");
    }
}