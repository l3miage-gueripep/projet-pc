package edu.uga.miage.m1.polygons.gui.shapes;

import java.awt.Graphics2D;

import edu.uga.miage.m1.polygons.gui.persistence.StringVisitor;
import edu.uga.miage.m1.polygons.gui.persistence.Visitor;
import edu.uga.singleshape.CubePanel;

public class Cube extends SimpleShape {
    CubePanel cubeShape;

    public Cube(int x, int y) {
        super(x, y);
    }


    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    



    @Override
    public void draw(Graphics2D g2) {
        cubeShape = new CubePanel(100, x, y);
        cubeShape.paintComponent(g2);
    }

    @Override
    public void setCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean isInside(int x, int y) {
        return x >= this.x - 30 && x <= (this.x + size +30) && y >= this.y - 30 && y <= (this.y + size + 30);
    }

    @Override
    public void applySize(Graphics2D g2, int newSize) {
        //not implemented
    }


    @Override
    public String acceptString(StringVisitor visitor) {
        return visitor.visit(this);
    }
    
}
