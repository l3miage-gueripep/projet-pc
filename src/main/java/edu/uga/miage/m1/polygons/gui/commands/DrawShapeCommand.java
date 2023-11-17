package edu.uga.miage.m1.polygons.gui.commands;

import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;
import java.awt.Graphics2D;

public class DrawShapeCommand implements ShapeCommand {
    private SimpleShape shape;
    private Graphics2D g2;

    //storing the initial coordinates because the shape might move
    private int x;
    private int y;

    private boolean drawn = false;
    private boolean moved = false;

    public DrawShapeCommand(SimpleShape shape, Graphics2D g2){
        this.shape = shape;
        this.g2 = g2;
        this.x = shape.getX();
        this.y = shape.getY();
    }

    @Override
    public void execute() {
        if(!moved){
            this.shape.setCoordinates(x, y);
        }
        if (!drawn) {
            this.shape.draw(g2);
            // drawn = true;
        }
    }

    @Override
    public SimpleShape getShape() {
        return this.shape;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    } 
}
