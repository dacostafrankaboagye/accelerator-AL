package org.frank.designpatterns.decorator;

/**
 * Decorator that adds color to a shape.
 * This is a "ConcreteDecorator" in the Decorator pattern.
 */
public class ColorDecorator extends ShapeDecorator {
    
    private String color;
    
    /**
     * Constructor for ColorDecorator.
     * 
     * @param decoratedShape The shape to decorate
     * @param color The color to apply to the shape
     */
    public ColorDecorator(Shape decoratedShape, String color) {
        super(decoratedShape);
        this.color = color;
    }
    
    @Override
    public void draw() {
        decoratedShape.draw();
        applyColor();
    }
    
    @Override
    public String getDescription() {
        return decoratedShape.getDescription() + " with " + color + " color";
    }
    
    /**
     * Apply color to the shape.
     */
    private void applyColor() {
        System.out.println("Applying " + color + " color to the shape");
    }
    
    /**
     * Get the color.
     * 
     * @return The color
     */
    public String getColor() {
        return color;
    }
}