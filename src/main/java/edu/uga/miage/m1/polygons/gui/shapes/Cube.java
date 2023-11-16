package edu.uga.miage.m1.polygons.gui.shapes;

import java.awt.Graphics2D;

import edu.uga.miage.m1.polygons.gui.persistence.Visitor;
import edu.uga.singleshape.CubePanel;

public class Cube implements SimpleShape {

    private int x;
    private int y;
    private int size = 50;

    CubePanel cube;

    public Cube(int x, int y) {
        this.x = x - size/2;
        this.y = y - size/2;
    }


    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void draw(Graphics2D g2) {
        Graphics2D g2d = (Graphics2D) g2;
        cube = new CubePanel(100, x, y);
        cube.paintComponent(g2d);
    }

    @Override
    public void setCoordinates(int x, int y) {
        this.x = x - size/2;
        this.y = y - size/2;
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public boolean isInside(int x, int y) {
        int[] verticesX = {this.x, this.x + size, this.x + size, this.x, this.x, this.x + size, this.x + size, this.x};
        int[] verticesY = {this.y, this.y, this.y + size, this.y + size, this.y, this.y, this.y + size, this.y + size};
        for (int i = 0, j = verticesX.length - 1; i < verticesX.length; j = i++) {
            if ((verticesY[i] > y) != (verticesY[j] > y) &&
                    (x < (verticesX[j] - verticesX[i]) * (y - verticesY[i]) / (verticesY[j] - verticesY[i]) + verticesX[i])) {
                return true;
            }
        }
        return false;
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
        return;
    }
    
}
