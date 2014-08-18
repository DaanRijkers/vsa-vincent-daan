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

    /*
     * Checks if the line is a almost perfectly horizontal or vertical line,
     * if so, performs a checkSelection for either horizontal or vertical lines
     * Otherwise checks in a crued manner if a rectangle around the line is selected,
     * if so, checks in a precise manner if the line is selected
     */
    @Override
    public IDrawable checkSelection(int mouseX, int mouseY, boolean multiSelect) {

        boolean clicked = false;

        int diff = startPoint.getX() - endPoint.getX();
        if (-2 < diff && diff < 2) {
            clicked = checkVerticalLineSelection(mouseX, mouseY);
        }

        diff = startPoint.getY() - endPoint.getY();
        if (-2 < diff && diff < 2) {
            clicked = checkHorizontalLineSelection(mouseX, mouseY);
        } else if (checkCruedSelection(mouseX, mouseY) && checkPreciseSelection(mouseX, mouseY)) {
            clicked = true;
        }

        if (clicked) {
            if (multiSelect && this.selected) {
                this.selected = false;
                return null;
            } else if (!multiSelect && this.selected) {
                this.selected = false;
                return null;
            } else {
                this.selected = true;
                return this;
            }

        } else {
            if (multiSelect && this.selected) {
                this.selected = true;
                return this;
            } else {
                this.selected = false;
                return null;
            }
        }
    }

    @Override
    public List<IDrawable> checkSelection(int mouseX, int mouseY, int width, int height, boolean multiSelect) {
        return null;
    }

    private boolean checkHorizontalLineSelection(int mouseX, int mouseY) {
        int minX = (startPoint.getX() < endPoint.getX()) ? startPoint.getX() : endPoint.getX();
        int maxX = (startPoint.getX() > endPoint.getX()) ? startPoint.getX() : endPoint.getX();

        int minY = (startPoint.getY() < endPoint.getY()) ? startPoint.getY() : endPoint.getY();
        int maxY = (startPoint.getY() > endPoint.getY()) ? startPoint.getY() : endPoint.getY();

        return (minX < mouseX && mouseX < maxX)
                && ((minY - 3) < mouseY && (mouseY - 3) < maxY);
    }

    private boolean checkVerticalLineSelection(int mouseX, int mouseY) {
        int minX = (startPoint.getX() < endPoint.getX()) ? startPoint.getX() : endPoint.getX();
        int maxX = (startPoint.getX() > endPoint.getX()) ? startPoint.getX() : endPoint.getX();

        int minY = (startPoint.getY() < endPoint.getY()) ? startPoint.getY() : endPoint.getY();
        int maxY = (startPoint.getY() > endPoint.getY()) ? startPoint.getY() : endPoint.getY();

        return ((minX - 3) < mouseX && mouseX < (maxX + 3))
                && ((minY < mouseY) && (mouseY < maxY));
    }

    private boolean checkCruedSelection(int mouseX, int mouseY) {

        int minX = (startPoint.getX() < endPoint.getX()) ? startPoint.getX() : endPoint.getX();
        int maxX = (startPoint.getX() > endPoint.getX()) ? startPoint.getX() : endPoint.getX();

        int minY = (startPoint.getY() < endPoint.getY()) ? startPoint.getY() : endPoint.getY();
        int maxY = (startPoint.getY() > endPoint.getY()) ? startPoint.getY() : endPoint.getY();

        return (minX + 2 < mouseX && mouseX < maxX - 2)
                && (minY + 2 < mouseY && mouseY < maxY - 2);
    }

    private boolean checkPreciseSelection(int mouseX, int mouseY) {

        double distance = Math.sqrt(Math.pow(startPoint.getX() - endPoint.getX(), 2.0)
                + Math.pow((startPoint.getY() - endPoint.getY()), 2.0));

        double numberOfPoints = distance / 6;

        for (double i = 1; i < (distance / 6.0); i += 1) {
            Point p = new Point((int) (startPoint.getX() - ((startPoint.getX() - endPoint.getX()) / numberOfPoints * i)),
                    (int) (startPoint.getY() - ((startPoint.getY() - endPoint.getY()) / numberOfPoints * i)));

            if (p.checkSelection(mouseX, mouseY, selected) != null) {
                return true;
            }
        }

        return false;
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
