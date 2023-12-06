package edu.uga.miage.m1.polygons.gui.shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import edu.uga.miage.m1.polygons.gui.persistence.Visitor;


public class Square extends SimpleShape {
    private Rectangle2D rectangle;

    public Square(int x, int y) {
        super(x, y);
        this.rectangle = new Rectangle2D.Double(this.x, this.y, size, size);
    }


    public void draw(Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint gradient = new GradientPaint(x, y, Color.BLUE, (float) x + size, y, Color.WHITE);
        g2.setPaint(gradient);
        g2.fill(rectangle);
        BasicStroke wideStroke = new BasicStroke(2.0f);
        g2.setColor(super.getBorderColor());
        g2.setStroke(wideStroke);
        g2.draw(rectangle);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public boolean isInside(int x, int y) {
        return rectangle.contains(x, y);
    }

    @Override
    public void setCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
        this.rectangle.setRect(x, y, size, size);
    }

    public void applySize(Graphics2D g2, int size) {
        int halfDifference = 0;
        if(size > this.size){
            halfDifference = (size - this.size)/2;
        }
        this.size = size;
        this.rectangle.setRect((double)x - halfDifference,(double) y - halfDifference, size, size);
        draw(g2);
    }
}
