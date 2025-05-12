package org.frank.designpatterns.factory;

/**
 * Circle class that implements the Shape interface.
 */
public class Circle implements Shape {
    private double radius;
    
    /**
     * Constructor for Circle.
     * 
     * @param radius The radius of the circle
     */
    public Circle(double radius) {
        this.radius = radius;
    }
    
    /**
     * Get the radius of the circle.
     * 
     * @return The radius of the circle
     */
    public double getRadius() {
        return radius;
    }
    
    @Override
    public void draw() {
        System.out.println("Drawing a Circle with radius " + radius);
    }
    
    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }
}