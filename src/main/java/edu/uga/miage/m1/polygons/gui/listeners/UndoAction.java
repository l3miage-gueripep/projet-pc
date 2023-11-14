package edu.uga.miage.m1.polygons.gui.listeners;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import edu.uga.miage.m1.polygons.gui.DrawTool;
import edu.uga.miage.m1.polygons.gui.JDrawingFrame;

public class UndoAction extends AbstractAction {
    private JDrawingFrame jDrawingFrame;

    public UndoAction(String name, JDrawingFrame jDrawingFrame) {
        super(name);
        this.jDrawingFrame = jDrawingFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var drawTool = jDrawingFrame.getDrawTool();
        drawTool.removeLastCommand();
        var drawnShapes = jDrawingFrame.getDrawnShapes();
        if(!drawnShapes.isEmpty()){
            drawnShapes.remove(drawnShapes.size()-1);
        }
        jDrawingFrame.repaint();
        drawTool.play();
    }
}
