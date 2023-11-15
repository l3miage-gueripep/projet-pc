package edu.uga.miage.m1.polygons.gui.listeners;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import edu.uga.miage.m1.polygons.gui.JDrawingFrame;
import edu.uga.miage.m1.polygons.gui.commands.DrawShapeCommand;

public class UndoAction extends AbstractAction {
    private JDrawingFrame jDrawingFrame;

    public UndoAction(String name, JDrawingFrame jDrawingFrame) {
        super(name);
        this.jDrawingFrame = jDrawingFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var drawTool = jDrawingFrame.getDrawTool();
        var lastCommand = drawTool.getLastCommand();
        var commands = drawTool.getCommands();
        drawTool.removeLastCommand();
        var drawnShapes = jDrawingFrame.getDrawnShapes();

        boolean isLastCommandDrawShapeCommand = !drawnShapes.isEmpty() && lastCommand instanceof DrawShapeCommand;
        if(isLastCommandDrawShapeCommand){
            drawnShapes.remove(drawnShapes.size()-1);
        }
        
        jDrawingFrame.repaint();
        drawTool.play();
        
    }
}
