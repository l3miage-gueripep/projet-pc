package edu.uga.miage.m1.polygons.gui.listeners;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import edu.uga.miage.m1.polygons.gui.JDrawingFrame;
import edu.uga.miage.m1.polygons.gui.DrawingPanel.Mode;

public class CursorButtonListener implements ActionListener {
    private JDrawingFrame jDrawingFrame;

    public CursorButtonListener(JDrawingFrame jDrawingFrame) {
        super();
        this.jDrawingFrame = jDrawingFrame;
    }

    public void actionPerformed(ActionEvent evt) {
        var panel = jDrawingFrame.getPanel();
        panel.setMode(Mode.MOVE);
        panel.setCursor(new Cursor(Cursor.MOVE_CURSOR));
        setPanelGroupButtonBackground();
        jDrawingFrame.deselectAllButtons();
        jDrawingFrame.getCursorButton().setBorderPainted(true);
        jDrawingFrame.setShapeSelected(null);
    }

    private void setPanelGroupButtonBackground(){
        var currentlySelectedGroupButton = jDrawingFrame.getCurrentlySelectedGroupButton();
        if(currentlySelectedGroupButton == null) return;
        currentlySelectedGroupButton.setBackground(Color.YELLOW);
    }
}
