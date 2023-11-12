import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.awt.Graphics2D;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.uga.miage.m1.polygons.gui.DrawTool;
import edu.uga.miage.m1.polygons.gui.commands.Command;
import edu.uga.miage.m1.polygons.gui.commands.DrawShapeCommand;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

class DrawToolTest {
    private DrawTool drawTool;

    @BeforeEach
    public void setUp() {
        // Create a new DrawTool instance for each test
        drawTool = new DrawTool();
    }

    @Test
    void testAddCommand() {
        // Create a Command instance
        SimpleShape shape = mock(SimpleShape.class);
        Graphics2D g2 = mock(Graphics2D.class);
        Command command = new DrawShapeCommand(shape, g2);

        // Add the command to the DrawTool
        drawTool.addCommand(command);

        // Check if the command was added to the DrawTool's list of commands
        List<Command> commands = drawTool.getCommands();
        assertTrue(commands.contains(command));
    }

    @Test
    void testRemoveLastCommand() {
        // Create and add some commands
        SimpleShape shape1 = mock(SimpleShape.class);
        Graphics2D g21 = mock(Graphics2D.class);
        Command command1 = new DrawShapeCommand(shape1, g21);

        SimpleShape shape2 = mock(SimpleShape.class);
        Graphics2D g22 = mock(Graphics2D.class);
        Command command2 = new DrawShapeCommand(shape2, g22);

        drawTool.addCommand(command1);
        drawTool.addCommand(command2);

        // Remove the last command
        drawTool.removeLastCommand();

        // Check if the last command was removed
        List<Command> commands = drawTool.getCommands();
        assertEquals(1, commands.size());
        assertTrue(commands.contains(command1));
    }

    @Test
    void testPlay() {
        // Create and add some commands
        SimpleShape shape1 = mock(SimpleShape.class);
        Graphics2D g21 = mock(Graphics2D.class);
        Command command1 = new DrawShapeCommand(shape1, g21);

        SimpleShape shape2 = mock(SimpleShape.class);
        Graphics2D g22 = mock(Graphics2D.class);
        Command command2 = new DrawShapeCommand(shape2, g22);

        drawTool.addCommand(command1);
        drawTool.addCommand(command2);

        // Execute the commands using play()
        drawTool.play();

        // Verify that the execute method of each command was called
        verify(shape1).draw(g21);
        verify(shape2).draw(g22);
    }

    @Test
    void testReset() {
        // Create and add some commands
        SimpleShape shape1 = mock(SimpleShape.class);
        Graphics2D g21 = mock(Graphics2D.class);
        Command command1 = new DrawShapeCommand(shape1, g21);

        SimpleShape shape2 = mock(SimpleShape.class);
        Graphics2D g22 = mock(Graphics2D.class);
        Command command2 = new DrawShapeCommand(shape2, g22);

        drawTool.addCommand(command1);
        drawTool.addCommand(command2);

        // Reset the commands
        drawTool.reset();

        // Check if the commands list is empty after resetting
        List<Command> commands = drawTool.getCommands();
        assertTrue(commands.isEmpty());
    }
}
