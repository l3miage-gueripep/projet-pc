package edu.uga.miage.m1.polygons.gui.listeners.panelListeners;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import edu.uga.miage.m1.polygons.gui.DrawingPanel.Mode;
import edu.uga.miage.m1.polygons.gui.JDrawingFrame;
import edu.uga.miage.m1.polygons.gui.commands.MoveShapeCommand;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

public class PanelMoveMouseListener implements MouseListener {

    private JDrawingFrame jDrawingFrame;
    private SimpleShape movingShape;

    private final int onDragSizeChange = 10;

    public PanelMoveMouseListener(JDrawingFrame jDrawingFrame) {
        super();
        this.jDrawingFrame = jDrawingFrame;
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
        movingShape = null;
        movingShape = jDrawingFrame.getPanel().getShapeAtLocation(x, y);
        if(movingShape == null) return;
        changeShapeSize(movingShape, movingShape.getSize() + onDragSizeChange);
    }

    private void moveShape(int x, int y) {
        if(movingShape == null) return;

        var drawTool = jDrawingFrame.getDrawTool();
        // needs to do this before actually changing the on screen shape size to move the shape on the right place
        //changing shape size before repaint = visual glitches
        movingShape.setSize(movingShape.getSize() - onDragSizeChange);
        drawTool.addCommand(new MoveShapeCommand(movingShape, x, y));
        drawTool.play();
        jDrawingFrame.repaint();
        changeShapeSize(movingShape, movingShape.getSize());
        movingShape = null;   
    }

    private void changeShapeSize(SimpleShape shape, int newSize){
        Graphics2D g2 = (Graphics2D) jDrawingFrame.getPanel().getGraphics();
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
