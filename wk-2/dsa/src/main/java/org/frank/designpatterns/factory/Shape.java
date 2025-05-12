package org.frank.designpatterns.factory;

/**
 * Shape interface that defines the common behavior for all shapes.
 */
public interface Shape {
    /**
     * Draw the shape.
     */
    void draw();
    
    /**
     * Calculate the area of the shape.
     * 
     * @return The area of the shape
     */
    double calculateArea();
}