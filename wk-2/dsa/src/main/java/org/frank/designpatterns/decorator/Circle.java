package org.frank.designpatterns.decorator;

/**
 * Circle class that implements the Shape interface.
 * This is a "ConcreteComponent" in the Decorator pattern.
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
    
    @Override
    public void draw() {
        System.out.println("Drawing a Circle with radius " + radius);
    }
    
    @Override
    public String getDescription() {
        return "Circle with radius " + radius;
    }
    
    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }
    
    @Override
    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }
    
    /**
     * Get the radius of the circle.
     * 
     * @return The radius of the circle
     */
    public double getRadius() {
        return radius;
    }
}