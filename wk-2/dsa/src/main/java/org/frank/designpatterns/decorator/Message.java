package org.frank.designpatterns.decorator;

/**
 * Message interface that defines the common behavior for all messages.
 * This is the "Component" in the Decorator pattern.
 */
public interface Message {
    
    /**
     * Get the content of the message.
     * 
     * @return The message content
     */
    String getContent();
    
    /**
     * Set the content of the message.
     * 
     * @param content The message content
     */
    void setContent(String content);
    
    /**
     * Send the message.
     * 
     * @return true if the message was sent successfully, false otherwise
     */
    boolean send();
    
    /**
     * Get a description of the message.
     * 
     * @return A description of the message
     */
    String getDescription();
    
    /**
     * Get the size of the message in bytes.
     * 
     * @return The size of the message in bytes
     */
    int getSize();
}