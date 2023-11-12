import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;

import edu.uga.miage.m1.polygons.gui.shapes.Square;

class SquareTest {
    @Mock
    private Graphics2D mockGraphics;

    private Square square;
    private int x, y;
    
    @BeforeEach
    void setUp(){
        square = new Square(x, y);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testConstructor() {
        assertNotNull(square);
        assertEquals(square.getX(), x - 25);
        assertEquals(square.getY(), y - 25);
    }

    @Test 
    void drawTest(){
        square.draw(mockGraphics);

        // on verifie que la methode setRenderingHint est appelée
        verify(mockGraphics).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // ensuite on verifie la creation de gradienpaint
        verify(mockGraphics).setPaint(any(GradientPaint.class));
        // verifier l'appel de methodes fill et draw
        verify(mockGraphics).fill(any(Rectangle2D.Double.class));
        verify(mockGraphics).draw(any(Rectangle2D.Double.class));

        // Verifiction des reglages
        verify(mockGraphics).setStroke(any(BasicStroke.class));
        verify(mockGraphics).setColor(Color.black);
    }
}
