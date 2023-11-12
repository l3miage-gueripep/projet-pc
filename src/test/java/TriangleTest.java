import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import edu.uga.miage.m1.polygons.gui.shapes.Triangle;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;


class TriangleTest {

    private Triangle triangle;
    private int x, y;

    @Mock
    private Graphics2D mockGraphics;
    
    @BeforeEach
    void setUp(){
        triangle = new Triangle(x, y);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void constructorTest() {
        assertNotNull(triangle);
        assertEquals(triangle.getX(), x - 25);
        assertEquals(triangle.getY(), y - 25);
    }

    @Test 
    void drawTest(){
        triangle.draw(mockGraphics);
        verify(mockGraphics).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        verify(mockGraphics).setPaint(any(GradientPaint.class));
        verify(mockGraphics).fill(any(GeneralPath.class));
        verify(mockGraphics).setStroke(any(BasicStroke.class));
        verify(mockGraphics).setColor(Color.black);
    }
}

