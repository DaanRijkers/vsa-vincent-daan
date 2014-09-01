/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editor.service;

import editor.domain.Line;
import editor.domain.Point;
import editor.domain.Polygon;
import editor.domain.Triangle;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Daan
 */
public class TriangulateService {

    private static int nextTriangle = 0;

    public static void resetAutonumeric() {
        nextTriangle = 0;
    }

    public static void determineTrianglesFromNewLine(Polygon p, Line newLine) {

        Point p1 = newLine.getStartPoint();
        Point p2 = newLine.getEndPoint();
        List<Line> lines = p.getLines();

        Point searchPoint = null;
        Line secondLine = null;
        List<Triangle> triangles = new ArrayList<>();
        List<Point> oldSearchPoints = new ArrayList<>();

        for (Line l2 : lines) {
            if (l2.getStartPoint() == p1) {
                searchPoint = l2.getEndPoint();
                secondLine = l2;
            } else if (l2.getEndPoint() == p1) {
                searchPoint = l2.getStartPoint();
                secondLine = l2;
            }

            if (searchPoint != null && !oldSearchPoints.contains(searchPoint)) {
                oldSearchPoints.add(searchPoint);

                for (Line l3 : lines) {
                    if (l3 != secondLine
                            && (l3.getStartPoint() == p2 || l3.getEndPoint() == p2)
                            && (l3.getStartPoint() == searchPoint || l3.getEndPoint() == searchPoint)
                            && !(newLine.getType() == Line.BORDER_INNER_SEGMENT
                            && secondLine.getType() == Line.BORDER_INNER_SEGMENT
                            && l3.getType() == Line.BORDER_INNER_SEGMENT)) {

                        triangles.add(createTriangle(newLine, secondLine, l3));
                    }
                }
            }

            if (triangles.size() > 1) {
                break;
            }
        }

        p.getTriangles().addAll(triangles);
    }

    private static Triangle createTriangle(Line l1, Line l2, Line l3) {

        List<Line> ls = new ArrayList<>();
        ls.add(l1);
        ls.add(l2);
        ls.add(l3);

        return new Triangle(ls, Color.ORANGE, nextTriangle += 1);
    }

    public static boolean doLinesCross(Line l1, Line l2, boolean excludeTouch) {

        Point l1a = l1.getStartPoint();
        Point l1b = l1.getEndPoint();
        Point l2a = l2.getStartPoint();
        Point l2b = l2.getEndPoint();

        if (singularCorrespondingPoint(l1, l2)) {
            return false;
        }

        return ((relativeCCW(l1a.getX(), l1a.getY(), l1b.getX(), l1b.getY(), l2a.getX(), l2a.getY())
                * relativeCCW(l1a.getX(), l1a.getY(), l1b.getX(), l1b.getY(), l2b.getX(), l2b.getY()) <= 0)
                && (relativeCCW(l2a.getX(), l2a.getY(), l2b.getX(), l2b.getY(), l1a.getX(), l1a.getY())
                * relativeCCW(l2a.getX(), l2a.getY(), l2b.getX(), l2b.getY(), l1b.getX(), l1b.getY()) <= 0));
    }

    public static int relativeCCW(double x1, double y1,
            double x2, double y2,
            double px, double py) {
        x2 -= x1;
        y2 -= y1;
        px -= x1;
        py -= y1;
        double ccw = px * y2 - py * x2;
        if (ccw == 0.0) {

            ccw = px * x2 + py * y2;
            if (ccw > 0.0) {

                px -= x2;
                py -= y2;
                ccw = px * x2 + py * y2;
                if (ccw < 0.0) {
                    ccw = 0.0;
                }
            }
        }
        return (ccw < 0.0) ? -1 : ((ccw > 0.0) ? 1 : 0);
    }

    public static boolean singularCorrespondingPoint(Line l1, Line l2) {

        if ((l1.getStartPoint().corresponds(l2.getStartPoint()) && !l1.getEndPoint().corresponds(l2.getEndPoint()))
                || (l1.getStartPoint().corresponds(l2.getEndPoint()) && !l1.getEndPoint().corresponds(l2.getStartPoint()))
                || (l1.getEndPoint().corresponds(l2.getStartPoint()) && !l1.getStartPoint().corresponds(l2.getEndPoint()))
                || (l1.getEndPoint().corresponds(l2.getEndPoint()) && !l1.getStartPoint().corresponds(l2.getStartPoint()))) {

            return true;
        }

        return false;
    }

    private static List<Point> sortPointsByLineType(Polygon pol, int type) {

        Set<Point> points = new HashSet<>();

        for (Line l : pol.getLines()) {
            if (l.getType() == type) {
                points.add(l.getStartPoint());
                points.add(l.getEndPoint());
            }
        }

        return new ArrayList<>(points);
    }

    private static List<Point> findLoosePoints(Polygon pol) {

        List<Point> loosePoints = new ArrayList<>();

        for (Point p : pol.getPoints()) {

            boolean connected = false;
            for (Line l : pol.getLines()) {
                if (l.getStartPoint() == p || l.getEndPoint() == p) {
                    connected = true;
                    break;
                }
            }

            if (!connected) {
                loosePoints.add(p);
            }
        }

        return loosePoints;
    }

    public static boolean checkPointInsidePolygon(Polygon pol, Point p) {

        List<Line> rays = new ArrayList<>();

        rays.add(new Line(p, new Point(p.getX(), 0000), Line.BORDER_OUTER_SEGMENT)); // Line upwards
        rays.add(new Line(p, new Point(p.getX(), 9000), Line.BORDER_OUTER_SEGMENT)); // Line downwards
        rays.add(new Line(p, new Point(0000, p.getY()), Line.BORDER_OUTER_SEGMENT)); // Line left sideways
        rays.add(new Line(p, new Point(9000, p.getY()), Line.BORDER_OUTER_SEGMENT)); // Line right sideways

        rays.add(new Line(p, new Point(p.getX() - 9000, p.getY() - 9000), Line.BORDER_OUTER_SEGMENT)); // Line diagionally upper left
        rays.add(new Line(p, new Point(p.getX() + 9000, p.getY() - 9000), Line.BORDER_OUTER_SEGMENT)); // Line diagionally upper right
        rays.add(new Line(p, new Point(p.getX() - 9000, p.getY() + 9000), Line.BORDER_OUTER_SEGMENT)); // Line diagionally lower left
        rays.add(new Line(p, new Point(p.getX() + 9000, p.getY() + 9000), Line.BORDER_OUTER_SEGMENT));  // Line diagionally lower right

        int outerHits = 0;
        int innerHits = 0;

        for (Line r : rays) {

            boolean hasHitOuter = false;
            for (Line l : pol.getLines()) {

                boolean hit = doLinesCross(r, l, true);
                if (hit && l.getType() == Line.BORDER_OUTER_SEGMENT && !hasHitOuter) {
                    outerHits++;
                    hasHitOuter = true;

                } else if (hit && l.getType() == Line.BORDER_INNER_SEGMENT) {
                    innerHits++;
                    break;
                }
            }
        }

        if (innerHits > 7) {
            return false;
        } else if (outerHits > 7) {
            return true;
        }
        return false;
    }

    public static void autoTriangulatePolygon(Polygon pol) {

        pol.getTriangles().clear();
        pol.deleteInnerLines();

        List<Point> outerPoints = sortPointsByLineType(pol, Line.BORDER_OUTER_SEGMENT);
        List<Point> innerPoints = sortPointsByLineType(pol, Line.BORDER_INNER_SEGMENT);
        List<Point> loosePoints = findLoosePoints(pol);

        List<Point> combined = new ArrayList<>();
        combined.addAll(loosePoints);
        combined.addAll(innerPoints);
        combined.addAll(outerPoints);

        for (Point p1 : combined) {

            int counter = 0;
            for (Point p2 : pol.getPoints()) {

                // Check if this point already has enough lines attached to it
                if (!loosePoints.contains(p1)
                        && Options.getMaxNumberOfTrianglesOnPoint() > 1
                        && counter > Options.getMaxNumberOfTrianglesOnPoint() - 1) {
                    //&& pol.getLinesConnectedToPoint(p2).size() >= Options.getMaxNumberOfTrianglesOnPoint()) {
                    break;
                }

                Line newLine;
                boolean crossed = false;

                if (p1 == p2) {
                    continue;

                } else {

                    // Check if the newly created line would cross any other lines
                    newLine = new Line(p1, p2, Line.INNER_SEGMENT);
                    for (Line l : pol.getLines()) {
                        crossed = doLinesCross(newLine, l, true);

                        if (crossed) {
                            break;
                        }
                    }

                    // Check if points on the line fall outside of the polygon
                    // Number of checks per line is specified in the options menu
                    if (Options.getOutOfPolygonCheck() > 0) {
                        boolean outside = false;
                        int amount = Options.getOutOfPolygonCheck() + 1;
                        
                        for (int i = 0; i < Options.getOutOfPolygonCheck(); i++) {

                            int xAddition = ((p2.getX() - p1.getX()) / amount) * (i + 1);
                            int yAddition = ((p2.getY() - p1.getY()) / amount) * (i + 1);

                            Point pointOnLine = new Point(p1.getX() + xAddition, p1.getY() + yAddition);

                            if ((innerPoints.contains(p1) && innerPoints.contains(p2))
                                    || (outerPoints.contains(p1) && outerPoints.contains(p2))) {
                                if (!checkPointInsidePolygon(pol, pointOnLine)) {
                                    outside = true;
                                }
                            }
                        }

                        if (outside) {
                            continue;
                        }
                    }
                }

                if (!crossed) {
                    pol.addLine(newLine, false);
                    counter++;
                }
            }
        }
    }
}
