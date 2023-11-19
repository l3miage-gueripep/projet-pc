import edu.uga.miage.m1.polygons.gui.shapes.Cube;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CubeTest {

    private Cube cube;

    @BeforeEach
    public void setUp() {
        cube = new Cube(10, 20);
    }

    @Test
    void testIsInside() {
        assertTrue(cube.isInside(15, 25));
        assertFalse(cube.isInside(100, 100));
    }

    @Test
    void testSetCoordinates() {
        cube.setCoordinates(30, 40);
        assertTrue(cube.isInside(35, 45));
    }
}