package edu.uga.miage.m1.polygons.gui.listeners;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import javax.swing.JButton;
import edu.uga.miage.m1.polygons.gui.shapes.Shapes;
import edu.uga.miage.m1.polygons.gui.JDrawingFrame;
import edu.uga.miage.m1.polygons.gui.DrawingPanel.Mode;

public class ShapeButtonListener implements ActionListener {

    private JDrawingFrame jDrawingFrame;

    public ShapeButtonListener(JDrawingFrame jDrawingFrame) {
        super();
        this.jDrawingFrame = jDrawingFrame;
    }

    public void actionPerformed(ActionEvent evt) {
        jDrawingFrame.getPanel().setMode(Mode.DRAW);
        jDrawingFrame.setCurrentlySelectedGroupButton(null);
        var panel = jDrawingFrame.getPanel();
        panel.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        Iterator<Shapes> keys = jDrawingFrame.getShapeButtons().keySet().iterator();
        while (keys.hasNext()) {
            Shapes shape = keys.next();
            JButton btn = jDrawingFrame.getShapeButtons().get(shape);
            if (evt.getActionCommand().equals(shape.toString())) {
                btn.setBorderPainted(true);
                jDrawingFrame.setShapeSelected(shape);
            } else {
                btn.setBorderPainted(false);
            }
            btn.repaint();
        }
    }
}
