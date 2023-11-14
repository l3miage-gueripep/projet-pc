package edu.uga.miage.m1.polygons.gui.events;

import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.AbstractAction;

import edu.uga.miage.m1.polygons.gui.DrawTool;

public class UndoAction extends AbstractAction {
    private DrawTool drawTool;
    private List<Shape> drawnShapes;

    public UndoAction(String name, DrawTool drawTool, List<Shape> drawnShapes) {
        super(name);
        this.drawTool = drawTool;
        this.drawnShapes = drawnShapes;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        drawTool.removeLastCommand();
        if(!drawnShapes.isEmpty()){
            drawnShapes.remove(drawnShapes.size()-1);
        }
        repaint();
        drawTool.play();
    }
}
