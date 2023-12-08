package edu.uga.miage.m1.polygons.gui.importexportjson;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import edu.uga.miage.m1.polygons.gui.persistence.JSonVisitor;
import edu.uga.miage.m1.polygons.gui.persistence.Visitable;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

public class ExportTool {
    private static final Logger logger = Logger.getLogger(ExportTool.class.getName());
    private static final String GENERIC_ERROR_MESSAGE = "An error occured while exporting the JSON file";


    private JSonVisitor jSonVisitor = new JSonVisitor();

    public JsonArrayBuilder createJsonShapesArray(List<SimpleShape> shapes){
        JsonArrayBuilder jsonArray = Json.createArrayBuilder();

        for(Visitable shape : shapes){
            shape.accept(jSonVisitor);
            jsonArray.add(jSonVisitor.getJsonObject());
        }
        return jsonArray;
    }

    public JsonArrayBuilder createJsonGroupsArray(List<GroupData> groupes){
        JsonArrayBuilder jsonArray = Json.createArrayBuilder();
        for(GroupData group : groupes){
            jsonArray.add(createJsonObject(group));
        }
        return jsonArray;
    }

    public JsonObject createJsonObject(GroupData groupData){
        var jsonObjectBuilder = Json.createObjectBuilder();
        jsonObjectBuilder.add("id", groupData.getId());
        var jsonArrayBuilder = Json.createArrayBuilder();
        for(Integer shape : groupData.getShapes()){
            jsonArrayBuilder.add(shape);
        }
        jsonObjectBuilder.add("shapes", jsonArrayBuilder);
        return jsonObjectBuilder.build();
    }

    public void writeInFile(String filepath, JsonObject jsonObject){
        try  (FileWriter fileWriter = new FileWriter(filepath)) {
            fileWriter.write(jsonObject.toString());
            logger.log(Level.INFO, "JSON object has been written to {0}", filepath);
        } catch (IOException e) {
            logger.log(Level.SEVERE, GENERIC_ERROR_MESSAGE, e);
        }
    }
}
