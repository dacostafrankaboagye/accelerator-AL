package org.frank.designpatterns.decorator;

/**
 * Example class demonstrating the usage of the Shape decorators.
 */
public class ShapeDecoratorExample {
    
    public static void main(String[] args) {
        System.out.println("Shape Decorator Example");
        System.out.println("----------------------");
        
        // Example 1: Basic shapes without decoration
        System.out.println("\nExample 1: Basic shapes without decoration");
        
        Shape circle = new Circle(5.0);
        System.out.println("Circle Description: " + circle.getDescription());
        circle.draw();
        System.out.println("Area: " + circle.getArea());
        System.out.println("Perimeter: " + circle.getPerimeter());
        
        Shape rectangle = new Rectangle(4.0, 6.0);
        System.out.println("\nRectangle Description: " + rectangle.getDescription());
        rectangle.draw();
        System.out.println("Area: " + rectangle.getArea());
        System.out.println("Perimeter: " + rectangle.getPerimeter());
        
        // Example 2: Shapes with a single decorator
        System.out.println("\nExample 2: Shapes with a single decorator");
        
        Shape circleWithBorder = new BorderDecorator(circle, 2, "solid");
        System.out.println("Circle with Border Description: " + circleWithBorder.getDescription());
        circleWithBorder.draw();
        
        Shape rectangleWithColor = new ColorDecorator(rectangle, "blue");
        System.out.println("\nRectangle with Color Description: " + rectangleWithColor.getDescription());
        rectangleWithColor.draw();
        
        // Example 3: Shapes with multiple decorators
        System.out.println("\nExample 3: Shapes with multiple decorators");
        
        // Circle with border and color
        Shape decoratedCircle = new ColorDecorator(
                new BorderDecorator(circle, 2, "dashed"),
                "red");
        
        System.out.println("Decorated Circle Description: " + decoratedCircle.getDescription());
        decoratedCircle.draw();
        System.out.println("Area (unchanged): " + decoratedCircle.getArea());
        System.out.println("Perimeter (unchanged): " + decoratedCircle.getPerimeter());
        
        // Rectangle with color, border, and transparency
        Shape decoratedRectangle = new TransparencyDecorator(
                new BorderDecorator(
                        new ColorDecorator(rectangle, "green"),
                        3, "dotted"),
                0.5);
        
        System.out.println("\nDecorated Rectangle Description: " + decoratedRectangle.getDescription());
        decoratedRectangle.draw();
        System.out.println("Area (unchanged): " + decoratedRectangle.getArea());
        System.out.println("Perimeter (unchanged): " + decoratedRectangle.getPerimeter());
        
        // Example 4: Demonstrating the flexibility of decorators
        System.out.println("\nExample 4: Demonstrating the flexibility of decorators");
        
        // Create a circle with two borders of different styles
        Shape circleWithTwoBorders = new BorderDecorator(
                new BorderDecorator(circle, 5, "solid"),
                2, "dashed");
        
        System.out.println("Circle with Two Borders Description: " + circleWithTwoBorders.getDescription());
        circleWithTwoBorders.draw();
        
        // Create a rectangle with the same color applied twice (just for demonstration)
        Shape rectangleWithTwoColors = new ColorDecorator(
                new ColorDecorator(rectangle, "yellow"),
                "orange");
        
        System.out.println("\nRectangle with Two Colors Description: " + rectangleWithTwoColors.getDescription());
        rectangleWithTwoColors.draw();
        
        System.out.println("\nDecorator Pattern Benefits:");
        System.out.println("1. Adds responsibilities to objects dynamically at runtime");
        System.out.println("2. Provides a flexible alternative to subclassing for extending functionality");
        System.out.println("3. Allows for a combination of behaviors through multiple decorators");
        System.out.println("4. Follows the Single Responsibility Principle by separating concerns");
        System.out.println("5. Follows the Open/Closed Principle by allowing extension without modification");
    }
}