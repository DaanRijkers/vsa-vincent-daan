/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package editor.service;

import javax.swing.JSpinner;

/**
 *
 * @author Daan
 */
public class Options {
    
    private static boolean showScale = true;
    
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
}
