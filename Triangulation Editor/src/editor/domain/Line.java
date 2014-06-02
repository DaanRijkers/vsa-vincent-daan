/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editor.domain;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Daan
 */
public class Line implements IDrawable, Serializable {
    
    private static final Color NORMAL_COLOR = Color.RED;
    private static final Color SELECTED_COLOR = Color.BLUE;
    
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
       if (checkCruedSelection(mouseX, mouseY) && checkPreciseSelection(mouseX, mouseY)) {
           this.selected = true;
           return this;
       }
       this.selected = false;
       return null;
    }    
    
    @Override
    public List<IDrawable> checkSelection(int mouseX, int mouseY, int width, int height, boolean multiSelect) {
        return null;
    }
    
    private boolean checkCruedSelection(int mouseX, int mouseY) {
         
        int minX = (startPoint.getX() < endPoint.getX()) ? startPoint.getX() : endPoint.getX();
        int maxX = (startPoint.getX() > endPoint.getX()) ? startPoint.getX() : endPoint.getX();
        
        int minY = (startPoint.getY() < endPoint.getY()) ? startPoint.getY() : endPoint.getY();
        int maxY = (startPoint.getY() > endPoint.getY()) ? startPoint.getY() : endPoint.getY();
              
        
        if ((minX + 2 < mouseX && mouseX < maxX - 2) && 
                (minY + 2 < mouseY && mouseY < maxY - 2)) {            
            return true;            
        }        
        return false;
    }
    
    private boolean checkPreciseSelection(int mouseX, int mouseY) {
        return true;
        // TODO OOOOOOOOOOOOOOOOOOOO
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
