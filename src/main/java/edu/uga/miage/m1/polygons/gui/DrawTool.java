package edu.uga.miage.m1.polygons.gui;

import java.util.ArrayList;
import java.util.List;

import edu.uga.miage.m1.polygons.gui.commands.DrawShapeCommand;
import edu.uga.miage.m1.polygons.gui.commands.MoveShapeCommand;
import edu.uga.miage.m1.polygons.gui.commands.ShapeCommand;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

public class DrawTool {

    protected List<ShapeCommand> commands;
    private JDrawingFrame jDrawingFrame;

    public DrawTool(JDrawingFrame jDrawingFrame) {
        commands = new ArrayList<>();
        this.jDrawingFrame = jDrawingFrame;
    }

    public ShapeCommand getLastCommand(){
        if(!this.commands.isEmpty()){
            return this.commands.get(this.commands.size()-1);
        }
        return null;
    }

    public void removeLastCommand(){
        if(!this.commands.isEmpty()){
            var lastCommand = this.commands.get(this.commands.size()-1);
            this.commands.remove(this.commands.size()-1);
            if (lastCommand instanceof MoveShapeCommand) {
                SimpleShape movedShape = lastCommand.getShape();
                if (!hasAMoveCommand(movedShape)) {
                    setShapeMoved(movedShape, false);
                }
            }
        }
    }

    public boolean hasAMoveCommand(SimpleShape movedShape) {
        for (ShapeCommand command : commands) {
            if (command instanceof MoveShapeCommand) {
                SimpleShape otherShape = command.getShape();
                if (movedShape.equals(otherShape)) {
                    return true;
                }
            }
        }
        return false;
    }

    //change the move attribute of the DrawShapeCommand that draws the movedShape to the right value
    public void setShapeMoved(SimpleShape movedShape, boolean moved) {
        for (ShapeCommand command : commands) {
            if (command instanceof DrawShapeCommand) {
                SimpleShape drawnShape = command.getShape();
                if (movedShape.equals(drawnShape)) {
                    movedShape.setIsMoved(moved);
                    // ((DrawShapeCommand)command).setMoved(moved);
                    break;
                }
            }
        }
    }

    public void addCommand(ShapeCommand command) {
        commands.add(command);
        if(command instanceof MoveShapeCommand){
            setShapeMoved(command.getShape(), true);
        }
    }

    public void play() {
        jDrawingFrame.resetGroupButtons();
        for(SimpleShape shape : jDrawingFrame.getDrawnShapes()){
            shape.setSelected(false);
        }
        for (ShapeCommand command : commands) {
            command.execute();
        }

        for(SimpleShape shape : jDrawingFrame.getDrawnShapes()){
            shape.draw(jDrawingFrame.getPanelG2());
        }
    }

    public void reset() {
        commands.clear();
    }
    
    public List<ShapeCommand> getCommands() {
        return commands;
    }
}
