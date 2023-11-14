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
import edu.uga.miage.m1.polygons.gui.commands.DrawShapeCommand;
import edu.uga.miage.m1.polygons.gui.events.ShapeActionListener;
import edu.uga.miage.m1.polygons.gui.events.exports.JsonActionListener;
import edu.uga.miage.m1.polygons.gui.events.exports.XMLActionListener;
import edu.uga.miage.m1.polygons.gui.shapes.Circle;
import edu.uga.miage.m1.polygons.gui.shapes.Shapes;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;
import edu.uga.miage.m1.polygons.gui.shapes.Square;
import edu.uga.miage.m1.polygons.gui.shapes.Triangle;
import java.util.logging.Level;
import java.util.logging.Logger;


                 
/**
 * This class represents the main application class, which is a JFrame subclass
 * that manages a toolbar of shapes and a drawing canvas.
 *
 * @author <a href="mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 */
public class JDrawingFrame extends JFrame implements MouseListener, MouseMotionListener {



    private static final long serialVersionUID = 1L;

    public void setSelected(Shapes selected) {
        this.selected = selected;
    }

    private JToolBar toolbar;

    private Shapes selected;

    private JPanel panel;

    private JLabel label;


    private List<SimpleShape> drawnShapes = new ArrayList<>();

    private static final Logger logger = Logger.getLogger(JDrawingFrame.class.getName());

    private transient DrawTool drawTool;
    private SimpleShape movingShape;


    /**
     * Tracks buttons to manage the background.
     */
    private EnumMap<Shapes, JButton> buttons = new EnumMap<>(Shapes.class);

    public EnumMap<Shapes, JButton> getButtons() {
        return buttons;
    }

    private transient ActionListener reusableActionListener = new ShapeActionListener(this);
    private transient JsonActionListener jsonActionListener = new JsonActionListener(this);
    private transient XMLActionListener xmlActionListener = new XMLActionListener(this);
    private transient CursorActionListener cursorActionListener = new CursorActionListener(this);
    

    /**
     * Default constructor that populates the main window.
     * @param frameName
     */
    public JDrawingFrame(String frameName) {
        super(frameName);
        // Instantiates components
        toolbar = new JToolBar("Toolbar");
        initializePanel();
        label = new JLabel(" ", SwingConstants.LEFT);
        // Fills the panel
        setLayout(new BorderLayout());
        add(toolbar, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
        add(label, BorderLayout.SOUTH);


        // Add shapes in the menu
        addShapes();
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

    private void initializePanel() {
        panel = new DrawingPanel(this);
        panel.setBackground(Color.WHITE);
        panel.setLayout(null);
        panel.setMinimumSize(new Dimension(700, 700));
        
        panel.addMouseListener(this);
        panel.addMouseMotionListener(this);
    }

    private void addShapes() {
        addShape(Shapes.SQUARE, new ImageIcon(getClass().getResource("images/square.png")));
        addShape(Shapes.TRIANGLE, new ImageIcon(getClass().getResource("images/triangle.png")));
        addShape(Shapes.CIRCLE, new ImageIcon(getClass().getResource("images/circle.png")));
    }

    

    public List<SimpleShape> getDrawnShapes() {
        return drawnShapes;
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
            drawnShapes.add(shape);
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

    private class CursorActionListener implements ActionListener {

        JDrawingFrame jDrawingFrame;

        public CursorActionListener(JDrawingFrame jDrawingFrame){
            super();
            this.jDrawingFrame = jDrawingFrame;
        }

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
                    for(SimpleShape shape : jDrawingFrame.getDrawnShapes()){
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
    }
}


