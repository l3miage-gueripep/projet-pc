import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import edu.uga.miage.m1.polygons.gui.shapes.Circle;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CircleTest {

    private Circle circle;
    private int x, y;
    
    @Mock
    private Graphics2D mockGraphics;
    
    @BeforeEach
    void setUp(){
        circle = new Circle(x, y);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testConstructor() {
        assertNotNull(circle);
        assertEquals(circle.getX(), x - 25);
        assertEquals(circle.getY(), y - 25);
    }

    @Test 
    void drawTest(){
        circle.draw(mockGraphics);

        // on verifie que la methode setRenderingHint est appelée
        verify(mockGraphics).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // ensuite on verifie la creation de gradienpaint
        verify(mockGraphics).setPaint(any(GradientPaint.class));
        // verifier l'appel de methodes fill et draw
        verify(mockGraphics).fill(any(Ellipse2D.Double.class));
        verify(mockGraphics).draw(any(Ellipse2D.Double.class));

        // Verifiction des reglages
        verify(mockGraphics).setStroke(any(BasicStroke.class));
        verify(mockGraphics).setColor(Color.black);
    }
}
