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
import java.awt.geom.Rectangle2D;
import edu.uga.miage.m1.polygons.gui.persistence.Visitor;

/**
 * This class implements the square <tt>SimpleShape</tt> extension.
 * It simply provides a <tt>draw()</tt> that paints a square.
 *
 * @author <a href="mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 */
public class Square extends SimpleShape {
    private Color borderColor = Color.BLACK;

    private Rectangle2D rectangle;

    public Square(int x, int y) {
        super(x, y);
        this.rectangle = new Rectangle2D.Double(this.x, this.y, size, size);
    }

    /**
     * Implements the <tt>SimpleShape.draw()</tt> method for painting
     * the shape.
     * @param panelGraphics2d The graphics object used for painting.
     */
    public void draw(Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint gradient = new GradientPaint(x, y, Color.BLUE, (float) x + size, y, Color.WHITE);
        g2.setPaint(gradient);
        g2.fill(rectangle);
        BasicStroke wideStroke = new BasicStroke(2.0f);
        g2.setColor(super.getBorderColor());
        g2.setStroke(wideStroke);
        g2.draw(rectangle);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public boolean isInside(int x, int y) {
        return rectangle.contains(x, y);
    }

    @Override
    public void setCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
        this.rectangle.setRect(x, y, size, size);
    }

    public void applySize(Graphics2D g2, int size) {
        int halfDifference = 0;
        if(size > this.size){
            halfDifference = (size - this.size)/2;
        }
        this.size = size;
        this.rectangle.setRect(x - halfDifference, y - halfDifference, size, size);
        draw(g2);
    }
}
