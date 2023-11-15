package edu.uga.miage.m1.polygons.gui.listeners;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.uga.miage.m1.polygons.gui.JDrawingFrame;
import edu.uga.miage.m1.polygons.gui.commands.DrawShapeCommand;
import edu.uga.miage.m1.polygons.gui.commands.MoveShapeCommand;
import edu.uga.miage.m1.polygons.gui.shapes.Circle;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;
import edu.uga.miage.m1.polygons.gui.shapes.Square;
import edu.uga.miage.m1.polygons.gui.shapes.Triangle;

public class PanelMouseListener implements MouseListener {

    private JDrawingFrame jDrawingFrame;
    private boolean dragShapesMode;
    private SimpleShape movingShape;

    private static final Logger logger = Logger.getLogger(PanelMouseListener.class.getName());

    public PanelMouseListener(JDrawingFrame jDrawingFrame) {
        super();
        this.jDrawingFrame = jDrawingFrame;
    }

    @Override
    public void mouseClicked(MouseEvent evt) {
        var panel = jDrawingFrame.getPanel();
        var selected = jDrawingFrame.getShapeSelected();
        if (panel.contains(evt.getX(), evt.getY()) && selected != null) {
            Graphics2D g2 = (Graphics2D) panel.getGraphics();
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
                default:
                    logger.log(Level.FINE, "No shape named {0}", selected);
            }
            DrawShapeCommand cDrawShape = new DrawShapeCommand(shape, g2);

            var drawTool = jDrawingFrame.getDrawTool();
            drawTool.addCommand(cDrawShape);
            jDrawingFrame.getDrawnShapes().add(shape);
            drawTool.play();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(!dragShapesMode) return;
        startMovingShape(e);
    }



    @Override
    public void mouseReleased(MouseEvent e) {
        if(!dragShapesMode) return;        
        moveShape(e.getX(), e.getY());
    }

    
    @Override
    public void mouseEntered(MouseEvent e) {
        // do nothing
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // do nothing
    }



    private void startMovingShape(MouseEvent e) {
        movingShape = null;
        for(SimpleShape shape : jDrawingFrame.getDrawnShapes()){
            if(shape.isInside(e.getX(), e.getY())){
                movingShape = shape;
            }
        }
    }

    private void moveShape(int x, int y) {
        if(movingShape == null) return;
        var drawTool = jDrawingFrame.getDrawTool();
        drawTool.addCommand(new MoveShapeCommand(movingShape, x, y));
        drawTool.play();
        jDrawingFrame.repaint();
        movingShape = null;
    }



    public void setDragShapesMode(boolean dragShapesMode) {
        this.dragShapesMode = dragShapesMode;
    }
}
