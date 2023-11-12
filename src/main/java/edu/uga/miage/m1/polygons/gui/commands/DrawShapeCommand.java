package edu.uga.miage.m1.polygons.gui.commands;

import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;
import java.awt.Graphics2D;

public class DrawShapeCommand implements ShapeCommand {
    private SimpleShape shape;
    private Graphics2D g2;

    public DrawShapeCommand(SimpleShape shape, Graphics2D g2){
        this.shape = shape;
        this.g2 = g2;
    }

    @Override
    public void execute() {
        this.shape.draw(g2);
    }

    @Override
    public SimpleShape getShape() {
        return this.shape;
    } 
}
