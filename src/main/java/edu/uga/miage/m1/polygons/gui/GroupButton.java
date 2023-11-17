package edu.uga.miage.m1.polygons.gui;

import java.util.ArrayList;

import javax.swing.JButton;

import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

public class GroupButton extends JButton{
    
    
    private ArrayList<SimpleShape> shapes;

    public GroupButton(String text) {
        super(text);
        shapes = new ArrayList<SimpleShape>();
    }

    public void addShape(SimpleShape shape) {
        shapes.add(shape);
    }

    public void removeShape(SimpleShape shape) {
        shapes.remove(shape);
    }

    public ArrayList<SimpleShape> getShapes() {
        return shapes;
    }

}
