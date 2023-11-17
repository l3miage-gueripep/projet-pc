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
import edu.uga.miage.m1.polygons.gui.listeners.PanelMouseListener;
import edu.uga.miage.m1.polygons.gui.listeners.ShapeButtonListener;
import edu.uga.miage.m1.polygons.gui.listeners.UndoAction;
import edu.uga.miage.m1.polygons.gui.listeners.exports.JsonActionListener;
import edu.uga.miage.m1.polygons.gui.listeners.exports.XMLActionListener;
import edu.uga.miage.m1.polygons.gui.shapes.Shapes;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

public class JDrawingFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private final int FRAME_WIDTH = 700;
    private JToolBar toolbar;
    private JToolBar groupsToolbar;
    private Shapes shapeSelected;
    private JPanel panel;
    private JLabel label;
    private List<SimpleShape> drawnShapes = new ArrayList<>();
    private transient DrawTool drawTool;
    private EnumMap<Shapes, JButton> buttons = new EnumMap<>(Shapes.class);

    private transient ShapeButtonListener shapeButtonListener = new ShapeButtonListener(this);
    private transient JsonActionListener jsonActionListener = new JsonActionListener(this);
    private transient XMLActionListener xmlActionListener = new XMLActionListener(this);
    private transient CursorButtonListener cursorActionListener = new CursorButtonListener(this);
    private transient PanelMouseListener panelMouseListener = new PanelMouseListener(this);
    
    public JDrawingFrame(String frameName) {
        super(frameName);
        initializeLayout();
        addTopToolbarButtons();
        addUndoAction();
        drawTool = new DrawTool();
        repaint();
    }

    public DrawTool getDrawTool() {
        return drawTool;
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


    private JPanel initializePanel() {
        JPanel panel = new DrawingPanel(this);
        panel.setBackground(Color.WHITE);
        panel.setLayout(null);
        panel.setMinimumSize(new Dimension(FRAME_WIDTH, FRAME_WIDTH));
        panelMouseListener = new PanelMouseListener(this);
        panel.addMouseListener(panelMouseListener);
        cursorActionListener.setPanelMouseListener(panelMouseListener);
        shapeButtonListener.setPanelMouseListener(panelMouseListener);
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
        buttons.put(shape, button);
        button.setActionCommand(shape.toString());
        button.addActionListener(shapeButtonListener);
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
        cursorButton.addActionListener(cursorActionListener);
        toolbar.add(cursorButton);
        cursorButton.setPreferredSize(new Dimension(50, 50));
    }



    public List<SimpleShape> getDrawnShapes() {
        return drawnShapes;
    }
    public EnumMap<Shapes, JButton> getButtons() {
        return buttons;
    }
    public Shapes getShapeSelected() {
        return shapeSelected;
    }
    public JPanel getPanel() {
        return panel;
    }

    public void setShapeSelected(Shapes selected) {
        this.shapeSelected = selected;
    }
}


