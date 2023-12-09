package edu.uga.miage.m1.polygons.gui.listeners.exports;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;


import edu.uga.miage.m1.polygons.gui.JDrawingFrame;
import fr.uga.miage.m1.ExportTool;

public class JsonExportActionListener implements ActionListener {
    JDrawingFrame jDrawingFrame;
    ExportTool exportTool = new ExportTool();


    public JsonExportActionListener(JDrawingFrame jDrawingFrame){
        super();
        this.jDrawingFrame = jDrawingFrame;
    }

    
    public void actionPerformed(ActionEvent evt) {
        JsonArrayBuilder jsonShapesArray = exportTool.createJsonShapesArray(jDrawingFrame.getDrawnShapesData());
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder().add("shapes", jsonShapesArray);
        JsonArrayBuilder jsonGroupsArray = exportTool.createJsonGroupsArray(jDrawingFrame.getGroupsDatas());
        jsonObjectBuilder.add("groups", jsonGroupsArray);
        JsonObject jsonObject = jsonObjectBuilder.build();
        exportTool.writeInFile("exports/export.json", jsonObject);
    }



}
