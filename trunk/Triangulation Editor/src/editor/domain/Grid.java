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

        for (int i = (int)(spacing * scale); i < screenWidth; i += (spacing * scale)) {            
            g.drawLine(i, 0, i, screenHeight);
        }
        for (int i = (int)(spacing * scale); i < screenHeight; i += (spacing * scale)) {
            g.drawLine(0, i, screenWidth, i);
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

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

}
