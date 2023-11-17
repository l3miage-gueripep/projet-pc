package edu.uga.miage.m1.polygons.gui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.text.html.HTMLDocument.Iterator;

import edu.uga.miage.m1.polygons.gui.JDrawingFrame;
import edu.uga.miage.m1.polygons.gui.shapes.Shapes;

public class GroupButtonListener implements ActionListener {
    private JDrawingFrame jDrawingFrame;

    public GroupButtonListener(JDrawingFrame jDrawingFrame) {
        super();
        this.jDrawingFrame = jDrawingFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("salut!!");
        System.out.println(e);
        // Iterator<Shapes> keys = jDrawingFrame.getShapeButtons().keySet().iterator();
        // while (keys.hasNext()) {
        //     Shapes shape = keys.next();
        //     JButton btn = jDrawingFrame.getShapeButtons().get(shape);
        //     if (e.getActionCommand().equals(shape.toString())) {
        //         btn.setBorderPainted(true);
        //         jDrawingFrame.setShapeSelected(shape);
        //     } else {
        //         btn.setBorderPainted(false);
        //     }
        //     btn.repaint();
        // }
    }

    // public void actionPerformed(ActionEvent evt) {
    //     panelMouseListener.setDragShapesMode(false);
    //     var panel = jDrawingFrame.getPanel();
    //     panel.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
    //     Iterator<Shapes> keys = jDrawingFrame.getButtons().keySet().iterator();
    //     while (keys.hasNext()) {
    //         Shapes shape = keys.next();
    //         JButton btn = jDrawingFrame.getButtons().get(shape);
    //         if (evt.getActionCommand().equals(shape.toString())) {
    //             btn.setBorderPainted(true);
    //             jDrawingFrame.setShapeSelected(shape);
    //         } else {
    //             btn.setBorderPainted(false);
    //         }
    //         btn.repaint();
    //     }
    // }


}
