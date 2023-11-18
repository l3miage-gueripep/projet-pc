package edu.uga.miage.m1.polygons.gui.commands;
import java.util.ArrayList;

import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

public class MoveShapesCommand extends ShapesCommand{
    private int vectorX;
    private int vectorY;

    public MoveShapesCommand(ArrayList<SimpleShape> shapes, int vectorX, int vectorY){
        this.shapes = shapes;
        this.vectorX = vectorX;
        this.vectorY = vectorY;
    }

    @Override
    public void execute() {
        for(SimpleShape shape : shapes){
            int newX = shape.getX() + vectorX - shape.getSize()/2;
            int newY = shape.getY() + vectorY - shape.getSize()/2;
            shape.setCoordinates(newX, newY);
        }
    }
}
