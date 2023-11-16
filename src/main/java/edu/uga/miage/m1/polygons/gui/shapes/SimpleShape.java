package edu.uga.miage.m1.polygons.gui.shapes;
import java.awt.Graphics2D;
import edu.uga.miage.m1.polygons.gui.persistence.Visitable;

/**
 * This interface defines the <tt>SimpleShape</tt> extension. This extension
 * is used to draw shapes.
 *
 * @author <a href="mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 */
public interface SimpleShape extends Visitable {

    /**
     * Method to draw the shape of the extension.
     * @param g2 The graphics object used for painting.
     */
    public abstract void draw(Graphics2D g2);

    public abstract void setCoordinates(int x, int y);

    public abstract int getX();

    public abstract int getY();

    public abstract boolean isInside(int x, int y);

    public abstract int getSize();

    public abstract void setSize(int size);

    public abstract void applySize(Graphics2D g2, int newSize);
}
