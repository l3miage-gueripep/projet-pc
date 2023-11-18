package edu.uga.miage.m1.polygons.gui.persistence;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import edu.uga.miage.m1.polygons.gui.shapes.Circle;
import edu.uga.miage.m1.polygons.gui.shapes.Cube;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;
import edu.uga.miage.m1.polygons.gui.shapes.Square;
import edu.uga.miage.m1.polygons.gui.shapes.Triangle;

/**
 * @author <a href="mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 */
public class JSonVisitor implements Visitor {

    private JsonObjectBuilder jsonObjectBuilder;

    public JSonVisitor(){
        jsonObjectBuilder = Json.createObjectBuilder();
    }

    private void visitShape(String type, SimpleShape shape) {
        jsonObjectBuilder.add("id", shape.getId())
            .add("type", type)
            .add("x", shape.getX())
            .add("y", shape.getY());
    }

    @Override
    public void visit(Circle circle) {
        visitShape("circle", circle);
    }

    @Override
    public void visit(Square square) {
        visitShape("square", square);
    }

    @Override
    public void visit(Triangle triangle) {
        visitShape("triangle", triangle);
    }

    @Override
    public void visit(Cube cube) {
        visitShape("cube", cube);
    }

    public String getRepresentation() {
        JsonObject jsonObject = jsonObjectBuilder.build();
        return jsonObject.toString();
    }

    public JsonObject getJsonObject(){
        return this.jsonObjectBuilder.build();
    }


}
