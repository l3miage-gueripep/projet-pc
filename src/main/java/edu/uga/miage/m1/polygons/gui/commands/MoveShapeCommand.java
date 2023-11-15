package edu.uga.miage.m1.polygons.gui.commands;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

public class MoveShapeCommand implements ShapeCommand{
    private SimpleShape shape;
    private int x;
    private int y;

    public MoveShapeCommand(SimpleShape shape, int x, int y){
        this.shape = shape;
        this.x = x - shape.getSize()/2;
        this.y = y - shape.getSize()/2;
    }

    @Override
    public void execute() {
        this.shape.setCoordinates(x, y);
    }

    @Override
    public SimpleShape getShape() {
        return this.shape;
    } 
}
