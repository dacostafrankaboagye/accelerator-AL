package org.frank.designpatterns.decorator;

/**
 * Abstract decorator class for shapes.
 * This is the "Decorator" in the Decorator pattern.
 */
public abstract class ShapeDecorator implements Shape {
    
    /**
     * The shape being decorated.
     */
    protected Shape decoratedShape;
    
    /**
     * Constructor for ShapeDecorator.
     * 
     * @param decoratedShape The shape to decorate
     */
    public ShapeDecorator(Shape decoratedShape) {
        this.decoratedShape = decoratedShape;
    }
    
    @Override
    public void draw() {
        decoratedShape.draw();
    }
    
    @Override
    public String getDescription() {
        return decoratedShape.getDescription();
    }
    
    @Override
    public double getArea() {
        return decoratedShape.getArea();
    }
    
    @Override
    public double getPerimeter() {
        return decoratedShape.getPerimeter();
    }
}