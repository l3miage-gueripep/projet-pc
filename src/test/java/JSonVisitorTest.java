import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import javax.json.JsonObject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import edu.uga.miage.m1.polygons.gui.persistence.JSonVisitor;
import edu.uga.miage.m1.polygons.gui.shapes.Circle;
import edu.uga.miage.m1.polygons.gui.shapes.Square;
import edu.uga.miage.m1.polygons.gui.shapes.Triangle;

class JSonVisitorTest {
    @Mock
    Circle circle;

    @Mock
    Square square;

    @Mock
    Triangle triangle;

    private JSonVisitor jsonVisitor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        jsonVisitor = new JSonVisitor();
    }

    @Test
    void testVisitCircle() {
        when(circle.getX()).thenReturn(10);
        when(circle.getY()).thenReturn(20);
        jsonVisitor.visit(circle);
        JsonObject jsonObject = jsonVisitor.getJsonObject();

        assertEquals("circle", jsonObject.getString("type"));
        assertEquals(10, jsonObject.getInt("x"));
        assertEquals(20, jsonObject.getInt("y"));
    }

    @Test
    void testVisitSquare() {
        when(square.getX()).thenReturn(30);
        when(square.getY()).thenReturn(40);

        jsonVisitor.visit(square);
        JsonObject jsonObject = jsonVisitor.getJsonObject();

        assertEquals("square", jsonObject.getString("type"));
        assertEquals(30, jsonObject.getInt("x"));
        assertEquals(40, jsonObject.getInt("y"));
    }

    @Test
    void testVisitTriangle() {
        when(triangle.getX()).thenReturn(50);
        when(triangle.getY()).thenReturn(60);

        jsonVisitor.visit(triangle);
        JsonObject jsonObject = jsonVisitor.getJsonObject();

        assertEquals("triangle", jsonObject.getString("type"));
        assertEquals(50, jsonObject.getInt("x"));
        assertEquals(60, jsonObject.getInt("y"));
    }


    @Test
    void testGetRepresentation() {
        // Call the method you want to test
        String representation = jsonVisitor.getRepresentation();

        // Define the expected result based on your mock setup
        JsonObject jsonObject = jsonVisitor.getJsonObject();
        String expectedRepresentation = jsonObject.toString();

        // Assert that the actual result matches the expected result
        assertEquals(expectedRepresentation, representation);
    }
}
