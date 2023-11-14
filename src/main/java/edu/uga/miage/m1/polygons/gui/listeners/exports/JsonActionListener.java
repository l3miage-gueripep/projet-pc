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
import edu.uga.miage.m1.polygons.gui.JDrawingFrame;
import edu.uga.miage.m1.polygons.gui.persistence.JSonVisitor;
import edu.uga.miage.m1.polygons.gui.persistence.Visitable;

public class JsonActionListener implements ActionListener {

    private static final Logger logger = Logger.getLogger(JsonActionListener.class.getName());
    private static final String GENERIC_ERROR_MESSAGE = "An error occured while exporting the JSON file";

    JDrawingFrame jDrawingFrame;

    public JsonActionListener(JDrawingFrame jDrawingFrame){
        super();
        this.jDrawingFrame = jDrawingFrame;
    }

    private JSonVisitor jSonVisitor = new JSonVisitor();
    JsonArrayBuilder jsonArray = Json.createArrayBuilder();
    public void actionPerformed(ActionEvent evt) {
        for(Visitable shape : jDrawingFrame.getDrawnShapes()){
            // ItÃÂ¨re sur tous les boutons
            shape.accept(jSonVisitor);
            jsonArray.add(jSonVisitor.getJsonObject());
        }
        JsonObject jsonObject = Json.createObjectBuilder()
            .add("shapes", jsonArray)
            .build();
        this.writeInFile("export.json", jsonObject);
    }


    private void writeInFile(String filepath, JsonObject jsonObject){
        try  (FileWriter fileWriter = new FileWriter(filepath)) {
        
            // Write the JSON object to the file
            fileWriter.write(jsonObject.toString());
        
            logger.log(Level.INFO, "JSON object has been written to {0}", filepath);
        } catch (IOException e) {
            logger.log(Level.SEVERE, GENERIC_ERROR_MESSAGE, e);
        }
    }
}
