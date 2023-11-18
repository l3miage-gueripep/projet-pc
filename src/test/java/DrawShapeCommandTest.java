import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.awt.Graphics2D;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import edu.uga.miage.m1.polygons.gui.commands.DrawShapeCommand;
import edu.uga.miage.m1.polygons.gui.shapes.Circle;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

class DrawShapeCommandTest {
    private DrawShapeCommand drawShapeCommand;
    
    @Mock
    private Graphics2D mockGraphics;
    
    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        SimpleShape shape = new Circle(0, 0);
        this.drawShapeCommand= new DrawShapeCommand(shape);
    }

    @Test
    void constructorTest(){
        assertNotNull(this.drawShapeCommand);
    }
}
