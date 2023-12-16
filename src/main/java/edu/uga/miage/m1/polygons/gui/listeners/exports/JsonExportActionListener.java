package edu.uga.miage.m1.polygons.gui.listeners.exports;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import edu.uga.miage.m1.polygons.gui.JDrawingFrame;
import fr.uga.miage.m1.ExportTool;
import fr.uga.miage.m1.model.Export;

public class JsonExportActionListener implements ActionListener {
    JDrawingFrame jDrawingFrame;
    ExportTool exportTool = new ExportTool();

    public JsonExportActionListener(JDrawingFrame jDrawingFrame){
        super();
        this.jDrawingFrame = jDrawingFrame;
    }
    
    public void actionPerformed(ActionEvent evt) {
        Export export = new Export(jDrawingFrame.getDrawnShapesData(), jDrawingFrame.getGroupsDatas());
        try{
            exportTool.export(export, "exports/export.json");
        }
        catch(Exception e){
            jDrawingFrame.showError("Erreur lors de l'exportation du fichier");
        }
    }



}
