package edu.uga.miage.m1.polygons.gui.persistence;

import edu.uga.miage.m1.polygons.gui.shapes.Circle;
import edu.uga.miage.m1.polygons.gui.shapes.Cube;
import edu.uga.miage.m1.polygons.gui.shapes.Square;
import edu.uga.miage.m1.polygons.gui.shapes.Triangle;

public interface StringVisitor {

    public String visit(Circle circle);

    public String visit(Square square);

    public String visit(Triangle triangle);

    public String visit(Cube cube);
}
