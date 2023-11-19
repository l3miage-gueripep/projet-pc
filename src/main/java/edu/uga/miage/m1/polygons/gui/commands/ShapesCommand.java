package edu.uga.miage.m1.polygons.gui.commands;

import java.util.List;

import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

public abstract class ShapesCommand implements Command {
    protected List<SimpleShape> shapes;

    public List<SimpleShape> getShapes(){
        return this.shapes;
    }
}
