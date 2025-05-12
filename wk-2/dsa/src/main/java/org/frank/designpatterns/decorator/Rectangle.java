package org.frank.designpatterns.decorator;

/**
 * Rectangle class that implements the Shape interface.
 * This is a "ConcreteComponent" in the Decorator pattern.
 */
public class Rectangle implements Shape {
    private double width;
    private double height;
    
    /**
     * Constructor for Rectangle.
     * 
     * @param width The width of the rectangle
     * @param height The height of the rectangle
     */
    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }
    
    @Override
    public void draw() {
        System.out.println("Drawing a Rectangle with width " + width + " and height " + height);
    }
    
    @Override
    public String getDescription() {
        return "Rectangle with width " + width + " and height " + height;
    }
    
    @Override
    public double getArea() {
        return width * height;
    }
    
    @Override
    public double getPerimeter() {
        return 2 * (width + height);
    }
    
    /**
     * Get the width of the rectangle.
     * 
     * @return The width of the rectangle
     */
    public double getWidth() {
        return width;
    }
    
    /**
     * Get the height of the rectangle.
     * 
     * @return The height of the rectangle
     */
    public double getHeight() {
        return height;
    }
}