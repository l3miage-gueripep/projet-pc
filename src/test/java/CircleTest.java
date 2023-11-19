import edu.uga.miage.m1.polygons.gui.shapes.Circle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CircleTest {

    private Circle circle;

    @BeforeEach
    public void setUp() {
        circle = new Circle(10, 20);
    }

    @Test
    void testIsInside() {
        assertTrue(circle.isInside(15, 25));
        assertFalse(circle.isInside(100, 100));
    }

    @Test
    void testToggleSelected() {
        assertFalse(circle.getSelected());
        circle.toggleSelected();
        assertTrue(circle.getSelected());
    }
}