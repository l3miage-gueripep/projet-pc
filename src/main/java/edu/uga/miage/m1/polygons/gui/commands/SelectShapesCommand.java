package edu.uga.miage.m1.polygons.gui.commands;


import java.util.List;

import edu.uga.miage.m1.polygons.gui.GroupButton;
import edu.uga.miage.m1.polygons.gui.JDrawingFrame;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

public class SelectShapesCommand extends ShapeCommand{

    private List<SimpleShape> shapes;
    private List<SimpleShape> shapesGroup;
    private JDrawingFrame jDrawingFrame;

    public SelectShapesCommand(List<SimpleShape> shapes, int x, int y, List<SimpleShape> shapesGroup, JDrawingFrame jDrawingFrame){
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
        return null;
    }

    public List<SimpleShape> getShapes() {
        return shapes;
    }
}
