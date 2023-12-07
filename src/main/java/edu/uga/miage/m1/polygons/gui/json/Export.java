package edu.uga.miage.m1.polygons.gui.json;

import java.util.List;


public class Export {
    private List<ShapeData> shapes;
    private List<GroupData> groups;

    public List<GroupData> getGroups() {
        return groups;
    }
    public List<ShapeData> getShapes() {
        return shapes;
    }
    public void setGroups(List<GroupData> groups) {
        this.groups = groups;
    }
    public void setShapes(List<ShapeData> shapes) {
        this.shapes = shapes;
    }
}
