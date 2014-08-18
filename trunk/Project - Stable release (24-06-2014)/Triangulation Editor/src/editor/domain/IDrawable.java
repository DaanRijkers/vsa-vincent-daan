/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package editor.domain;

import java.awt.Graphics2D;
import java.util.List;

/**
 *
 * @author Daan
 */
public interface IDrawable {
    
    public void draw(Graphics2D g, double scale);
    
    public IDrawable checkSelection(int mouseX, int mouseY, boolean multiSelect);
    
    public List<IDrawable> checkSelection(int mouseX, int mouseY, 
                                          int width, int height, boolean multiSelect);
    
    
    
}
