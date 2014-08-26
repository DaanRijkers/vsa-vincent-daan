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
import java.util.List;

/**
 *
 * @author Daan
 */
public class TriangulateService {

    private static int nextTriangle = 0; 
    
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
                            && (l3.getStartPoint() == searchPoint || l3.getEndPoint() == searchPoint)) {

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
        System.out.println("FOUND TRIANGLE");
        System.out.println(l1.getStartPoint() + " - " + l1.getEndPoint());
        System.out.println(l2.getStartPoint() + " - " + l2.getEndPoint());
        System.out.println(l3.getStartPoint() + " - " + l3.getEndPoint());

        List<Line> ls = new ArrayList<>();
        ls.add(l1);
        ls.add(l2);
        ls.add(l3);

        return new Triangle(ls, Color.ORANGE, nextTriangle += 1);
    }

}
