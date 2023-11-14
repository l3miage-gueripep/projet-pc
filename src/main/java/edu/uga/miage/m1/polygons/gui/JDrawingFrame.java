package edu.uga.miage.m1.polygons.gui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
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

import edu.uga.miage.m1.polygons.gui.commands.DrawShapeCommand;
import edu.uga.miage.m1.polygons.gui.persistence.JSonVisitor;
import edu.uga.miage.m1.polygons.gui.persistence.Visitable;
import edu.uga.miage.m1.polygons.gui.persistence.XMLVisitor;
import edu.uga.miage.m1.polygons.gui.shapes.Circle;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;
import edu.uga.miage.m1.polygons.gui.shapes.Square;
import edu.uga.miage.m1.polygons.gui.shapes.Triangle;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;


                 
/**
 * This class represents the main application class, which is a JFrame subclass
 * that manages a toolbar of shapes and a drawing canvas.
 *
 * @author <a href="mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 */
public class JDrawingFrame extends JFrame implements MouseListener, MouseMotionListener {

    private enum Shapes {

        SQUARE, TRIANGLE, CIRCLE
    }

    private static final long serialVersionUID = 1L;
//test
    private JToolBar toolbar;

    private Shapes selected;

    private JPanel panel;

    private JLabel label;

    private transient ActionListener reusableActionListener = new ShapeActionListener();

    protected static List<SimpleShape> drawnShapes = new ArrayList<>();

    private static final Logger logger = Logger.getLogger(JDrawingFrame.class.getName());

    private transient DrawTool drawTool;

    private static final String GENERIC_ERROR_MESSAGE = "An error occurred";

    private SimpleShape movingShape;



    /**
     * Tracks buttons to manage the background.
     */
    private EnumMap<Shapes, JButton> buttons = new EnumMap<>(Shapes.class);


    private transient JsonActionListener jsonActionListener = new JsonActionListener();
    private transient XMLActionListener xmlActionListener = new XMLActionListener();
    private transient CursorActionListener cursorActionListener = new CursorActionListener();
    

    /**
     * Default constructor that populates the main window.
     * @param frameName
     */
    public JDrawingFrame(String frameName) {
        super(frameName);
        // Instantiates components
        toolbar = new JToolBar("Toolbar");
        panel = new DrawingPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(null);
        panel.setMinimumSize(new Dimension(700, 700));
        
        panel.addMouseListener(this);
        panel.addMouseMotionListener(this);
        label = new JLabel(" ", SwingConstants.LEFT);
        // Fills the panel
        setLayout(new BorderLayout());
        add(toolbar, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
        add(label, BorderLayout.SOUTH);
        // Add shapes in the menu
        addShape(Shapes.SQUARE, new ImageIcon(getClass().getResource("images/square.png")));
        addShape(Shapes.TRIANGLE, new ImageIcon(getClass().getResource("images/triangle.png")));
        addShape(Shapes.CIRCLE, new ImageIcon(getClass().getResource("images/circle.png")));

        
        addCursorButton();
        
        setPreferredSize(new Dimension(700, 700));


        //NOTRE CODE
        JButton jsonButton = new JButton("Convertir en JSON");
        JButton xmlButton = new JButton("Convertir en XML");

        //buttons json et xml
        jsonButton.addActionListener(jsonActionListener);
        toolbar.add(jsonButton);
        xmlButton.addActionListener(xmlActionListener);
        toolbar.add(xmlButton);

        toolbar.validate();
        repaint();


        this.drawTool = new DrawTool();

        // remove a shape
        Action undoAction = new AbstractAction("Undo") {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawTool.removeLastCommand();
                if(!drawnShapes.isEmpty()){
                    drawnShapes.remove(drawnShapes.size()-1);
                }
                repaint();
                drawTool.play();
                
            }
        };

        //Ctrl+z event
        KeyStroke ctrlZKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx());
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(ctrlZKeyStroke, "undo");
        panel.getActionMap().put("undo", undoAction);

        
    }

    private void addCursorButton(){
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("images/cursor.png"));
        Image scaledImage = originalIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(scaledImage);
        JButton cursorButton = new JButton(resizedIcon);
        cursorButton.addActionListener(cursorActionListener);
        toolbar.add(cursorButton);
        cursorButton.setPreferredSize(new Dimension(50, 50));
    }

    public class DrawingPanel extends JPanel{
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            
            // Set rendering settings for all shapes
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            for (SimpleShape shape : drawnShapes) {
                // Apply rendering settings
                GradientPaint gradient = new GradientPaint(shape.getX(), shape.getY(), Color.RED, (float) shape.getX() + 50, shape.getY(), Color.WHITE);
                g2.setPaint(gradient);
                
                BasicStroke wideStroke = new BasicStroke(2.0f);
                g2.setColor(Color.black);
                g2.setStroke(wideStroke);
                
                // Draw the shape
                shape.draw(g2);
            }
        }
    }

    /**
     * Injects an available <tt>SimpleShape</tt> into the drawing frame.
     * @param name The name of the injected <tt>SimpleShape</tt>.
     * @param icon The icon associated with the injected <tt>SimpleShape</tt>.
     */
    private void addShape(Shapes shape, ImageIcon icon) {
        JButton button = new JButton(icon);
        button.setBorderPainted(false);
        buttons.put(shape, button);
        button.setActionCommand(shape.toString());
        button.addActionListener(reusableActionListener);
        if (selected == null) {
            button.doClick();
        }
        toolbar.add(button);
        toolbar.validate();
        repaint();
    }

    /**
     * Implements method for the <tt>MouseListener</tt> interface to
     * draw the selected shape into the drawing canvas.
     * @param evt The associated mouse event.
     */
    public void mouseClicked(MouseEvent evt) {
        if (panel.contains(evt.getX(), evt.getY()) && selected != null) {
            Graphics2D g2 = (Graphics2D) panel.getGraphics();
            SimpleShape shape = null;

            switch(selected) {
                case CIRCLE:
                    shape = new Circle(evt.getX(), evt.getY());
                    break;
                case TRIANGLE:
                    shape = new Triangle(evt.getX(), evt.getY());
                    break;
                case SQUARE:
                    shape = new Square(evt.getX(), evt.getY());
                    break;
                default:
                    logger.log(Level.FINE, "No shape named {0}", selected);
            }
            DrawShapeCommand cDrawShape = new DrawShapeCommand(shape, g2);
            this.drawTool.addCommand(cDrawShape);
            JDrawingFrame.drawnShapes.add(shape);
            this.drawTool.play();
        }
    }

    /**
     * Implements an empty method for the <tt>MouseListener</tt> interface.
     * @param evt The associated mouse event.
     */
    public void mouseEntered(MouseEvent evt) {
        //Does nothing
    }

    /**
     * Implements an empty method for the <tt>MouseListener</tt> interface.
     * @param evt The associated mouse event.
     */
    public void mouseExited(MouseEvent evt) {
        label.setText(" ");
        label.repaint();
    }

    /**
     * Implements method for the <tt>MouseListener</tt> interface to initiate
     * shape dragging.
     * @param evt The associated mouse event.
     */
    public void mousePressed(MouseEvent evt) {
        //Does nothing
    }

    /**
     * Implements method for the <tt>MouseListener</tt> interface to complete
     * shape dragging.
     * @param evt The associated mouse event.
     */
    public void mouseReleased(MouseEvent evt) {
        //Does nothing
    }

    /**
     * Implements method for the <tt>MouseMotionListener</tt> interface to
     * move a dragged shape.
     * @param evt The associated mouse event.
     */
    public void mouseDragged(MouseEvent evt) {
        //Does nothing
    }

    /**
     * Implements an empty method for the <tt>MouseMotionListener</tt>
     * interface.
     * @param evt The associated mouse event.
     */
    public void mouseMoved(MouseEvent evt) {
        modifyLabel(evt);
    }

    private void modifyLabel(MouseEvent evt) {
        label.setText("(" + evt.getX() + "," + evt.getY() + ")");
    }

    /**
     * Simple action listener for shape tool bar buttons that sets
     * the drawing frame's currently selected shape when receiving
     * an action event.
     */
    private class ShapeActionListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            //
            panel.addActionL(cursorActionListener);
            // ItÃÂÃÂÃÂÃÂ¨re sur tous les boutons
            Iterator<Shapes> keys = buttons.keySet().iterator();
            while (keys.hasNext()) {
                Shapes shape = keys.next();
                JButton btn = buttons.get(shape);
                if (evt.getActionCommand().equals(shape.toString())) {
                    btn.setBorderPainted(true);
                    selected = shape;
                } else {
                    btn.setBorderPainted(false);
                }
                btn.repaint();
            }
        }
    }

    private class JsonActionListener implements ActionListener {
        private JSonVisitor jSonVisitor = new JSonVisitor();
        JsonArrayBuilder jsonArray = Json.createArrayBuilder();
        public void actionPerformed(ActionEvent evt) {
            for(Visitable shape : JDrawingFrame.drawnShapes){
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

    private class CursorActionListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            panel.setCursor(new Cursor(Cursor.MOVE_CURSOR));
            selected = null;
            System.out.println(evt.toString());
            panel.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {}
            
                @Override
                public void mousePressed(MouseEvent e) {
                    movingShape = null;
                    for(SimpleShape shape : JDrawingFrame.drawnShapes){
                        if(shape.isInside(e.getX(), e.getY())){
                            System.out.println("shape selected");
                            movingShape = shape;

                        }
                    }

                }
            
                @Override
                public void mouseReleased(MouseEvent e) {
                    System.out.println("mouseReleased");
                    drawTool.commands.stream().filter(command -> command.getShape() == movingShape).forEach(command -> {
                        command.getShape().changeCoordinates(e.getX(), e.getY());
                    });
                    repaint();
                    drawTool.play();
                }
            
                @Override
                public void mouseEntered(MouseEvent e) {}
            
                @Override
                public void mouseExited(MouseEvent e) {}
            });
        }

        private class MoveActionListener implements ActionListener {
            public void actionPerformed(ActionEvent evt) {
                ((Object) panel).removeActionL(cursorActionListener);
                panel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                panel.removeMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {}
                
                    @Override
                    public void mousePressed(MouseEvent e) {}
                
                    @Override
                    public void mouseReleased(MouseEvent e) {}
                
                    @Override
                    public void mouseEntered(MouseEvent e) {}
                
                    @Override
                    public void mouseExited(MouseEvent e) {}
                });
            }
        }
    }

    

    private class XMLActionListener implements ActionListener {
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
                for (Visitable shape : JDrawingFrame.drawnShapes) {
                    // ItÃ¨re sur tous les boutons
                    shape.accept(xmlVisitor);
                    //on importe la node dans le document
                    Node copiedNode = document.importNode(xmlVisitor.getShapElement(), true);
                    shapesElement.appendChild(copiedNode);
                }
                this.writeInFile("export.xml", document);
            } catch (ParserConfigurationException e) {
                logger.log(Level.SEVERE, GENERIC_ERROR_MESSAGE, e);
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
                logger.log(Level.SEVERE, GENERIC_ERROR_MESSAGE, e);            
            }
        }
        private void writeInFile(String filepath, StringWriter stringWriter){
            try (FileWriter fileWriter = new FileWriter(filepath)) {
                
                // Write the JSON object to the file
                fileWriter.write(stringWriter.toString());
            
                logger.log(Level.INFO, "XML object has been written to {0}", filepath);
            } catch (IOException e) {
                logger.log(Level.SEVERE, GENERIC_ERROR_MESSAGE, e);
            }
        }
    }
}


