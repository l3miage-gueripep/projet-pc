package edu.uga.miage.m1.polygons.gui.persistence;

import edu.uga.miage.m1.polygons.gui.shapes.Circle;
import edu.uga.miage.m1.polygons.gui.shapes.Cube;
import edu.uga.miage.m1.polygons.gui.shapes.Square;
import edu.uga.miage.m1.polygons.gui.shapes.Triangle;

/**
 * @author <a href="mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 */
public class JSonVisitor implements StringVisitor {

    @Override
    public String visit(Circle circle) {
        return "circle";
    }

    @Override
    public String visit(Square square) {
        return "square";
    }

    @Override
    public String visit(Triangle triangle) {
        return "triangle";
    }

    @Override
    public String visit(Cube cube) {
        return "cube";
    }


}
