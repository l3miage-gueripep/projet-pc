package edu.uga.miage.m1.polygons.gui.listeners;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import edu.uga.miage.m1.polygons.gui.JDrawingFrame;

public class CursorButtonListener implements ActionListener {
    private JDrawingFrame jDrawingFrame;
    private PanelMouseListener panelMouseListener;

    public void setPanelMouseListener(PanelMouseListener mouseListener) {
        this.panelMouseListener = mouseListener;
    }


    public CursorButtonListener(JDrawingFrame jDrawingFrame){
        super();
        this.jDrawingFrame = jDrawingFrame;
    }

    public void actionPerformed(ActionEvent evt) {
        var panel = jDrawingFrame.getPanel();
        panel.setCursor(new Cursor(Cursor.MOVE_CURSOR));
        jDrawingFrame.setShapeSelected(null);
        panelMouseListener.setDragShapesMode(true);
    }
}
