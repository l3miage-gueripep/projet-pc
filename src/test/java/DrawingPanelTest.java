import edu.uga.miage.m1.polygons.gui.JDrawingFrame;
import edu.uga.miage.m1.polygons.gui.DrawingPanel;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List; // Add this import statement

class DrawingPanelTest {

    private DrawingPanel drawingPanel;
    private JDrawingFrame jDrawingFrame;
    private SimpleShape simpleShape;

    @BeforeEach
    public void setUp() {
        jDrawingFrame = Mockito.mock(JDrawingFrame.class);
        simpleShape = Mockito.mock(SimpleShape.class);
        drawingPanel = new DrawingPanel(jDrawingFrame);
    }

    @Test
    void testSetAndGetMode() {
        drawingPanel.setMode(DrawingPanel.Mode.MOVE);
        assertEquals(DrawingPanel.Mode.MOVE, drawingPanel.getMode());
    }


    @Test
    void testGetShapeAtLocation() {
        Mockito.when(jDrawingFrame.getDrawnShapes()).thenReturn(List.of(simpleShape));
        Mockito.when(simpleShape.isInside(Mockito.anyInt(), Mockito.anyInt())).thenReturn(true);
        SimpleShape shape = drawingPanel.getShapeAtLocation(10, 10);
        assertEquals(simpleShape, shape);
    }

    @Test
    void testAddShapeToSelection() {
        drawingPanel.addShapeToSelection(simpleShape);
        assertTrue(drawingPanel.getCurrentlySelectedShapes().contains(simpleShape));
    }

}