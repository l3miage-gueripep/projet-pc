package edu.uga.miage.m1.polygons.gui.shapes;
import java.awt.Color;
import java.awt.Graphics2D;
import edu.uga.miage.m1.polygons.gui.persistence.Visitable;

/**
 * This interface defines the <tt>SimpleShape</tt> extension. This extension
 * is used to draw shapes.
 *
 * @author <a href="mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 */
public abstract class SimpleShape implements Visitable {
    protected int x;
    protected int y;
    protected int size = 50;
    protected boolean selected = false;

    public abstract void draw(Graphics2D g2);
    public abstract void setCoordinates(int x, int y);
    
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public abstract boolean isInside(int x, int y);
    public abstract int getSize();
    public abstract void setSize(int size);
    public abstract void applySize(Graphics2D g2, int newSize);
    public void toggleSelected(){
        this.selected = !selected;
    }

    protected Color getBorderColor(){
        return selected ? Color.RED : Color.BLACK;
    }
}
