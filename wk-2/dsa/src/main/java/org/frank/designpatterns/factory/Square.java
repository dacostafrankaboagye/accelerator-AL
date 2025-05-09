package org.frank.designpatterns.factory;

/**
 * Square class that implements the Shape interface.
 */
public class Square implements Shape {
    private double side;
    
    /**
     * Constructor for Square.
     * 
     * @param side The length of the side of the square
     */
    public Square(double side) {
        this.side = side;
    }
    
    /**
     * Get the side length of the square.
     * 
     * @return The side length of the square
     */
    public double getSide() {
        return side;
    }
    
    @Override
    public void draw() {
        System.out.println("Drawing a Square with side " + side);
    }
    
    @Override
    public double calculateArea() {
        return side * side;
    }
}