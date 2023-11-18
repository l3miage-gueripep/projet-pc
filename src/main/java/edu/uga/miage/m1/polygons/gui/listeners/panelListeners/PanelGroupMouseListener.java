package edu.uga.miage.m1.polygons.gui.listeners.panelListeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import edu.uga.miage.m1.polygons.gui.JDrawingFrame;
import edu.uga.miage.m1.polygons.gui.DrawingPanel.Mode;
import edu.uga.miage.m1.polygons.gui.commands.SelectShapesCommand;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

public class PanelGroupMouseListener implements MouseListener {
    private JDrawingFrame jDrawingFrame;

    public PanelGroupMouseListener(JDrawingFrame jDrawingFrame) {
        super();
        this.jDrawingFrame = jDrawingFrame;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(jDrawingFrame.getPanel().getMode() != Mode.GROUP) return;
        SimpleShape clickedShape = jDrawingFrame.getPanel().getShapeAtLocation(e.getX(), e.getY());
        if(clickedShape == null) return;

        var drawTool = jDrawingFrame.getDrawTool();
        //create a list of the single shape clicked
        ArrayList<SimpleShape> shapes = new ArrayList<>();
        shapes.add(clickedShape);

        ArrayList<SimpleShape> selectedShapes = jDrawingFrame.getCurrentlySelectedGroupButton().getShapes();
        drawTool.addCommand(new SelectShapesCommand(shapes, e.getX(), e.getY(), selectedShapes, jDrawingFrame));
        jDrawingFrame.resetGroupButtons();
        drawTool.play();
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
