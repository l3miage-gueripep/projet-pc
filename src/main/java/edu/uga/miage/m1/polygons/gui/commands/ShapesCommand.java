package edu.uga.miage.m1.polygons.gui.commands;

import java.util.ArrayList;

import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

public abstract class ShapesCommand implements Command {
    protected ArrayList<SimpleShape> shapes;

    public ArrayList<SimpleShape> getShapes(){
        return this.shapes;
    }
}
