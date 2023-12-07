import edu.uga.miage.m1.polygons.gui.JDrawingFrame;
import edu.uga.miage.m1.polygons.gui.GroupButton;
import edu.uga.miage.m1.polygons.gui.shapes.Shapes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JDrawingFrameTest {

    private JDrawingFrame jDrawingFrame;

    @BeforeEach
    public void setUp() {
        jDrawingFrame = JDrawingFrame.getInstance();
    }

    @Test
    void testGetInstance() {
        assertNotNull(jDrawingFrame);
    }

    @Test
    void testGetDrawTool() {
        assertNotNull(jDrawingFrame.getDrawTool());
    }

    @Test
    void testSetCurrentlySelectedGroupButton() {
        GroupButton groupButton = mock(GroupButton.class);
        jDrawingFrame.setCurrentlySelectedGroupButton(groupButton);
        assertEquals(groupButton, jDrawingFrame.getCurrentlySelectedGroupButton());
    }

    @Test
    void testGetDrawnShapes() {
        assertTrue(jDrawingFrame.getDrawnShapes().isEmpty());
    }

    @Test
    void testGetShapeButtons() {
        assertEquals(Shapes.values().length, jDrawingFrame.getShapeButtons().size());
    }

    @Test
    void testGetShapeSelected() {
        assertNull(jDrawingFrame.getShapeForm());
    }

    @Test
    void testGetPanel() {
        assertNotNull(jDrawingFrame.getPanel());
    }

    @Test
    void testGetGroupButtons() {
        assertEquals(5, jDrawingFrame.getGroupButtons().size());
    }

    @Test
    void testSetShapeSelected() {
        jDrawingFrame.setShapeForm(Shapes.CIRCLE);
        assertEquals(Shapes.CIRCLE, jDrawingFrame.getShapeForm());
    }

    @Test
    void testGetCursorButton() {
        assertNotNull(jDrawingFrame.getCursorButton());
    }
}