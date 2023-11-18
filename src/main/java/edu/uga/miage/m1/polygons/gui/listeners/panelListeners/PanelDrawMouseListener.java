package edu.uga.miage.m1.polygons.gui.listeners.panelListeners;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.uga.miage.m1.polygons.gui.JDrawingFrame;
import edu.uga.miage.m1.polygons.gui.DrawingPanel.Mode;
import edu.uga.miage.m1.polygons.gui.commands.DrawShapeCommand;
import edu.uga.miage.m1.polygons.gui.shapes.Circle;
import edu.uga.miage.m1.polygons.gui.shapes.Cube;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;
import edu.uga.miage.m1.polygons.gui.shapes.Square;
import edu.uga.miage.m1.polygons.gui.shapes.Triangle;

public class PanelDrawMouseListener implements MouseListener {

    private JDrawingFrame jDrawingFrame;
    private static final Logger logger = Logger.getLogger(PanelDrawMouseListener.class.getName());

    public PanelDrawMouseListener(JDrawingFrame jDrawingFrame) {
        super();
        this.jDrawingFrame = jDrawingFrame;
    }

    @Override
    public void mouseClicked(MouseEvent evt) {
        if(jDrawingFrame.getPanel().getMode() != Mode.DRAW) return;
        var panel = jDrawingFrame.getPanel();
        var selected = jDrawingFrame.getShapeSelected();
        if (panel.contains(evt.getX(), evt.getY()) && selected != null) {
            Graphics2D g2 = jDrawingFrame.getPanelG2();
            SimpleShape shape = null;
            switch(selected) {
                case CIRCLE:
                    shape = new Circle(evt.getX(), evt.getY());
                    break;
                case TRIANGLE:
                    shape = new Triangle(evt.getX(), evt.getY());
                    break;
                case SQUARE:
                    shape = new Square(evt.getX(), evt.getY());
                    break;
                case CUBE:
                    shape = new Cube(evt.getX(), evt.getY());
                    break;
                default:
                    logger.log(Level.FINE, "No shape named {0}", selected);
            }
            DrawShapeCommand drawShapeCommand = new DrawShapeCommand(shape);

            var drawTool = jDrawingFrame.getDrawTool();
            drawTool.addCommand(drawShapeCommand);
            jDrawingFrame.getDrawnShapes().add(shape);
            drawTool.play();
        }
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
