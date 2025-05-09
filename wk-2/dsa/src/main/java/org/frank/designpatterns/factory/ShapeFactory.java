package org.frank.designpatterns.factory;

/**
 * Factory class for creating different shapes based on a provided type string.
 */
public class ShapeFactory {
    
    /**
     * Create a shape based on the provided type.
     * 
     * @param shapeType The type of shape to create ("circle" or "square")
     * @param size The size parameter (radius for circle, side length for square)
     * @return The created shape, or null if the type is not supported
     */
    public Shape createShape(String shapeType, double size) {
        if (shapeType == null) {
            return null;
        }
        
        // Convert to lowercase for case-insensitive comparison
        String shapeTypeLower = shapeType.toLowerCase();

        return switch (shapeTypeLower) {
            case "circle" -> new Circle(size);
            case "square" -> new Square(size);
            default -> throw new IllegalArgumentException("Unsupported shape type: " + shapeType);
        };
    }
    
    /**
     * Create a circle with the specified radius.
     * 
     * @param radius The radius of the circle
     * @return A new Circle instance
     */
    public Shape createCircle(double radius) {
        return new Circle(radius);
    }
    
    /**
     * Create a square with the specified side length.
     * 
     * @param side The side length of the square
     * @return A new Square instance
     */
    public Shape createSquare(double side) {
        return new Square(side);
    }
}