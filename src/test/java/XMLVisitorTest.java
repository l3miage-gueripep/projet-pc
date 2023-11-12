
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import edu.uga.miage.m1.polygons.gui.persistence.XMLVisitor;
import edu.uga.miage.m1.polygons.gui.shapes.Circle;
import edu.uga.miage.m1.polygons.gui.shapes.Square;
import edu.uga.miage.m1.polygons.gui.shapes.Triangle;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class XMLVisitorTest {

    @Mock
    Circle circle;

    @Mock
    Square square;

    @Mock
    Triangle triangle;

    private XMLVisitor xmlVisitor;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        xmlVisitor = new XMLVisitor();
    }

    @Test
    void testVisitCircle() {
        when(circle.getX()).thenReturn(10);
        when(circle.getY()).thenReturn(20);

        xmlVisitor.visit(circle);
        String xml = xmlVisitor.getRepresentation();

        // Perform assertions on the generated XML string
        assertTrue(xml.contains("<type>circle</type>"));
        assertTrue(xml.contains("<x>10</x>"));
        assertTrue(xml.contains("<y>20</y>"));
    }

    @Test
    void testVisitSquare() {
        when(square.getX()).thenReturn(30);
        when(square.getY()).thenReturn(40);

        xmlVisitor.visit(square);
        String xml = xmlVisitor.getRepresentation();

        // Perform assertions on the generated XML string
        assertTrue(xml.contains("<type>square</type>"));
        assertTrue(xml.contains("<x>30</x>"));
        assertTrue(xml.contains("<y>40</y>"));
    }

    @Test
    void testVisitTriangle() {
        when(triangle.getX()).thenReturn(50);
        when(triangle.getY()).thenReturn(60);

        xmlVisitor.visit(triangle);
        String xml = xmlVisitor.getRepresentation();

        // Perform assertions on the generated XML string
        assertTrue(xml.contains("<type>triangle</type>"));
        assertTrue(xml.contains("<x>50</x>"));
        assertTrue(xml.contains("<y>60</y>"));
    }
}