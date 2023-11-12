package edu.uga.miage.m1.polygons.gui.persistence;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import edu.uga.miage.m1.polygons.gui.shapes.Circle;
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

    @Override
    public void visit(Circle circle) {
        jsonObjectBuilder.add("type", "circle")
                .add("x", circle.getX())
                .add("y", circle.getY());
    }

    @Override
    public void visit(Square square) {
        jsonObjectBuilder.add("type", "square")
                .add("x", square.getX())
                .add("y", square.getY());
    }

    @Override
    public void visit(Triangle triangle) {
        jsonObjectBuilder.add("type", "triangle")
                .add("x", triangle.getX())
                .add("y", triangle.getY());
    }

    public String getRepresentation() {
        JsonObject jsonObject = jsonObjectBuilder.build();
        return jsonObject.toString();
    }

    public JsonObject getJsonObject(){
        return this.jsonObjectBuilder.build();
    }
}
