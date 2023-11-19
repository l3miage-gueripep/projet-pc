import edu.uga.miage.m1.polygons.gui.GroupButton;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class GroupButtonTest {

    private GroupButton groupButton;
    private SimpleShape simpleShape;

    @BeforeEach
    public void setUp() {
        simpleShape = Mockito.mock(SimpleShape.class);
        groupButton = new GroupButton("Test");
    }

    @Test
    void testAddShape() {
        groupButton.addShape(simpleShape);
        assertTrue(groupButton.getShapes().contains(simpleShape));
    }

    @Test
    void testRemoveShape() {
        groupButton.addShape(simpleShape);
        groupButton.removeShape(simpleShape);
        assertFalse(groupButton.getShapes().contains(simpleShape));
    }

    @Test
    void testGetShapes() {
        groupButton.addShape(simpleShape);
        assertEquals(1, groupButton.getShapes().size());
    }
}