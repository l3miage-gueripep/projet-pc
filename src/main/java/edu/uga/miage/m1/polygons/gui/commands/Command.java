package edu.uga.miage.m1.polygons.gui.commands;

public abstract class Command {
    protected boolean canCancel = true;
    public abstract void execute();

    public boolean getCanCancel(){
        return this.canCancel;
    }
}
