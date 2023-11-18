package edu.uga.miage.m1.polygons.gui.commands;

import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

public abstract class ShapeCommand implements Command{
    protected SimpleShape shape;
    protected int x;
    protected int y;

    public SimpleShape getShape(){
        return this.shape;
    }
}
