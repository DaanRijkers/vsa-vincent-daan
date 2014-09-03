/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package editor.service;

import editor.domain.Line;
import editor.domain.Point;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Vincent
 */
public class KnotServiceTest {
    
    public KnotServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of angleDegreesBetween2Lines method, of class KnotService.
     */
    @Test
    public void testAngleBetween2Lines() {
        System.out.println("angleBetween2Lines");
        
        Point p1 = new Point(100, 100);
        Point p2 = new Point(150, 50);
        Point p3 = new Point(100, 0);
        
        Line l1 = new Line(p1, p2, 0);
        Line l2 = new Line(p2, p3, 0);
        double result1 = KnotService.angleDegreesBetween2Lines(p2, l1, l2);
        l1 = new Line(p1, p2, 0);
        l2 = new Line(p3, p2, 0);
        double result2 = KnotService.angleDegreesBetween2Lines(p2, l1, l2);
        System.out.println(result1);
        System.out.println(result2);
        long roundedResult1 = Math.round(result1);
        long roundedResult2 = Math.round(result2);
        assertEquals(roundedResult1, roundedResult2, 0.0);
    }
    
    @Test
    public void testLineHeading() {
        System.out.println("lineHeading");
        
        Point p1 = new Point(50, 50);
        Point p2 = new Point(75, 75);
        
        Line l1 = new Line(p1, p2, 0);
        double result1 = KnotService.lineHeading(p1, l1);
        
        System.out.println(result1);
        long roundedResult1 = Math.round(result1);
        assertEquals(roundedResult1, 135, 0.0);
    }
    
    @Test
    public void testLineLenght() {
        System.out.println("lineLength");
        
        Point p1 = new Point(100, 100);
        Point p2 = new Point(100, 200);
        
        Line l1 = new Line(p1, p2, 0);
        double result1 = KnotService.lineLength(l1);
        System.out.println(result1);
        assertEquals(result1, 100, 0.0);
    }
    
}
