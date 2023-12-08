package edu.uga.miage.m1.polygons.gui.importexportjson;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ImportTool {
    public static Export getExport(String filepath){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(new File(filepath), Export.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
