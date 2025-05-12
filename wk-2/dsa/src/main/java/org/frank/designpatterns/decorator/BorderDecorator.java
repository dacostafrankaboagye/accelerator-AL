package org.frank.designpatterns.decorator;

/**
 * Decorator that adds a border to a shape.
 * This is a "ConcreteDecorator" in the Decorator pattern.
 */
public class BorderDecorator extends ShapeDecorator {
    
    private int borderWidth;
    private String borderStyle;
    
    /**
     * Constructor for BorderDecorator.
     * 
     * @param decoratedShape The shape to decorate
     * @param borderWidth The width of the border in pixels
     * @param borderStyle The style of the border (e.g., "solid", "dashed", "dotted")
     */
    public BorderDecorator(Shape decoratedShape, int borderWidth, String borderStyle) {
        super(decoratedShape);
        this.borderWidth = borderWidth;
        this.borderStyle = borderStyle;
    }
    
    @Override
    public void draw() {
        decoratedShape.draw();
        drawBorder();
    }
    
    @Override
    public String getDescription() {
        return decoratedShape.getDescription() + " with " + borderStyle + " border of width " + borderWidth + "px";
    }
    
    /**
     * Draw the border around the shape.
     */
    private void drawBorder() {
        System.out.println("Drawing a " + borderStyle + " border with width " + borderWidth + "px");
    }
    
    /**
     * Get the border width.
     * 
     * @return The border width in pixels
     */
    public int getBorderWidth() {
        return borderWidth;
    }
    
    /**
     * Get the border style.
     * 
     * @return The border style
     */
    public String getBorderStyle() {
        return borderStyle;
    }
}