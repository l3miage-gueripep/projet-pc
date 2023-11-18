package edu.uga.miage.m1.polygons.gui.listeners.panelListeners;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import edu.uga.miage.m1.polygons.gui.DrawingPanel.Mode;
import edu.uga.miage.m1.polygons.gui.JDrawingFrame;
import edu.uga.miage.m1.polygons.gui.commands.MoveShapeCommand;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

public class PanelMoveMouseListener implements MouseListener {

    private JDrawingFrame jDrawingFrame;
    private ArrayList<SimpleShape> movingShapes;

    private int initialX;
    private int initialY;

    private final int onDragSizeChange = 10;

    public PanelMoveMouseListener(JDrawingFrame jDrawingFrame) {
        super();
        this.jDrawingFrame = jDrawingFrame;
        this.movingShapes = new ArrayList<>();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(jDrawingFrame.getPanel().getMode() != Mode.MOVE) return;
        startMovingShape(e.getX(), e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(jDrawingFrame.getPanel().getMode() != Mode.MOVE) return;        
        moveShape(e.getX(), e.getY());
    }

    private void startMovingShape(int x, int y) {
        movingShapes.clear();
        var panel = jDrawingFrame.getPanel();
        var currentlySelectedGroupButton = jDrawingFrame.getCurrentlySelectedGroupButton();

        var currentlySelectedShapes = currentlySelectedGroupButton != null ? currentlySelectedGroupButton.getShapes() : null;
        var shapeAtLocation = panel.getShapeAtLocation(x, y);
        if(shapeAtLocation != null){
            movingShapes.add(shapeAtLocation);
        }
        if(movingShapes.isEmpty()) return;
        //detect if the shape is in a group
        if(currentlySelectedShapes != null && currentlySelectedShapes.contains(movingShapes.get(0))){
            currentlySelectedShapes.forEach(shape -> {
                if(shape != movingShapes.get(0))
                    movingShapes.add(shape);
                changeShapeSize(shape, shape.getSize() + onDragSizeChange);
            });
        }
        else{
            //moving just one shape
            changeShapeSize(movingShapes.get(0), movingShapes.get(0).getSize() + onDragSizeChange);
        }
        //we'll need this variables to move the shape on the right place
        this.initialX = movingShapes.get(0).getX();
        this.initialY = movingShapes.get(0).getY();
    }

    private void moveShape(int x, int y) {
        if(movingShapes.isEmpty()) return;

        var drawTool = jDrawingFrame.getDrawTool();
        // needs to do this before actually changing the on screen shape size to move the shape on the right place
        // changing shape size before repaint = visual glitches
        for(SimpleShape shape : movingShapes){
            shape.setSize(shape.getSize() - onDragSizeChange);
            var newX = shape.getX() + (x - initialX);
            var newY = shape.getY() + (y - initialY);
            drawTool.addCommand(new MoveShapeCommand(shape, newX, newY));
        }
        drawTool.play();
        jDrawingFrame.repaint();
        
        for(SimpleShape shape : movingShapes){
            changeShapeSize(shape, shape.getSize());
        }
        movingShapes.clear();   
    }

    private void changeShapeSize(SimpleShape shape, int newSize){
        Graphics2D g2 = jDrawingFrame.getPanelG2();
        shape.applySize(g2, newSize);
    }

    @Override
    public void mouseClicked(MouseEvent evt) {
        //do nothing
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
