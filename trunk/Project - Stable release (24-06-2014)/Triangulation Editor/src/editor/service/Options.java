/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package editor.service;

/**
 *
 * @author Daan
 */
public class Options {
    
    private static boolean showGrid = true;
    private static boolean showScale = true;
    private static boolean showPointLocation = true;
    private static boolean showTriangleNumber = true;
    
    // Options for auto-triangulating
    private static int NumberOfRayChecks = 8;
    private static int OutOfPolygonCheck = 1;
    private static int MinAngleBetweenLines = 10;
    private static int MaxNumberOfTrianglesOnPoint = 3;
    
    private static int correctNegative(int value) {
        
        if (value < 0) {
            return 0;
        }
        return value;
    }
    
    public static boolean isShowScale() {
        return showScale;
    }

    public static void setShowScale(boolean showGrid) {
        Options.showScale = showGrid;        
    }

    public static int getNumberOfRayChecks() {
        return NumberOfRayChecks;
    }

    public static void setNumberOfRayChecks(int value) {
        Options.NumberOfRayChecks = correctNegative(value);
    }

    public static int getOutOfPolygonCheck() {
        return OutOfPolygonCheck;
    }

    public static void setOutOfPolygonCheck(int value) {
        Options.OutOfPolygonCheck = correctNegative(value);
    }

    public static int getMinAngleBetweenLines() {
        return MinAngleBetweenLines;
    }

    public static void setMinAngleBetweenLines(int value) {
        Options.MinAngleBetweenLines = correctNegative(value);
    }

    public static int getMaxNumberOfTrianglesOnPoint() {
        return MaxNumberOfTrianglesOnPoint;
    }

    public static void setMaxNumberOfTrianglesOnPoint(int value) {
        Options.MaxNumberOfTrianglesOnPoint = correctNegative(value);
    }     

    public static boolean isShowGrid() {
        return showGrid;
    }

    public static void setShowGrid(boolean showGrid) {
        Options.showGrid = showGrid;
    }

    public static boolean isShowPointLocation() {
        return showPointLocation;
    }

    public static void setShowPointLocation(boolean showPointLocation) {
        Options.showPointLocation = showPointLocation;
    }

    public static boolean isShowTriangleNumber() {
        return showTriangleNumber;
    }

    public static void setShowTriangleNumber(boolean showTriangleNumber) {
        Options.showTriangleNumber = showTriangleNumber;
    }
    
    
}
