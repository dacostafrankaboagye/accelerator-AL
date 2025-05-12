package org.frank.designpatterns.decorator;

/**
 * Decorator that adds transparency to a shape.
 * This is a "ConcreteDecorator" in the Decorator pattern.
 */
public class TransparencyDecorator extends ShapeDecorator {
    
    private double transparencyLevel; // 0.0 (fully opaque) to 1.0 (fully transparent)
    
    /**
     * Constructor for TransparencyDecorator.
     * 
     * @param decoratedShape The shape to decorate
     * @param transparencyLevel The level of transparency (0.0 to 1.0)
     * @throws IllegalArgumentException If the transparency level is not between 0.0 and 1.0
     */
    public TransparencyDecorator(Shape decoratedShape, double transparencyLevel) {
        super(decoratedShape);
        if (transparencyLevel < 0.0 || transparencyLevel > 1.0) {
            throw new IllegalArgumentException("Transparency level must be between 0.0 and 1.0");
        }
        this.transparencyLevel = transparencyLevel;
    }
    
    @Override
    public void draw() {
        decoratedShape.draw();
        applyTransparency();
    }
    
    @Override
    public String getDescription() {
        return decoratedShape.getDescription() + " with " + getTransparencyPercentage() + "% transparency";
    }
    
    /**
     * Apply transparency to the shape.
     */
    private void applyTransparency() {
        System.out.println("Applying " + getTransparencyPercentage() + "% transparency to the shape");
    }
    
    /**
     * Get the transparency level.
     * 
     * @return The transparency level (0.0 to 1.0)
     */
    public double getTransparencyLevel() {
        return transparencyLevel;
    }
    
    /**
     * Get the transparency percentage.
     * 
     * @return The transparency percentage (0 to 100)
     */
    public int getTransparencyPercentage() {
        return (int) (transparencyLevel * 100);
    }
}