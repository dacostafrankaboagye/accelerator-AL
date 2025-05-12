package org.frank.designpatterns.decorator;

/**
 * Abstract decorator class for messages.
 * This is the "Decorator" in the Decorator pattern.
 */
public abstract class MessageDecorator implements Message {
    
    /**
     * The message being decorated.
     */
    protected Message decoratedMessage;
    
    /**
     * Constructor for MessageDecorator.
     * 
     * @param decoratedMessage The message to decorate
     */
    public MessageDecorator(Message decoratedMessage) {
        this.decoratedMessage = decoratedMessage;
    }
    
    @Override
    public String getContent() {
        return decoratedMessage.getContent();
    }
    
    @Override
    public void setContent(String content) {
        decoratedMessage.setContent(content);
    }
    
    @Override
    public boolean send() {
        return decoratedMessage.send();
    }
    
    @Override
    public String getDescription() {
        return decoratedMessage.getDescription();
    }
    
    @Override
    public int getSize() {
        return decoratedMessage.getSize();
    }
}