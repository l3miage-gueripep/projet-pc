/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package edu.uga.miage.m1.polygons.gui.shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;
import edu.uga.miage.m1.polygons.gui.persistence.Visitor;


public class Triangle implements SimpleShape  {
    private int x;
    private int y;
    private int size = 50;

    private GeneralPath triangle;

    private int[] xcoords;
    private int[] ycoords;

    public Triangle(int x, int y) {
        setCoordinates(x - size / 2, y - size / 2);
    }

    public void draw(Graphics2D g2) {
        this.triangle = new GeneralPath(Path2D.WIND_EVEN_ODD, xcoords.length);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint gradient = new GradientPaint(x,  y, Color.GREEN,  (float)x + size,  y, Color.WHITE);
        g2.setPaint(gradient);
        triangle.moveTo((float)x + size, y + size);
        for (int i = 0; i < xcoords.length; i++) {
            triangle.lineTo(xcoords[i], ycoords[i]);
        }
        triangle.closePath();
        g2.fill(triangle);
        BasicStroke wideStroke = new BasicStroke(2.0f);
        g2.setColor(Color.black);
        g2.setStroke(wideStroke);
        g2.draw(triangle);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public boolean isInside(int x, int y) {
        return triangle.contains(x, y);
    }


    @Override
    public void setCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
        xcoords = new int[]{x + size/2, x, x + size};
        ycoords = new int[]{y, y + size, y + size};
    }

    public int getSize() {
        return this.size;
    }

    public void applySize(Graphics2D g2, int size) {
        int halfDifference = 0;
        if(size > this.size){
            halfDifference = (size - this.size)/2;
        }
        this.size = size;
        this.setCoordinates(x-halfDifference, y-halfDifference);
        draw(g2);
    }

    @Override
    public void setSize(int size) {
        this.size = size;
    }
}
