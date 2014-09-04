/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package editor.service;

import editor.domain.Line;
import editor.domain.Point;
import java.awt.BorderLayout;
import java.awt.Color;

/**
 *
 * @author Vincent
 */
public class KnotService {
    
    public static Color specialGreen = new Color(50, 200, 50, 255);
    
    public static double angleDegreesBetween2Lines(Point p1, Line l1, Line l2){
        
        Double heading1 = lineHeading(p1, l1);
        Double heading2 = lineHeading(p1, l2);
        Double result1;
        Double result2;
        
        if(heading1 > heading2){
            result1 = heading1;
            result2 = heading2;
        } else {
            result1 = heading2;
            result2 = heading1;
        }
        
        if (result1 - result2 > 180) {
            return 360 - result1 + result2;
        } else {
            return result1 - result2;
        }
    }
    
    public static double lineHeading(Point p1, Line l1){
        Point p2;
        if(l1.getStartPoint() == p1){
            p2 = l1.getEndPoint();
        } else {
            p2 = l1.getStartPoint();
        }
        
        double heading = Math.atan2(p1.getX()- p2.getX(), 
                p1.getY()- p2.getY());
        heading = Math.toDegrees(heading);
        if(heading < 0) {
            heading = -heading;
        } else {
            heading = 360 - heading;
        }
        return heading;
    }
    
    public static double lineLength(Line l){
        Point p1 = l.getStartPoint();
        Point p2 = l.getEndPoint();
        int n1 = p1.getX() - p2.getX();
        int n2 = p1.getY() - p2.getY();
        double result = Math.sqrt(Math.pow(n1, 2) + Math.pow(n2, 2) );
        
        return result;
    }
    
    public static Line calcPointByBearingAndDistance(Point p1, double distance, double heading){
        
        double bearing = Math.toRadians(heading);
        
        int finalX = p1.getX() + (int)Math.round(distance * Math.sin(bearing));
        int finalY = p1.getY() -(int)Math.round(distance * Math.cos(bearing));
        
        Point dest = new Point(finalX, finalY);
        System.out.println("finalX: "+finalX+" finalY: "+finalY);
        return new Line(p1, dest, 2);
    }
}
