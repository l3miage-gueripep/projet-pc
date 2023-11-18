package edu.uga.miage.m1.polygons.gui.shapes;

import java.awt.Graphics2D;

import edu.uga.miage.m1.polygons.gui.persistence.Visitor;
import edu.uga.singleshape.CubePanel;

public class Cube extends SimpleShape {
    CubePanel cube;

    public Cube(int x, int y) {
        super(x, y);
    }


    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void draw(Graphics2D g2) {
        cube = new CubePanel(100, x, y);
        cube.paintComponent(g2);
    }

    @Override
    public void setCoordinates(int x, int y) {
        this.x = x - size/2;
        this.y = y - size/2;
    }

    @Override
    public boolean isInside(int x, int y) {
            return x >= this.x - 30 && x <= (this.x + size +30) && y >= this.y - 30 && y <= (this.y + size + 30);
}


    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public void applySize(Graphics2D g2, int newSize) {
        //indisponible pour les cubes
        return;
    }
    
}
