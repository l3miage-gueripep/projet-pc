package edu.uga.miage.m1.polygons.gui.listeners.imports;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleAbstractTypeResolver;

import edu.uga.miage.m1.polygons.gui.DrawTool;
import edu.uga.miage.m1.polygons.gui.GroupButton;
import edu.uga.miage.m1.polygons.gui.JDrawingFrame;
import edu.uga.miage.m1.polygons.gui.commands.DrawShapeCommand;
import edu.uga.miage.m1.polygons.gui.commands.SelectShapesCommand;
import edu.uga.miage.m1.polygons.gui.json.Export;
import edu.uga.miage.m1.polygons.gui.json.GroupData;
import edu.uga.miage.m1.polygons.gui.json.ShapeData;
import edu.uga.miage.m1.polygons.gui.shapes.Shapes;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

public class JsonImportActionListener implements ActionListener {
    JDrawingFrame jDrawingFrame;

    public JsonImportActionListener(JDrawingFrame jDrawingFrame){
        super();
        this.jDrawingFrame = jDrawingFrame;
    }

    public void actionPerformed(ActionEvent evt) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            Export export = objectMapper.readValue(new File("exports/export.json"), Export.class);
            drawShapes(export.getShapes());
            setGroups(export.getGroups());
            DrawTool drawTool = jDrawingFrame.getDrawTool();
            drawTool.play();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void drawShapes(List<ShapeData> shapes){
        for(ShapeData shape : shapes){
            Shapes shapeForm = Shapes.fromString(shape.getType());
            SimpleShape simpleShape = jDrawingFrame.createShape(shapeForm, shape.getX() + 25, shape.getY() + 25);
            DrawShapeCommand drawShapeCommand = new DrawShapeCommand(simpleShape, false);
            jDrawingFrame.getDrawTool().addCommand(drawShapeCommand);
            jDrawingFrame.addDrawnShape(simpleShape);

        }
    }

    private void setGroups(List<GroupData> group){
        DrawTool drawTool = jDrawingFrame.getDrawTool();
        for(GroupData groupData : group){
            GroupButton groupButton = jDrawingFrame.getGroupButton(groupData.getId());
            List<SimpleShape> shapes = new ArrayList<>();
            for(Integer shapeId : groupData.getShapes()){
                Optional<SimpleShape> shapeOptional = jDrawingFrame.getDrawnShapes().stream()
                    .filter(shape -> shape.getId() == shapeId)
                    .findFirst();
                if (shapeOptional.isPresent()) {
                    shapes.add(shapeOptional.get());
                }
            }
            SelectShapesCommand selectShapesCommand = new SelectShapesCommand(shapes, groupButton.getShapes(), jDrawingFrame, false);
            drawTool.addCommand(selectShapesCommand);
        }
    }

}