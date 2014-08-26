/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editor.domain;

import editor.service.MessageService;
import editor.service.TriangulateService;
import java.awt.Graphics2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Daan
 */
public class Polygon implements IDrawable, Serializable {

    private List<Point> points;
    private List<Line> lines;
    private List<Triangle> triangles;
    private boolean completed;

    public Polygon() {
        this.points = new ArrayList<>();
        this.lines = new ArrayList<>();
        this.triangles = new ArrayList<>();
    }

    public Polygon(List<Point> points, List<Line> lines, List<Triangle> triangles) {
        this.points = points;
        this.lines = lines;
        this.triangles = triangles;
    }

    public void addPoint(int x, int y) {
        //Point startPoint = getLineStartPoint();
        //Point newPoint = new Point(x, y);

        //if (points.lastIndexOf(startPoint) == points.size() - 1) {
        points.add(new Point(x, y));
        //} else {
        //    points.add(points.lastIndexOf(startPoint) + 1, newPoint);
        //}

        //if (startPoint != null) {
        //    lines.add(new Line(startPoint, newPoint, Line.BORDER_OUTER_SEGMENT));
        //}
        //setLastAsHightlighted();
    }

    public void addLine(Line line) {

        if (checkDoubleLine(line.getStartPoint(), line.getEndPoint())) {
            MessageService.showDoubleLineMessage();
        } else if (line.getStartPoint() == line.getEndPoint()) {
            MessageService.showLineToSelfMessage();
        } else if ((line.getType() == Line.BORDER_OUTER_SEGMENT || line.getType() == Line.BORDER_INNER_SEGMENT)
                && (countLines(line.getStartPoint(), Line.BORDER_OUTER_SEGMENT) > 1
                || countLines(line.getStartPoint(), Line.BORDER_INNER_SEGMENT) > 1
                || countLines(line.getEndPoint(), Line.BORDER_OUTER_SEGMENT) > 1
                || countLines(line.getEndPoint(), Line.BORDER_INNER_SEGMENT) > 1)) {
            MessageService.showToManyLinesMessage();
        } else {
            TriangulateService.determineTrianglesFromNewLine(this, line);

            this.lines.add(line);
        }
    }

    private void setLastAsHightlighted() {
        for (Point p : points) {
            p.setHighlighted(false);
        }

        Point p = getLineStartPoint();
        if (p != null) {
            p.setHighlighted(true);
        }
    }

//    public boolean canConnectToPoint(Point p) {
//        Point startPoint = getLineStartPoint();
//
//        if (startPoint != p && countLines(p) < 2 && !checkDoubleLine(startPoint, p)) {
//            return true;
//        }
//        return false;
//    }
    public void connectSegment(Point p) {
        Point startPoint = getLineStartPoint();

        //if (startPoint != p && countLines(p) < 2 && !checkDoubleLine(startPoint, p)) {
        lines.add(new Line(startPoint, p, Line.BORDER_OUTER_SEGMENT));
        //}

        setLastAsHightlighted();
    }

    private boolean checkDoubleLine(Point p1, Point p2) {
        for (Line l : lines) {
            if ((l.getStartPoint() == p1 && l.getEndPoint() == p2)
                    || (l.getStartPoint() == p2 && l.getEndPoint() == p1)) {
                return true;
            }
        }
        return false;
    }

    private int countLines(Point p, int t) {
        int counter = 0;

        for (Line l : lines) {
            if (l.getType() == t && (l.getStartPoint() == p || l.getEndPoint() == p)) {
                counter++;
            }
        }

        return counter;
    }

    private Point getLineStartPoint() {
        for (Point p : points) {

            int counter = countLines(p, Line.BORDER_OUTER_SEGMENT);
            if ((counter != 2 && points.indexOf(p) != 0) || (points.indexOf(p) == 0 && counter == 0)) {
                return p;
            }
        }
        return null;

        // TODO, REPLACE WITH:        
//        for (Point p : points) {
//            if (p.isHighlighted()) {
//                return true;
//            }
//        }
    }

    public void deleteSelectedItems() {
        List<Point> selectedPoints = new ArrayList<>();
        List<Line> selectedLines = new ArrayList<>();
        List<Triangle> selectedTriangles = new ArrayList<>();

        for (Point p : points) {
            if (p.isSelected()) {
                selectedPoints.add(p);
            }
        }

        for (Line l : lines) {
            if (l.isSelected()) {
                selectedLines.add(l);
            }

            for (Point p : selectedPoints) {
                if (l.getEndPoint() == p || l.getStartPoint() == p) {
                    selectedLines.add(l);
                }
            }
        }

        for (Point p : selectedPoints) {
            for (Triangle t : triangles) {
                if (t.containsPoint(p)) {
                    selectedTriangles.add(t);
                }
            }
        }

        points.removeAll(selectedPoints);
        lines.removeAll(selectedLines);
        triangles.removeAll(selectedTriangles);

        //setLastAsHightlighted();
    }

    public Point getMouseLineStartPoint() {
        if (points.isEmpty()) {
            return null;
        } else {
            return points.get(points.size() - 1);
        }
    }

    public void clearSelection() {
        for (Point p : points) {
            p.setSelected(false);
        }

        for (Line l : lines) {
            l.setSelected(false);
        }
    }

    public void selectAll() {
        for (Point p : points) {
            p.setSelected(true);
        }

        for (Line l : lines) {
            l.setSelected(true);
        }
    }

    public void moveSelection(int moveX, int moveY) {
        for (Point p : points) {
            if (p.isSelected()) {
                p.setX(p.getX() + moveX);
                p.setY(p.getY() + moveY);

                for (Triangle t : triangles) {
                    if (t.containsPoint(p)) {
                        t.determineCenter();
                    }
                }
            }
        }

    }

    @Override
    public void draw(Graphics2D g, double scale) {

        for (IDrawable t : this.triangles) {
            t.draw(g, scale);
        }

        for (IDrawable l : this.lines) {
            l.draw(g, scale);
        }

        for (IDrawable p : this.points) {
            p.draw(g, scale);
        }
    }

    @Override
    public IDrawable checkSelection(int mouseX, int mouseY, boolean multiSelect) {
        List<IDrawable> drawables = new ArrayList<>();
        drawables.addAll(points);
        drawables.addAll(lines);

        IDrawable selected = null;
        for (IDrawable x : drawables) {
            IDrawable d = x.checkSelection(mouseX, mouseY, multiSelect);

            if (d != null) {
                selected = d;
            }

            if (x instanceof Line) {
                Line l = (Line) x;
                if (l.getStartPoint().isSelected() && l.getEndPoint().isSelected()) {
                    l.setSelected(true);
                }
            }

            if (!multiSelect && selected != null) {
                return selected;
            }
        }

        return selected;
    }

    @Override
    public List<IDrawable> checkSelection(int mouseX, int mouseY, int width, int height, boolean multiSelect) {
        return null;
    }

    public List<IDrawable> getSelectedObjects() {
        List<IDrawable> sp = new ArrayList<>();

        for (Point p : points) {
            if (p.isSelected()) {
                sp.add(p);
            }
        }

        for (Line l : lines) {
            if (l.isSelected()) {
                sp.add(l);
            }
        }

        return sp;
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

    public List<Line> getLines() {
        return lines;
    }

    public void setLines(List<Line> lines) {
        this.lines = lines;
    }

    public List<Triangle> getTriangles() {
        return triangles;
    }

    public void setTriangles(List<Triangle> triangles) {
        this.triangles = triangles;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
