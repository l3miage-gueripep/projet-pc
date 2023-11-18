package edu.uga.miage.m1.polygons.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.JPanel;

import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

/**
 * Cette classe représente le panneau de dessin qui affiche les formes dessinées.
 */
public class DrawingPanel extends JPanel{

    private ArrayList<SimpleShape> currentlySelectedShapes = new ArrayList<>();

    public enum Mode {
        NONE, //clic = nothing
        DRAW, //clic = draw
        MOVE, //clic = move
        GROUP //clic = add to group
    }

    private JDrawingFrame jDrawingFrame;

    private Mode mode = Mode.DRAW;

    public DrawingPanel(JDrawingFrame jDrawingFrame) {
        super();
        this.jDrawingFrame = jDrawingFrame;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        // g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        for (SimpleShape shape : jDrawingFrame.getDrawnShapes()) {
            shape.draw(g2);
        }
    }

    public Mode getMode() {
        return mode;
    }
    public void setMode(Mode mode) {
        this.mode = mode;
    }
    public SimpleShape getShapeAtLocation(int x, int y){
        for(SimpleShape shape : jDrawingFrame.getDrawnShapes()){
            if(shape.isInside(x, y)){
                return shape;
            }
        }
        return null;
    }

    public void addShapeToSelection(SimpleShape shape){
        currentlySelectedShapes.add(shape);
    }

    public ArrayList<SimpleShape> getCurrentlySelectedShapes() {
        return currentlySelectedShapes;
    }
}
