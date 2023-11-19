package edu.uga.miage.m1.polygons.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import javax.swing.Action;
import javax.swing.BoxLayout;
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
import edu.uga.miage.m1.polygons.gui.listeners.ShapeButtonListener;
import edu.uga.miage.m1.polygons.gui.listeners.UndoAction;
import edu.uga.miage.m1.polygons.gui.listeners.exports.JsonActionListener;
import edu.uga.miage.m1.polygons.gui.listeners.exports.XMLActionListener;
import edu.uga.miage.m1.polygons.gui.listeners.panellisteners.PanelDrawMouseListener;
import edu.uga.miage.m1.polygons.gui.listeners.panellisteners.PanelGroupMouseListener;
import edu.uga.miage.m1.polygons.gui.listeners.panellisteners.PanelMoveMouseListener;
import edu.uga.miage.m1.polygons.gui.shapes.Shapes;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

public class JDrawingFrame extends JFrame {
    private static JDrawingFrame instance;
    private static final long serialVersionUID = 1L;
    private static final int FRAME_WIDTH = 700;
    private static final int GROUPS_AMOUNT = 5;
    private static final int BUTTONS_SIZE = 50;
    private JToolBar toolbar;
    private JToolBar groupsToolbar;
    private Shapes shapeSelected;
    private DrawingPanel panel;
    private transient List<SimpleShape> drawnShapes = new ArrayList<>();
    private transient DrawTool drawTool;
    private EnumMap<Shapes, JButton> shapeButtons = new EnumMap<>(Shapes.class);
    private List<GroupButton> groupButtons = new ArrayList<>();
    private GroupButton currentlySelectedGroupButton;
    private JButton cursorButton;


    private transient GroupButtonListener groupButtonListener = new GroupButtonListener(this);
    private transient JsonActionListener jsonActionListener = new JsonActionListener(this);
    private transient XMLActionListener xmlActionListener = new XMLActionListener(this);
    
    private JDrawingFrame(String frameName) {
        super(frameName);
        initializeLayout();
        addTopToolbarButtons();
        initializeShapeGroups();
        addUndoAction();
        drawTool = new DrawTool(this);
        repaint();
    }

    public static JDrawingFrame getInstance() {
        if (instance == null) {
            instance = new JDrawingFrame("PhotoMiage");
        }
        return instance;
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
            groupButtons.add(button);
            groupsToolbar.add(button);
        }
    }

    private void initializeLayout() {
        toolbar = new JToolBar("Toolbar");
        groupsToolbar = new JToolBar("Groups", SwingConstants.VERTICAL);
        panel = initializePanel();
        JLabel label = new JLabel(" ", SwingConstants.LEFT);
        setLayout(new BorderLayout());
        add(groupsToolbar, BorderLayout.WEST);
        add(toolbar, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
        add(label, BorderLayout.SOUTH);
        toolbar.validate();
        setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_WIDTH));
    }

    private DrawingPanel initializePanel() {
        DrawingPanel drawingPanel = new DrawingPanel(this);
        drawingPanel.setBackground(Color.WHITE);
        drawingPanel.setLayout(null);
        drawingPanel.setMinimumSize(new Dimension(FRAME_WIDTH, FRAME_WIDTH));
        drawingPanel.addMouseListener(new PanelDrawMouseListener(this));
        drawingPanel.addMouseListener(new PanelMoveMouseListener(this));
        drawingPanel.addMouseListener(new PanelGroupMouseListener(this));
        return drawingPanel;
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
        JButton button = new JButton(getScaledImageIcon(icon));
        button.setBorderPainted(false);
        shapeButtons.put(shape, button);
        button.setActionCommand(shape.toString());
        button.addActionListener(new ShapeButtonListener(this));
        toolbar.add(button);
        toolbar.validate();
        repaint();
    }

    private void addCursorButton(){
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("images/cursor.png"));
        cursorButton = new JButton(getScaledImageIcon(originalIcon));
        cursorButton.setBorderPainted(false);
        cursorButton.addActionListener(new CursorButtonListener(this));
        toolbar.add(cursorButton);
    }

    private ImageIcon getScaledImageIcon(ImageIcon icon){
        Image scaledImage = icon.getImage().getScaledInstance(BUTTONS_SIZE, BUTTONS_SIZE, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    private void addExportButtons() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
        JButton jsonButton = new JButton("Convertir en JSON");
        JButton xmlButton = new JButton("Convertir en XML  ");
        Dimension maxDimension = new Dimension(200, jsonButton.getPreferredSize().height);
        jsonButton.setMaximumSize(maxDimension);
        xmlButton.setMaximumSize(maxDimension);
        jsonButton.addActionListener(jsonActionListener);
        xmlButton.addActionListener(xmlActionListener);
        buttonPanel.add(jsonButton);
        buttonPanel.add(xmlButton);
        toolbar.add(buttonPanel, BorderLayout.EAST);
    }

    private void addUndoAction()  {
        Action undoAction = new UndoAction("Undo", this);
        KeyStroke ctrlZKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx());
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(ctrlZKeyStroke, "undo");
        panel.getActionMap().put("undo", undoAction);
    }

    public GroupButton getCurrentlySelectedGroupButton(){
        return currentlySelectedGroupButton;
    }

    public void setCurrentlySelectedGroupButton(GroupButton currentlySelectedGroupButton) {
        if(this.currentlySelectedGroupButton != null)
            this.currentlySelectedGroupButton.setBackground(Color.WHITE);
        if(currentlySelectedGroupButton != null)
            currentlySelectedGroupButton.setBackground(Color.CYAN);
        this.currentlySelectedGroupButton = currentlySelectedGroupButton;
    }

    public void resetGroupButtons(){
        for(var groupButton : groupButtons){
            groupButton.getShapes().clear();
        }
    }

    public void deselectAllButtons(){
        deselectShapeButton();
        deselectCursorButton();
    }

    private void deselectShapeButton(){
        for(JButton button : shapeButtons.values()){
            button.setBorderPainted(false);
        }
    }

    private void deselectCursorButton(){
        cursorButton.setBorderPainted(false);
    }


    public List<SimpleShape> getDrawnShapes() {
        return drawnShapes;
    }
    public Map<Shapes, JButton> getShapeButtons() {
        return shapeButtons;
    }
    public Shapes getShapeSelected() {
        return shapeSelected;
    }
    public DrawingPanel getPanel() {
        return panel;
    }
    public List<GroupButton> getGroupButtons() {
        return groupButtons;
    }
    public void setShapeSelected(Shapes selected) {
        this.shapeSelected = selected;
    }
    public JButton getCursorButton() {
        return cursorButton;
    }
    public Graphics2D getPanelG2(){
        return (Graphics2D) panel.getGraphics();
    }


}


