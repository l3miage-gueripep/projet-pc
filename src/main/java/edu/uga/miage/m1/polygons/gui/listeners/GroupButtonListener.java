package edu.uga.miage.m1.polygons.gui.listeners;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import edu.uga.miage.m1.polygons.gui.JDrawingFrame;
import edu.uga.miage.m1.polygons.gui.DrawingPanel.Mode;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;


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
        System.out.println("Grouping shapes");
        if(jDrawingFrame.getCurrentlySelectedGroupButton() != null && jDrawingFrame.getCurrentlySelectedGroupButton().equals(jDrawingFrame.getGroupButtons().get(0))){
            jDrawingFrame.setCurrentlySelectedGroupButton(null);
        }
        else{
            jDrawingFrame.setCurrentlySelectedGroupButton(jDrawingFrame.getGroupButtons().get(0));
        }
        for(SimpleShape shape : jDrawingFrame.getGroupButtons().get(0).getShapes()){
            
            shape.toggleSelected();
            jDrawingFrame.getDrawTool().play();
        }
    }
}
