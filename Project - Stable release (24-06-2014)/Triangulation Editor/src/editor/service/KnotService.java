/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package editor.service;

import editor.domain.Line;
import editor.domain.Point;
import java.awt.Color;

/**
 *
 * @author Vincent
 */
public class KnotService {
    
    public static Color specialGreen = new Color(50, 200, 50, 255);
    
    public static double angleDegreesBetween2Lines(Point p1, Line l1, Line l2){
        
        Point p2;
        Point p3;
        if(l1.getStartPoint() == p1)
            p2 = l1.getEndPoint();
        else
            p2 = l1.getStartPoint();
        
        if(l2.getStartPoint() == p1)
            p3 = l2.getEndPoint();
        else
            p3 = l2.getStartPoint();
        
        double angle1 = Math.atan2(p2.getX()- p1.getX(), 
                p2.getY()- p1.getY());
        double angle2 = Math.atan2(p1.getX()- p3.getX(), 
                p1.getY()- p3.getY());
        double result = (angle1 - angle2) * 57.2957795;
        if(result < 0)
            result = -result;
        if (result > 180)
            result = result - 180;
        return result;
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
        heading = heading * 57.2957795;
        if(heading < 0) {
            heading = -heading;
        } else {
            heading = 360 - heading;
        }
        return heading;
    }
}
