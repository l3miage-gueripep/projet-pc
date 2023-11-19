import edu.uga.miage.m1.polygons.gui.shapes.Square;
import edu.uga.miage.m1.polygons.gui.persistence.Visitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Graphics2D;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SquareTest {

    private Square square;
    private Graphics2D graphics2D;
    private Visitor visitor;

    @BeforeEach
    public void setUp() {
        square = new Square(10, 20);
        graphics2D = mock(Graphics2D.class);
        visitor = mock(Visitor.class);
    }

    @Test
    void testDraw() {
        square.draw(graphics2D);
        // Verify that fill and draw are called on the Graphics2D object
        verify(graphics2D, times(1)).fill(any());
        verify(graphics2D, times(1)).draw(any());
    }

    @Test
    void testIsInside() {
        assertTrue(square.isInside(15, 25));
        assertFalse(square.isInside(100, 100));
    }

    @Test
    void testSetCoordinates() {
        square.setCoordinates(30, 40);
        assertTrue(square.isInside(35, 45));
    }

    @Test
    void testApplySize() {
        square.applySize(graphics2D, 100);
        assertTrue(square.isInside(50, 60));
    }

    @Test
    void testAccept() {
        square.accept(visitor);
        verify(visitor, times(1)).visit(square);
    }
}