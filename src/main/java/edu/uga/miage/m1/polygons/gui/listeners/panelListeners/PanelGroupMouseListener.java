package edu.uga.miage.m1.polygons.gui.listeners.panelListeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import edu.uga.miage.m1.polygons.gui.JDrawingFrame;
import edu.uga.miage.m1.polygons.gui.DrawingPanel.Mode;
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
        SimpleShape selectedShape = jDrawingFrame.getPanel().getShapeAtLocation(e.getX(), e.getY());
        if(selectedShape == null) return;
        selectedShape.toggleSelected();
        jDrawingFrame.getDrawTool().play();
        System.out.println("trouvé!!");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'mousePressed'");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'mouseReleased'");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'mouseEntered'");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'mouseExited'");
    }
}
