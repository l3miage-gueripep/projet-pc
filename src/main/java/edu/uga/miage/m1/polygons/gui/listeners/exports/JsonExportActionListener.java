package edu.uga.miage.m1.polygons.gui.listeners.exports;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import edu.uga.miage.m1.polygons.gui.GroupButton;
import edu.uga.miage.m1.polygons.gui.JDrawingFrame;
import edu.uga.miage.m1.polygons.gui.persistence.JSonVisitor;
import edu.uga.miage.m1.polygons.gui.persistence.Visitable;

public class JsonExportActionListener implements ActionListener {

    private static final Logger logger = Logger.getLogger(JsonExportActionListener.class.getName());
    private static final String GENERIC_ERROR_MESSAGE = "An error occured while exporting the JSON file";

    JDrawingFrame jDrawingFrame;

    public JsonExportActionListener(JDrawingFrame jDrawingFrame){
        super();
        this.jDrawingFrame = jDrawingFrame;
    }

    private JSonVisitor jSonVisitor = new JSonVisitor();
    JsonArrayBuilder jsonArray = Json.createArrayBuilder();
    public void actionPerformed(ActionEvent evt) {
        for(Visitable shape : jDrawingFrame.getDrawnShapes()){
            shape.accept(jSonVisitor);
            jsonArray.add(jSonVisitor.getJsonObject());
        }
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder().add("shapes", jsonArray);
        jsonArray = Json.createArrayBuilder();
        for(GroupButton groupButton : jDrawingFrame.getGroupButtons()){
            jsonArray.add(groupButton.getJsonObject());
        }
        jsonObjectBuilder.add("groups", jsonArray);
        JsonObject jsonObject = jsonObjectBuilder.build();



        this.writeInFile("exports/export.json", jsonObject);
    }


    private void writeInFile(String filepath, JsonObject jsonObject){
        try  (FileWriter fileWriter = new FileWriter(filepath)) {
            fileWriter.write(jsonObject.toString());
            logger.log(Level.INFO, "JSON object has been written to {0}", filepath);
        } catch (IOException e) {
            logger.log(Level.SEVERE, GENERIC_ERROR_MESSAGE, e);
        }
    }
}