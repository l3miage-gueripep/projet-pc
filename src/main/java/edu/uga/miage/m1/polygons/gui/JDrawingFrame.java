package edu.uga.miage.m1.polygons.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

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

import edu.uga.miage.m1.polygons.gui.listeners.CursorButtonListener;
import edu.uga.miage.m1.polygons.gui.listeners.GroupButtonListener;
import edu.uga.miage.m1.polygons.gui.listeners.panelListeners.PanelGroupMouseListener;
import edu.uga.miage.m1.polygons.gui.listeners.panelListeners.PanelMoveMouseListener;
import edu.uga.miage.m1.polygons.gui.listeners.panelListeners.PanelDrawMouseListener;
import edu.uga.miage.m1.polygons.gui.listeners.ShapeButtonListener;
import edu.uga.miage.m1.polygons.gui.listeners.UndoAction;
import edu.uga.miage.m1.polygons.gui.listeners.exports.JsonActionListener;
import edu.uga.miage.m1.polygons.gui.listeners.exports.XMLActionListener;
import edu.uga.miage.m1.polygons.gui.shapes.Shapes;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

public class JDrawingFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private final int FRAME_WIDTH = 700;
    private final int GROUPS_AMOUNT = 5;
    private JToolBar toolbar;
    private JToolBar groupsToolbar;
    private Shapes shapeSelected;
    private DrawingPanel panel;
    private JLabel label;
    private List<SimpleShape> drawnShapes = new ArrayList<>();
    private transient DrawTool drawTool;
    private EnumMap<Shapes, JButton> shapeButtons = new EnumMap<>(Shapes.class);
    private List<GroupButton> groupButtons = new ArrayList<>();

    private transient GroupButtonListener groupButtonListener = new GroupButtonListener(this);
    private transient JsonActionListener jsonActionListener = new JsonActionListener(this);
    private transient XMLActionListener xmlActionListener = new XMLActionListener(this);
    
    public JDrawingFrame(String frameName) {
        super(frameName);
        initializeLayout();
        addTopToolbarButtons();
        
        //shape groups
        initializeShapeGroups();


        addUndoAction();
        drawTool = new DrawTool();
        repaint();
    }

    public DrawTool getDrawTool() {
        return drawTool;
    }

    private void initializeShapeGroups(){
        createShapeGroupsButtons();
    }

    private void createShapeGroupsButtons() {
        for(int i = 0; i < GROUPS_AMOUNT; i++) {
            GroupButton button = new GroupButton("Groupe " + (i+1));
            button.addActionListener(groupButtonListener);
            groupsToolbar.add(button);
        }
    }

    private void initializeLayout() {
        toolbar = new JToolBar("Toolbar");
        groupsToolbar = new JToolBar("Groups", SwingConstants.VERTICAL);
        panel = initializePanel();
        label = new JLabel(" ", SwingConstants.LEFT);
        setLayout(new BorderLayout());
        add(groupsToolbar, BorderLayout.WEST);
        add(toolbar, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
        add(label, BorderLayout.SOUTH);
        toolbar.validate();
        setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_WIDTH));
    }

    private DrawingPanel initializePanel() {
        DrawingPanel panel = new DrawingPanel(this);
        panel.setBackground(Color.WHITE);
        panel.setLayout(null);
        panel.setMinimumSize(new Dimension(FRAME_WIDTH, FRAME_WIDTH));
        panel.addMouseListener(new PanelDrawMouseListener(this));
        panel.addMouseListener(new PanelMoveMouseListener(this));
        panel.addMouseListener(new PanelGroupMouseListener(this));
        return panel;
    }

    private void addTopToolbarButtons(){
        addShapesButtons();
        addCursorButton();
        addExportButtons();
    }

    private void addShapesButtons() {
        addShapeButton(Shapes.SQUARE, new ImageIcon(getClass().getResource("images/square.png")));
        addShapeButton(Shapes.TRIANGLE, new ImageIcon(getClass().getResource("images/triangle.png")));
        addShapeButton(Shapes.CIRCLE, new ImageIcon(getClass().getResource("images/circle.png")));
        addShapeButton(Shapes.CUBE, new ImageIcon(getClass().getResource("images/underc.png")));
    }

    private void addShapeButton(Shapes shape, ImageIcon icon) {
        JButton button = new JButton(icon);
        button.setBorderPainted(false);
        shapeButtons.put(shape, button);
        button.setActionCommand(shape.toString());
        button.addActionListener(new ShapeButtonListener(this));
        toolbar.add(button);
        toolbar.validate();
        repaint();
    }

    private void addExportButtons() {
        JButton jsonButton = new JButton("Convertir en JSON");
        JButton xmlButton = new JButton("Convertir en XML");
        jsonButton.addActionListener(jsonActionListener);
        toolbar.add(jsonButton);
        xmlButton.addActionListener(xmlActionListener);
        toolbar.add(xmlButton);
    }

    private void addUndoAction()  {
        Action undoAction = new UndoAction("Undo", this);
        KeyStroke ctrlZKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx());
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(ctrlZKeyStroke, "undo");
        panel.getActionMap().put("undo", undoAction);
    }


    private void addCursorButton(){
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("images/cursor.png"));
        Image scaledImage = originalIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(scaledImage);
        JButton cursorButton = new JButton(resizedIcon);
        cursorButton.addActionListener(new CursorButtonListener(this));
        toolbar.add(cursorButton);
        cursorButton.setPreferredSize(new Dimension(50, 50));
    }



    public List<SimpleShape> getDrawnShapes() {
        return drawnShapes;
    }
    public EnumMap<Shapes, JButton> getShapeButtons() {
        return shapeButtons;
    }
    public Shapes getShapeSelected() {
        return shapeSelected;
    }
    public DrawingPanel getPanel() {
        return panel;
    }

    public void setShapeSelected(Shapes selected) {
        this.shapeSelected = selected;
    }


}


