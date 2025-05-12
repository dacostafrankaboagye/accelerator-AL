package org.frank.designpatterns.decorator;

/**
 * TextMessage class that implements the Message interface.
 * This is a "ConcreteComponent" in the Decorator pattern.
 */
public class TextMessage implements Message {
    
    private String content;
    private String recipient;
    
    /**
     * Constructor for TextMessage.
     * 
     * @param content The message content
     * @param recipient The recipient of the message
     */
    public TextMessage(String content, String recipient) {
        this.content = content;
        this.recipient = recipient;
    }
    
    @Override
    public String getContent() {
        return content;
    }
    
    @Override
    public void setContent(String content) {
        this.content = content;
    }
    
    @Override
    public boolean send() {
        // Simulate sending a text message
        System.out.println("Sending text message to " + recipient + ": " + content);
        return true;
    }
    
    @Override
    public String getDescription() {
        return "Text message to " + recipient;
    }
    
    @Override
    public int getSize() {
        // Return the size of the content in bytes
        return content.getBytes().length;
    }
    
    /**
     * Get the recipient of the message.
     * 
     * @return The recipient of the message
     */
    public String getRecipient() {
        return recipient;
    }
    
    /**
     * Set the recipient of the message.
     * 
     * @param recipient The recipient of the message
     */
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
}