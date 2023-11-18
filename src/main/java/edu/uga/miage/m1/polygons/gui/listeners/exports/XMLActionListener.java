package edu.uga.miage.m1.polygons.gui.listeners.exports;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import edu.uga.miage.m1.polygons.gui.GroupButton;
import edu.uga.miage.m1.polygons.gui.JDrawingFrame;
import edu.uga.miage.m1.polygons.gui.persistence.Visitable;
import edu.uga.miage.m1.polygons.gui.persistence.XMLVisitor;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

public class XMLActionListener implements ActionListener {

    private static final Logger LOGGER = Logger.getLogger(XMLActionListener.class.getName());
    private static final String GENERIC_ERROR_MESSAGE = "An error occured while exporting the XML file";
    private JDrawingFrame jDrawingFrame;

    public XMLActionListener(JDrawingFrame jDrawingFrame){
        super();
        this.jDrawingFrame = jDrawingFrame;
    }


    private XMLVisitor xmlVisitor = new XMLVisitor();
        
        public void actionPerformed(ActionEvent evt) {
            Document document = null;
            try {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                document = dBuilder.newDocument();
                
                Element rooElement = document.createElement("root");
                document.appendChild(rooElement);
                Element shapesElement = document.createElement("shapes");
                rooElement.appendChild(shapesElement);
                Element groupsElement = document.createElement("groups");
                rooElement.appendChild(groupsElement);
                

                List<SimpleShape> drawnShapes = jDrawingFrame.getDrawnShapes();
                for (SimpleShape shape : drawnShapes) {
                    shape.accept(xmlVisitor);
                    Node copiedNode = document.importNode(xmlVisitor.getShapeElement(), true);
                    ((Element)copiedNode).setAttribute("id", Integer.toString(shape.getId()));
                    shapesElement.appendChild(copiedNode);
                }

                int groupId = 0;
                for (GroupButton groupButton : jDrawingFrame.getGroupButtons()) {
                    Element groupElement = document.createElement("group");
                    groupElement.setAttribute("id", Integer.toString(groupId++));
                    groupsElement.appendChild(groupElement);
                    for (SimpleShape shape : groupButton.getShapes()) {
                        Element shapeElement = document.createElement("shape");
                        shapeElement.setAttribute("id", Integer.toString(shape.getId()));
                        groupElement.appendChild(shapeElement);
                    }
                }

                this.writeInFile("exports/export.xml", document);
            } catch (ParserConfigurationException e) {
                LOGGER.log(Level.SEVERE, GENERIC_ERROR_MESSAGE, e);
            }
        }
        

        private void writeInFile(String filepath, Document xmlDocument){
            try {
                TransformerFactory transformerFactory = TransformerFactory.newDefaultInstance();
                Transformer transformer = transformerFactory.newTransformer();
                StringWriter stringWriter = new StringWriter();
                StreamResult streamResult = new StreamResult(stringWriter);
                DOMSource source = new DOMSource(xmlDocument);
                transformer.transform(source, streamResult);
        
                this.writeInFile(filepath, stringWriter);

                
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, GENERIC_ERROR_MESSAGE, e);            
            }
        }
        private void writeInFile(String filepath, StringWriter stringWriter){
            try (FileWriter fileWriter = new FileWriter(filepath)) {
                fileWriter.write(stringWriter.toString());
                LOGGER.log(Level.INFO, "XML object has been written to {0}", filepath);
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, GENERIC_ERROR_MESSAGE, e);
            }
        }
    }