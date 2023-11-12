package edu.uga.miage.m1.polygons.gui.commands;

import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

public interface ShapeCommand extends Command{
    public SimpleShape getShape();
}
