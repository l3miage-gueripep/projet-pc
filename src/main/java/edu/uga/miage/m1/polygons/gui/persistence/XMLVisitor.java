package edu.uga.miage.m1.polygons.gui.persistence;

import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import edu.uga.miage.m1.polygons.gui.shapes.Circle;
import edu.uga.miage.m1.polygons.gui.shapes.Cube;
import edu.uga.miage.m1.polygons.gui.shapes.Square;
import edu.uga.miage.m1.polygons.gui.shapes.Triangle;

/**
 * @author <a href="mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 */
public class XMLVisitor implements Visitor {

    private Document document;
    private Element rootElement;
    private Element shapeElement;
    private static final Logger logger = Logger.getLogger(XMLVisitor.class.getName());
    private static final String GENERIC_ERROR_MESSAGE = "An error occurred";

    public Element getShapElement() {
        return shapeElement;
    }

    public XMLVisitor() {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            document = dBuilder.newDocument();
        } catch (ParserConfigurationException e) {
            logger.log(Level.SEVERE, GENERIC_ERROR_MESSAGE, e);
        } 
    }

    private Element createShapeElement(String type, int x, int y) {
        shapeElement = document.createElement("shape");
        Element typeElement = document.createElement("type");
        typeElement.appendChild(document.createTextNode(type));
        Element xElement = document.createElement("x");
        xElement.appendChild(document.createTextNode(Integer.toString(x)));
        Element yElement = document.createElement("y");
        yElement.appendChild(document.createTextNode(Integer.toString(y)));

        shapeElement.appendChild(typeElement);
        shapeElement.appendChild(xElement);
        shapeElement.appendChild(yElement);

        return shapeElement;
    }

    @Override
    public void visit(Circle circle) {
        rootElement = createShapeElement("circle", circle.getX(), circle.getY());
    }

    @Override
    public void visit(Square square) {
        rootElement = createShapeElement("square", square.getX(), square.getY());
    }

    @Override
    public void visit(Triangle triangle) {
        rootElement = createShapeElement("triangle", triangle.getX(), triangle.getY());
    }

    public String getRepresentation() {
        return elementToString(rootElement);
    }

    public String elementToString(Element element) {
        try {
            // Assuming you have an 'element' variable
            document.appendChild(element);

            Transformer transformer = TransformerFactory.newDefaultInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            StreamResult result = new StreamResult(new StringWriter());
            transformer.transform(new DOMSource(document), result);

            String xmlString = result.getWriter().toString();
  
            // Remove line breaks and extra whitespace
            xmlString = xmlString.replaceAll("\\n\\s*", "");

            return xmlString;
        } catch (Exception e) {
            logger.log(Level.SEVERE, GENERIC_ERROR_MESSAGE, e);
            return null;
        }
    }

    @Override
    public void visit(Cube cube) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }
}
