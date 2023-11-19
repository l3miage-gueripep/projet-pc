import edu.uga.miage.m1.polygons.gui.shapes.Triangle;
import edu.uga.miage.m1.polygons.gui.persistence.Visitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Graphics2D;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TriangleTest {

    private Triangle triangle;
    private Graphics2D graphics2D;
    private Visitor visitor;

    @BeforeEach
    public void setUp() {
        triangle = new Triangle(10, 20);
        graphics2D = mock(Graphics2D.class);
        visitor = mock(Visitor.class);
    }

    @Test
    void testDraw() {
        triangle.draw(graphics2D);
        // Verify that fill and draw are called on the Graphics2D object
        verify(graphics2D, times(1)).fill(any());
        verify(graphics2D, times(1)).draw(any());
    }


    @Test
    void testApplySize() {
        triangle.applySize(graphics2D, 100);
        assertTrue(triangle.isInside(50, 60));
    }

    @Test
    void testAccept() {
        triangle.accept(visitor);
        verify(visitor, times(1)).visit(triangle);
    }
}