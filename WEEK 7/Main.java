
abstract class Shape {
    private static int counter = 0;
    private final String shapeId;
    protected double xScale = 1.0;
    protected double yScale = 1.0;

    public Shape() {
        counter++;
        shapeId = "S" + counter;
    }

    public abstract double calculateArea();

    public void scale(double factor) {
        scale(factor, factor);
    }

    public void scale(double xFactor, double yFactor) {
        xScale *= xFactor;
        yScale *= yFactor;
    }

    public String getShapeId() {
        return shapeId;
    }
}

class CircleShape extends Shape {
    private double radius;

    public CircleShape(double radius) {
        this.radius = radius;
    }

    @Override
    public double calculateArea() {
        return Math.PI * radius * radius * xScale * yScale;
    }
}

class SquareShape extends Shape {
    private double side;

    public SquareShape(double side) {
        this.side = side;
    }

    @Override
    public double calculateArea() {
        return side * side * xScale * yScale;
    }
}

public class Main {
    public static void printArea(Shape s) {
        System.out.println("Area: " + s.calculateArea());
    }

    public static void main(String[] args) {
        CircleShape c = new CircleShape(5.0);
        SquareShape sq = new SquareShape(4.0);

        System.out.println("Circle ID: " + c.getShapeId());
        System.out.printf("Circle Area: %.2f%n", c.calculateArea());

        System.out.println("Square ID: " + sq.getShapeId());
        System.out.println("Square Area: " + sq.calculateArea());

        sq.scale(2.0);
        System.out.println("Scaled Square Area: " + sq.calculateArea());

        printArea(c);
        printArea(sq);
    }
}