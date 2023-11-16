package edu.uga.miage.m1.polygons.gui;

import java.util.ArrayList;
import java.util.List;

import edu.uga.miage.m1.polygons.gui.commands.DrawShapeCommand;
import edu.uga.miage.m1.polygons.gui.commands.MoveShapeCommand;
import edu.uga.miage.m1.polygons.gui.commands.ShapeCommand;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

public class DrawTool {

    protected List<ShapeCommand> commands;

    public DrawTool() {
        commands = new ArrayList<>();
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
                boolean hasAnotherMoveCommand = false;
                for (ShapeCommand command : commands) {
                    if (command instanceof MoveShapeCommand) {
                        System.out.println();
                        SimpleShape otherShape = command.getShape();
                        if (movedShape.equals(otherShape)) {
                            hasAnotherMoveCommand = true;
                            break;
                        }
                    }
                }
                if (!hasAnotherMoveCommand) {
                    for (ShapeCommand command : commands) {
                        if (command instanceof DrawShapeCommand) {
                            SimpleShape drawnShape = command.getShape();
                            if (movedShape.equals(drawnShape)) {
                                ((DrawShapeCommand)command).setMoved(false);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    public void addCommand(ShapeCommand command) {
        commands.add(command);
        if (command instanceof MoveShapeCommand) {
            SimpleShape movedShape = command.getShape();
            for (ShapeCommand shapeCommand : commands) {
                if (shapeCommand instanceof DrawShapeCommand) {
                    SimpleShape drawnShape = shapeCommand.getShape();
                    if (movedShape.equals(drawnShape)) {
                        ((DrawShapeCommand)shapeCommand).setMoved(true);
                        break;
                    }
                }
            }
        }
    }

    public void play() {
        for (ShapeCommand command : commands) {
            command.execute();
        }
    }

    public void reset() {
        commands.clear();
    }
    
    public List<ShapeCommand> getCommands() {
        return commands;
    }
}
