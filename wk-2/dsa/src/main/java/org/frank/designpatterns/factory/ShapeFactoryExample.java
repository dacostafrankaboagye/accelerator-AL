package org.frank.designpatterns.factory;

/**
 * Example class demonstrating the usage of the ShapeFactory.
 */
public class ShapeFactoryExample {
    
    public static void main(String[] args) {
        // Create a shape factory
        ShapeFactory shapeFactory = new ShapeFactory();
        
        System.out.println("Shape Factory Example");
        System.out.println("---------------------");
        
        // Create shapes using the generic createShape method
        System.out.println("\nCreating shapes using the generic method:");
        
        try {
            // Create a circle
            Shape circle = shapeFactory.createShape("circle", 5.0);
            System.out.println("Created a circle:");
            circle.draw();
            System.out.println("Area: " + circle.calculateArea());
            
            // Create a square
            Shape square = shapeFactory.createShape("square", 4.0);
            System.out.println("\nCreated a square:");
            square.draw();
            System.out.println("Area: " + square.calculateArea());
            
            // Try to create an unsupported shape
            try {
                Shape triangle = shapeFactory.createShape("triangle", 3.0);
                triangle.draw();
            } catch (IllegalArgumentException e) {
                System.out.println("\nCaught exception: " + e.getMessage());
            }
            
        } catch (Exception e) {
            System.err.println("Error creating shapes: " + e.getMessage());
        }
        
        // Create shapes using the specific methods
        System.out.println("\nCreating shapes using specific methods:");
        
        // Create a circle
        Shape circle = shapeFactory.createCircle(7.5);
        System.out.println("Created a circle:");
        circle.draw();
        System.out.println("Area: " + circle.calculateArea());
        
        // Create a square
        Shape square = shapeFactory.createSquare(6.0);
        System.out.println("\nCreated a square:");
        square.draw();
        System.out.println("Area: " + square.calculateArea());
        
        // Demonstrate polymorphism
        System.out.println("\nDemonstrating polymorphism:");
        Shape[] shapes = new Shape[4];
        shapes[0] = shapeFactory.createCircle(2.0);
        shapes[1] = shapeFactory.createSquare(3.0);
        shapes[2] = shapeFactory.createShape("circle", 4.0);
        shapes[3] = shapeFactory.createShape("SQUARE", 5.0); // Note: case-insensitive
        
        for (Shape shape : shapes) {
            shape.draw();
            System.out.println("Area: " + shape.calculateArea());
            System.out.println();
        }
    }
}