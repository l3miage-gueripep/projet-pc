package edu.uga.miage.m1.polygons.gui.shapes;

public enum Shapes {
    SQUARE("Square"),
    TRIANGLE("Triangle"),
    CIRCLE("Circle"),
    CUBE("Cube");

    private final String shapeName;

    Shapes(String shapeName) {
        this.shapeName = shapeName;
    }

    public String getShapeName() {
        return this.shapeName;
    }

    public static Shapes fromString(String shapeName) {
        for (Shapes shape : Shapes.values()) {
            if (shape.getShapeName().equalsIgnoreCase(shapeName)) {
                return shape;
            }
        }
        return null;
    }
}