package edu.uga.miage.m1.polygons.gui;

import java.util.ArrayList;
import java.util.List;

import edu.uga.miage.m1.polygons.gui.commands.Command;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

public class DrawTool {

    protected List<Command> commands;
    private JDrawingFrame jDrawingFrame;

    public DrawTool(JDrawingFrame jDrawingFrame) {
        commands = new ArrayList<>();
        this.jDrawingFrame = jDrawingFrame;
    }

    public Command getLastCommand(){
        if(!this.commands.isEmpty()){
            return this.commands.get(this.commands.size()-1);
        }
        return null;
    }

    public void removeLastCommand(){
        if(!this.commands.isEmpty()){
            this.commands.remove(this.commands.size()-1);
        }
    }

    public void addCommand(Command command) {
        commands.add(command);
    }

    public void play() {
        resetSelection();
        for (Command command : commands) {
            command.execute();
        }
        drawAllShapes();
    }

    private void resetSelection() {
        jDrawingFrame.resetGroupButtons();
        for(SimpleShape shape : jDrawingFrame.getDrawnShapes()){
            shape.setSelected(false);
        }
    }

    private void drawAllShapes(){
        for(SimpleShape shape : jDrawingFrame.getDrawnShapes()){
            shape.draw(jDrawingFrame.getPanelG2());
        }
    }

    public void reset() {
        commands.clear();
    }
    
    public List<Command> getCommands() {
        return commands;
    }
}
