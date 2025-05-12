package org.frank.designpatterns.decorator;

/**
 * Shape interface that defines the common behavior for all shapes.
 * This is the "Component" in the Decorator pattern.
 */
public interface Shape {
    
    /**
     * Draw the shape.
     */
    void draw();
    
    /**
     * Get a description of the shape.
     * 
     * @return A description of the shape
     */
    String getDescription();
    
    /**
     * Calculate the area of the shape.
     * 
     * @return The area of the shape
     */
    double getArea();
    
    /**
     * Calculate the perimeter of the shape.
     * 
     * @return The perimeter of the shape
     */
    double getPerimeter();
}