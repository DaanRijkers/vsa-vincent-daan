/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editor.domain;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;

/**
 *
 * @author Daan
 */
public class Grid implements IDrawable {

    private int spacing;
    private int screenWidth;
    private int screenHeight;

    public Grid() {
        this.spacing = 0;
        this.screenWidth = 0;
        this.screenHeight = 0;
    }

    public Grid(int sp, int width, int height) {
        this.spacing = sp;
        this.screenWidth = width;
        this.screenHeight = height;
    }

    @Override
    public void draw(Graphics2D g, double scale) {
        g.setColor(Color.LIGHT_GRAY);

        int counter = 0;
        // Vertical lines
        for (double i = (spacing * scale); i < screenWidth; i += (spacing * scale)) {
            g.drawLine((int) i, 0, (int) i, screenHeight);
            
            counter++;
            if (scale > 0.5 || counter == 3) {
                g.drawString(String.valueOf(((int) (i / scale))), (int) i - (g.getFont().getSize() / 2), g.getFont().getSize());
                counter = 0;
            }
        }

        counter = 0;
        // Horizontal lines
        for (double i = (spacing * scale); i < screenHeight; i += (spacing * scale)) {
            g.drawLine(0, (int) i, screenWidth, (int) i);

            //System.out.println((int) i);
            counter++;
            if (scale > 0.5 || counter == 3) {
                g.drawString(String.valueOf((int) (i / scale)), 1, (int) i + (g.getFont().getSize() / 2) - 1);
                counter = 0;
            }
        }
    }

    @Override
    public IDrawable checkSelection(int mouseX, int mouseY, boolean multiSelect) {
        return null;
    }

    @Override
    public List<IDrawable> checkSelection(int mouseX, int mouseY, int width, int height, boolean multiSelect) {
        return null;
    }

    public void setSpacing(int spacing) {
        this.spacing = spacing;
    }

    public int getSpacing() {
        return spacing;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

}
