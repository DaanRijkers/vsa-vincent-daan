/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editor.domain;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.Serializable;

/**
 *
 * @author Daan
 */
public class Line implements IDrawable, Serializable {
    
    private static final Color NORMAL_COLOR = Color.RED;
    private static final Color SELECTED_COLOR = Color.ORANGE;
    
    public static final int OUTER_SEGMENT = 0;
    public static final int INNER_SEGMENT = 1;

    private Point startPoint;
    private Point endPoint;
    
    private int type;
    private boolean selected;

    public Line(Point start) {
        this.startPoint = start;
        this.endPoint = null;
        this.type = Line.OUTER_SEGMENT;
        this.selected = false;
    }

    public Line(Point start, Point end, int type) {
        this.startPoint = start;
        this.endPoint = end;
        this.type = type;
        this.selected = false;
    }

    @Override
    public void draw(Graphics2D g, double scale) {        
        g.setColor((selected) ? SELECTED_COLOR : NORMAL_COLOR);
        g.drawLine(startPoint.getX(),
                startPoint.getY(),
                endPoint.getX(),
                endPoint.getY());
    }
    
    @Override
    public IDrawable checkSelection(int mouseX, int mouseY, boolean multiSelect) {
        if (((startPoint.getX() - 5) < mouseX && mouseX < (startPoint.getX() + 5)) && 
                ((startPoint.getY() - 5) < mouseY && mouseY < (startPoint.getY() + 5))) {
            
        }
        
        return null;
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    
    
}
