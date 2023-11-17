package edu.uga.miage.m1.polygons.gui.listeners;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import edu.uga.miage.m1.polygons.gui.JDrawingFrame;
import edu.uga.miage.m1.polygons.gui.DrawingPanel.Mode;


public class GroupButtonListener implements ActionListener {
    private JDrawingFrame jDrawingFrame;

    public GroupButtonListener(JDrawingFrame jDrawingFrame) {
        super();
        this.jDrawingFrame = jDrawingFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var panel = jDrawingFrame.getPanel();
        panel.setMode(Mode.GROUP);
        panel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }


}
