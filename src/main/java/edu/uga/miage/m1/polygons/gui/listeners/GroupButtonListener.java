package edu.uga.miage.m1.polygons.gui.listeners;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import edu.uga.miage.m1.polygons.gui.GroupButton;
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
        GroupButton sourceButton = (GroupButton) e.getSource();
        jDrawingFrame.deselectAllButtons();
        boolean isAButtonSelected = jDrawingFrame.getCurrentlySelectedGroupButton() != null;
        boolean isAlreadySelected = isAButtonSelected && jDrawingFrame.getCurrentlySelectedGroupButton().equals(sourceButton);
        if(isAlreadySelected){
            var mode = panel.getMode();
            if(mode == Mode.MOVE){
                panel.setMode(Mode.GROUP);
                sourceButton.setBackground(Color.CYAN);
            }
            else{
                unselectSelectedShapes();
                jDrawingFrame.setCurrentlySelectedGroupButton(null);
                panel.setMode(Mode.NONE);
            }
        }
        else{
            if(isAButtonSelected){
                unselectSelectedShapes();
                jDrawingFrame.getCurrentlySelectedGroupButton().setBackground(Color.WHITE);
            }
            jDrawingFrame.setCurrentlySelectedGroupButton(sourceButton);
            toggleShapes(sourceButton.getShapes());
            panel.setMode(Mode.GROUP);
        }
        panel.setMode(Mode.GROUP);
        panel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        jDrawingFrame.getDrawTool().play();
    }

    private void unselectSelectedShapes(){
        var currentlySelectedGroupButton = jDrawingFrame.getCurrentlySelectedGroupButton();
        if(currentlySelectedGroupButton == null) return;
        toggleShapes(currentlySelectedGroupButton.getShapes());
    }

    private void toggleShapes(List<SimpleShape> shapes){
        for(SimpleShape shape : shapes){
            shape.toggleSelected();
        }
    }
}
