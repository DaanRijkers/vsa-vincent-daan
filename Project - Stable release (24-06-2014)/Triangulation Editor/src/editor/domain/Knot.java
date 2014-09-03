/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editor.domain;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Daan
 */
public class Knot implements IDrawable, Serializable {

    private static final Color NORMAL_COLOR = Color.RED;
    private static final Color SELECTED_COLOR = Color.BLUE;

    private int x;
    private int y;
    private int size;
    private IDrawable previous;

    private boolean selected;

//    public Knot() {
//        this.x = 0;
//        this.y = 0;
//        this.size = 10;
//        this.selected = false;
//    }

    public Knot(int x, int y, IDrawable prev) {
        this.x = x;
        this.y = y;
        this.size = 10;
        this.selected = false;
        this.previous = prev;
    }
    
    public Point getPoint(){
        if(previous instanceof Point) {
            return (Point)previous;
        } else {
            return ((Knot)previous).getPoint();
        }
    }

    @Override
    public void draw(Graphics2D g, double scale) {
        g.setColor((selected) ? SELECTED_COLOR : NORMAL_COLOR);
        
        if(previous instanceof Point)
            g.drawLine(((Point)previous).getX(), ((Point)previous).getY(), x, y);
        else
            g.drawLine(((Knot)previous).getX(), ((Knot)previous).getY(), x, y);

        Ellipse2D.Double circle = new Ellipse2D.Double(x - 5, y - 5, size, size);
        //g.fill(circle);
        g.draw(circle);
        g.drawLine(x - 3, y - 3, x + 3, y + 3);
        g.drawLine(x + 3, y - 3, x - 3, y + 3);
        
//        g.setColor(Color.BLACK);
//        try {
//            g.drawString(this.toString(), x - 2, y + 17);
//        } catch (InternalError ex) {
//            System.out.println("Java Internal Error: \"Something not implemented yet\".");
//        }
    }

    @Override
    public IDrawable checkSelection(int mouseX, int mouseY, boolean multiSelect) {
        if (((x - 5) < mouseX && mouseX < (x + 5))
                && ((y - 5) < mouseY && mouseY < (y + 5))) {
            this.selected = !this.selected;
            return this;
        } else if (this.selected == true && multiSelect == true) {
            return this;
        } else {
            this.selected = false;
            return null;
        }
    }

    @Override
    public List<IDrawable> checkSelection(int mouseX, int mouseY, int width, int height, boolean multiSelect) {
        return null;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public IDrawable getPrevious() {
        return previous;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
