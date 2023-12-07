package edu.uga.miage.m1.polygons.gui.commands;

import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

public class DrawShapeCommand extends ShapeCommand {

    public DrawShapeCommand(SimpleShape shape){
        this(shape, true);
    }

    public DrawShapeCommand(SimpleShape shape, boolean canCancel){
        this.shape = shape;
        this.x = shape.getX();
        this.y = shape.getY();
        this.canCancel = canCancel;
    }

    @Override
    public void execute() {
        this.shape.setCoordinates(x, y);
    }
}
