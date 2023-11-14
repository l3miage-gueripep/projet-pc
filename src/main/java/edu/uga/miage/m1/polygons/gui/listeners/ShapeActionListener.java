package edu.uga.miage.m1.polygons.gui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import javax.swing.JButton;
import edu.uga.miage.m1.polygons.gui.shapes.Shapes;
import edu.uga.miage.m1.polygons.gui.JDrawingFrame;

public class ShapeActionListener implements ActionListener {

    private JDrawingFrame jDrawingFrame;

    public ShapeActionListener(JDrawingFrame jDrawingFrame) {
        super();
        this.jDrawingFrame = jDrawingFrame;
    }

    public void actionPerformed(ActionEvent evt) {
        Iterator<Shapes> keys = jDrawingFrame.getButtons().keySet().iterator();
        while (keys.hasNext()) {
            Shapes shape = keys.next();
            JButton btn = jDrawingFrame.getButtons().get(shape);
            if (evt.getActionCommand().equals(shape.toString())) {
                btn.setBorderPainted(true);
                jDrawingFrame.setSelected(shape);
            } else {
                btn.setBorderPainted(false);
            }
            btn.repaint();
        }
    }
}
