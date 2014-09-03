/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editor.domain;

import editor.service.KnotService;
import editor.service.MessageService;
import editor.service.TriangulateService;
import java.awt.Color;
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
    private List<Knot> knots;
    private boolean completed;
    private boolean drawKnotcheck;

    public Polygon() {
        this.points = new ArrayList<>();
        this.lines = new ArrayList<>();
        this.triangles = new ArrayList<>();
        this.knots = new ArrayList<>();
        drawKnotcheck = false;
    }

    public Polygon(List<Point> points, List<Line> lines, List<Triangle> triangles, List<Knot> knots) {
        this.points = points;
        this.lines = lines;
        this.triangles = triangles;
        this.knots = knots;
        drawKnotcheck = false;
    }

    public void addPoint(int x, int y) {
        //Point startPoint = getLineStartPoint();
        //Point newPoint = new Point(x, y);

        //if (points.lastIndexOf(startPoint) == points.size() - 1) {
        points.add(new Point(x, y));
        System.out.println(TriangulateService.checkPointInsidePolygon(this, new Point(x, y)));
        //} else {
        //    points.add(points.lastIndexOf(startPoint) + 1, newPoint);
        //}

        //if (startPoint != null) {
        //    lines.add(new Line(startPoint, newPoint, Line.BORDER_OUTER_SEGMENT));
        //}
        //setLastAsHightlighted();
    }

    public void addLine(Line line, boolean doChecks) {

        if (doChecks) {
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
        } else {
            TriangulateService.determineTrianglesFromNewLine(this, line);
            this.lines.add(line);
        }
    }
    
    public void addKnot(int x, int y, IDrawable prev) {
        
        for(Knot k : knots) {
            if(k.getPrevious() == prev)
                return;
        }
        knots.add(new Knot(x, y, prev));
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

    public void deleteInnerLines() {
        List<Line> innerLines = new ArrayList<>();
        for (Line l : lines) {
            if (l.getType() == Line.INNER_SEGMENT) {
                innerLines.add(l);
            }
        }
        lines.removeAll(innerLines);
    }

    public void deleteSelectedItems() {
        List<Point> selectedPoints = new ArrayList<>();
        List<Line> selectedLines = new ArrayList<>();
        List<Triangle> selectedTriangles = new ArrayList<>();
        List<Knot> selectedKnots = new ArrayList<>();

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

        for (Line l : selectedLines) {
            for (Triangle t : triangles) {
                if (t.containsLine(l)) {
                    selectedTriangles.add(t);
                }
            }
        }
        
        for (Knot k : knots) {
            if (!selectedKnots.contains(k)) {
                if (k.isSelected()) {
                    selectedKnots.addAll(deleteKnotList(new ArrayList<Knot>(), k));
                } else {
                    for (Point p : selectedPoints) {
                        if (k.getPrevious() == p) {
                            selectedKnots.addAll(deleteKnotList(new ArrayList<Knot>(), k));
                        }
                    }
                }
            }
        }
        
        

        points.removeAll(selectedPoints);
        lines.removeAll(selectedLines);
        triangles.removeAll(selectedTriangles);
        knots.removeAll(selectedKnots);

        //setLastAsHightlighted();
    }
    
    private ArrayList<Knot> deleteKnotList(ArrayList<Knot> deleteKnots, Knot knot){
        deleteKnots.add(knot);
        for (Knot k : knots) {
            if (k.getPrevious() == knot)
                return deleteKnotList(deleteKnots, k);
        }
        return deleteKnots;
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
    
    public void drawKnotCheck(Graphics2D g, double scale) {
        Point pointK = null;
        Double size = null;
        List<Line> outerLines = new ArrayList<>();
        List<Triangle> knotTriangles = new ArrayList<>();
        
        for (Point p : points) {
            if(p.isSelected())
                pointK = p;
        }
        if (pointK == null) {
            return;
        }
        for (Triangle t : triangles) {
            if (t.containsPoint(pointK)) {
                knotTriangles.add(t);
            }
        }
        for (Triangle t : knotTriangles) {
            for (Line l : t.getLines()) {
                if(l.getStartPoint() == pointK || l.getEndPoint() == pointK){
                    double length = KnotService.lineLength(l);
                    System.out.println("Line length: "+length);
                    if(size == null || length < size)
                        size = length;
                    
                    if(l.getType() == 0 || l.getType() == 1)
                        outerLines.add(l);
                }
            }
        }
        if(outerLines.isEmpty()){
            drawInnerKnotCheck(pointK, (int)Math.round(size), knotTriangles, g, scale);
        } else {
            drawEdgeKnotCheck(pointK, (int)Math.round(size), outerLines, knotTriangles, g, scale);
        }
    }
    
    private void drawInnerKnotCheck(Point pointK, int size, List<Triangle> knotTriangles, Graphics2D g, double scale){
        System.out.println("drawing inner knot check");
    }
    
    private void drawEdgeKnotCheck(Point pointK, int size, List<Line> outerLines, List<Triangle> knotTriangles, Graphics2D g, double scale){
        double totalAngle = 0;
        for (Triangle t : knotTriangles){
            List<Line> angledLines = new ArrayList<>();
            for (Line l : t.getLines()){
                if(l.getStartPoint() == pointK || l.getEndPoint() == pointK){
                    angledLines.add(l);
                }
            }
            totalAngle += KnotService.angleDegreesBetween2Lines(pointK, angledLines.get(0), angledLines.get(1));
        }
        double heading1 = KnotService.lineHeading(pointK, outerLines.get(0));
        double heading2 = KnotService.lineHeading(pointK, outerLines.get(1));
        double startAngle = 0;
        double arcAngle = 0;
        if(totalAngle > 180){
            arcAngle = 360 - totalAngle;
            if (Math.round(heading1 + arcAngle) == Math.round(heading2) || 
                    Math.round(heading1 -totalAngle) == Math.round(heading2)){
                startAngle = heading2;
            } else {
                startAngle = heading1;
            }
            
        } else {
            arcAngle = totalAngle;
            if (Math.round(heading1 - arcAngle) == Math.round(heading2) || 
                    Math.round(heading1 + (360-arcAngle)) == Math.round(heading2)){
                startAngle = heading1 + 180;
            } else {
                startAngle = heading2 + 180;
            }
        }
        
        startAngle = -(startAngle - 90);
        
        g.setColor(KnotService.specialGreen);
        g.fillArc(pointK.getX()-(size/2), pointK.getY()-(size/2),
                size, size, (int)Math.round(startAngle), (int)Math.round(arcAngle));
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
        
        for (IDrawable k : this.knots) {
            k.draw(g, scale);
        }
        
        if(drawKnotcheck)
            drawKnotCheck(g, scale);
    }

    @Override
    public IDrawable checkSelection(int mouseX, int mouseY, boolean multiSelect) {
        List<IDrawable> drawables = new ArrayList<>();
        drawables.addAll(points);
        drawables.addAll(lines);
        drawables.addAll(triangles);
        drawables.addAll(knots);

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
        
        for (Knot k : knots) {
            if (k.isSelected()) {
                sp.add(k);
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

    public List<Knot> getKnots() {
        return knots;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void setDrawKnotcheck(boolean drawKnotcheck) {
        this.drawKnotcheck = drawKnotcheck;
    }
}
