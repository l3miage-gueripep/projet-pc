import edu.uga.miage.m1.polygons.gui.JDrawingFrame;
import edu.uga.miage.m1.polygons.gui.commands.Command;
import edu.uga.miage.m1.polygons.gui.DrawTool;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class DrawToolTest {

    private DrawTool drawTool;
    private JDrawingFrame jDrawingFrame;
    private Command command;

    @BeforeEach
    public void setUp() {
        jDrawingFrame = Mockito.mock(JDrawingFrame.class);
        command = Mockito.mock(Command.class);
        drawTool = new DrawTool(jDrawingFrame);
    }

    @Test
    void testAddCommand() {
        drawTool.addCommand(command);
        assertFalse(drawTool.getCommands().isEmpty());
    }

    @Test
    void testGetLastCommand() {
        drawTool.addCommand(command);
        Command lastCommand = drawTool.getLastCommand();
        assertEquals(command, lastCommand);
    }

    @Test
    void testRemoveLastCommand() {
        drawTool.addCommand(command);
        drawTool.removeLastCommand();
        assertTrue(drawTool.getCommands().isEmpty());
    }

    @Test
    void testReset() {
        drawTool.addCommand(command);
        drawTool.reset();
        assertTrue(drawTool.getCommands().isEmpty());
    }
}