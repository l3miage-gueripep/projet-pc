package edu.uga.miage.m1.polygons.gui.listeners.panellisteners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import edu.uga.miage.m1.polygons.gui.JDrawingFrame;
import edu.uga.miage.m1.polygons.gui.DrawingPanel.Mode;
import edu.uga.miage.m1.polygons.gui.commands.DrawShapeCommand;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

public class PanelDrawMouseListener implements MouseListener {
    private JDrawingFrame jDrawingFrame;
    public PanelDrawMouseListener(JDrawingFrame jDrawingFrame) {
        super();
        this.jDrawingFrame = jDrawingFrame;
    }

    @Override
    public void mouseClicked(MouseEvent evt) {
        if(jDrawingFrame.getPanel().getMode() != Mode.DRAW) return;
        var panel = jDrawingFrame.getPanel();
        var selected = jDrawingFrame.getShapeForm();
        if (panel.contains(evt.getX(), evt.getY()) && selected != null) {
            SimpleShape shape = jDrawingFrame.createShape(selected, evt.getX(), evt.getY());
            DrawShapeCommand drawShapeCommand = new DrawShapeCommand(shape);
            var drawTool = jDrawingFrame.getDrawTool();
            drawTool.addCommand(drawShapeCommand);
            jDrawingFrame.addDrawnShape(shape);
            drawTool.play();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // do nothing
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // do nothing
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {
        // do nothing
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // do nothing
    }
}
