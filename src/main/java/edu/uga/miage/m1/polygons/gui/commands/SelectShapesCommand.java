package edu.uga.miage.m1.polygons.gui.commands;

import java.awt.Graphics2D;
import java.util.ArrayList;

import edu.uga.miage.m1.polygons.gui.GroupButton;
import edu.uga.miage.m1.polygons.gui.JDrawingFrame;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

public class SelectShapesCommand extends ShapeCommand{

    private ArrayList<SimpleShape> shapes;
    private ArrayList<SimpleShape> shapesGroup;
    private JDrawingFrame jDrawingFrame;

    public SelectShapesCommand(ArrayList<SimpleShape> shapes, int x, int y, ArrayList<SimpleShape> shapesGroup, JDrawingFrame jDrawingFrame){
        this.shapes = shapes;
        this.x = x;
        this.y = y;
        this.jDrawingFrame = jDrawingFrame;
        this.shapesGroup = shapesGroup;
    }

    @Override
    public void execute() {
        for(SimpleShape shape : shapes){
            toggleSelectedIfGroupIsSelected(shape);
            addOrRemoveShapeFromGroup(shape);
        }
    }

    private void toggleSelectedIfGroupIsSelected(SimpleShape shape){
        GroupButton currentlySelectedGroupButton = jDrawingFrame.getCurrentlySelectedGroupButton();
        if(currentlySelectedGroupButton == null) return;
        boolean isGroupSelected = currentlySelectedGroupButton.getShapes() == shapesGroup;
        if(isGroupSelected){
            shape.toggleSelected();
        }
    }

    private void addOrRemoveShapeFromGroup(SimpleShape shape){
        if(shapesGroup.contains(shape)){
            shapesGroup.remove(shape);
        }
        else{
            shapesGroup.add(shape);
        }
    }

    @Override
    public SimpleShape getShape() {
        // TODO Auto-generated method stub
        return null;
    }

    public ArrayList<SimpleShape> getShapes() {
        return shapes;
    }
}
