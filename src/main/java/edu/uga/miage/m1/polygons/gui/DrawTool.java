package edu.uga.miage.m1.polygons.gui;

import java.util.ArrayList;
import java.util.List;
import edu.uga.miage.m1.polygons.gui.commands.ShapeCommand;

public class DrawTool {

    protected List<ShapeCommand> commands;

    public DrawTool() {
        commands = new ArrayList<>();
    }

    public void removeLastCommand(){
        if(!this.commands.isEmpty()){
            this.commands.remove(this.commands.size()-1);
        }
    }

    public void addCommand(ShapeCommand command) {
        commands.add(command);
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
