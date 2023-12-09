package edu.uga.miage.m1.polygons.gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

public class GroupButton extends JButton{
    
    
    private transient ArrayList<SimpleShape> shapes;
    private static int numberOfGroups = 0;
    private int id;

    public GroupButton(String text) {
        super(text);
        this.id = numberOfGroups++;
        shapes = new ArrayList<>();
        setBackground(Color.WHITE);
    }

    public void addShape(SimpleShape shape) {
        shapes.add(shape);
    }

    public void removeShape(SimpleShape shape) {
        shapes.remove(shape);
    }

    public List<SimpleShape> getShapes() {
        return shapes;
    }

    public int getId() {
        return id;
    }
}
