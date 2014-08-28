/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editor.domain;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Daan
 */
public class Triangle implements IDrawable, Serializable {

    private List<Line> lines;
    private List<Point> points;
    private Point center;

    private boolean selected;
    private Color color;
    private int number;

    public Triangle(List<Line> lines, Color color, int number) {
        this.lines = lines;
        this.color = color;
        this.number = number;
        this.selected = false;

        extractPoints();
    }

    private void extractPoints() {

        Set pointsSet = new HashSet();
        for (Line l : lines) {
            pointsSet.add(l.getStartPoint());
            pointsSet.add(l.getEndPoint());
        }

        this.points = new ArrayList<>(pointsSet);
    }

    public void determineCenter() {

        int lowestX = 10000;
        int highestX = 0;
        int lowestY = 10000;
        int highestY = 0;

        for (Point p : createInnerTriangle().getPoints()) {
            lowestX = (p.getX() < lowestX) ? p.getX() : lowestX;
            highestX = (p.getX() > highestX) ? p.getX() : highestX;
            lowestY = (p.getY() < lowestY) ? p.getY() : lowestY;
            highestY = (p.getY() > highestY) ? p.getY() : highestY;
        }

        center = new Point((lowestX + highestX) / 2, (lowestY + highestY) / 2);
    }

    private Triangle createInnerTriangle() {

        List<Line> innerLines = new ArrayList<>();
        innerLines.add(createInnerLine(lines.get(0), lines.get(1)));
        innerLines.add(createInnerLine(lines.get(1), lines.get(2)));
        innerLines.add(createInnerLine(lines.get(2), lines.get(0)));

        return new Triangle(innerLines, Color.CYAN, 9001);
    }

    private Line createInnerLine(Line l1, Line l2) {

        int p1x = (l1.getStartPoint().getX() + l1.getEndPoint().getX()) / 2;
        int p1y = (l1.getStartPoint().getY() + l1.getEndPoint().getY()) / 2;

        int p2x = (l2.getStartPoint().getX() + l2.getEndPoint().getX()) / 2;
        int p2y = (l2.getStartPoint().getY() + l2.getEndPoint().getY()) / 2;

        Point p1 = new Point(p1x, p1y);
        Point p2 = new Point(p2x, p2y);

        return new Line(p1, p2, Line.INNER_SEGMENT);
    }

    public boolean containsPoint(Point p) {
        return this.points.contains(p);
    }

    public boolean containsLine(Line l) {
        return this.lines.contains(l);
    }

    @Override
    public void draw(Graphics2D g, double scale) {

        // Get coordinates into arrays
        int[] x = new int[points.size()];
        int[] y = new int[points.size()];

        for (int i = 0; i < points.size(); i++) {
            x[i] = points.get(i).getX();
            y[i] = points.get(i).getY();
        }

        // Draw triangle
        g.setColor(color);
        try {

            g.fillPolygon(x, y, points.size());

            // Draw clickable center
            determineCenter();
            g.setColor(Color.BLACK);
            Ellipse2D.Double circle = new Ellipse2D.Double(center.getX() - 10, center.getY() - 10, 20, 20);
            g.draw(circle);

            // Draw triangle number
            String text = String.valueOf(number);
            text = (text.length() < 2) ? "0" + text : text;
            g.drawString(text, center.getX() - 5, center.getY() + 5);
        } catch (InternalError ex) {
            System.out.println("Java Internal Error: \"Something not implemented yet\".");
        }
    }

    @Override
    public IDrawable checkSelection(int mouseX, int mouseY, boolean multiSelect) {
//        if (((center.getX() - 10) < mouseX && mouseX < (center.getX() + 10))
//                && ((center.getY() - 10) < mouseY && mouseY < (center.getY() + 10))) {
//            this.selected = !this.selected;
//            return this;
//        } else if (this.selected == true && multiSelect == true) {
//            return this;
//        } else {
//            this.selected = false;
//            return null;
//        }
        return null;
    }

    @Override
    public List<IDrawable> checkSelection(int mouseX, int mouseY, int width, int height, boolean multiSelect) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<Line> getLines() {
        return lines;
    }

    public List<Point> getPoints() {
        return points;
    }

    public Point getCenter() {
        return center;
    }

}
