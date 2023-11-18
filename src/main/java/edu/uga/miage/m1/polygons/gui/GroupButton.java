package edu.uga.miage.m1.polygons.gui;

import java.awt.Color;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonObject;
import javax.swing.JButton;

import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

public class GroupButton extends JButton{
    
    
    private ArrayList<SimpleShape> shapes;
    private static int numberOfGroups = 0;
    private int id;

    public GroupButton(String text) {
        super(text);
        this.id = numberOfGroups++;
        shapes = new ArrayList<SimpleShape>();
        setBackground(Color.WHITE);
    }

    public void addShape(SimpleShape shape) {
        shapes.add(shape);
    }

    public void removeShape(SimpleShape shape) {
        shapes.remove(shape);
    }

    public ArrayList<SimpleShape> getShapes() {
        return shapes;
    }

    public JsonObject getJsonObject(){
        var jsonObjectBuilder = Json.createObjectBuilder();
        jsonObjectBuilder.add("id", id);
        var jsonArrayBuilder = Json.createArrayBuilder();
        for(SimpleShape shape : shapes){
            jsonArrayBuilder.add(shape.getId());
        }
        jsonObjectBuilder.add("shapes", jsonArrayBuilder);
        return jsonObjectBuilder.build();
    }

}
