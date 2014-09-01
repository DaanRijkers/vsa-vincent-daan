/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editor.service;

import javax.swing.JOptionPane;

/**
 *
 * @author Daan
 */
public class MessageService {

    public static void showDoubleLineMessage() {

        JOptionPane.showMessageDialog(null, "Line could not be added. There already exists a line between these points.");
    }

    public static void showLineToSelfMessage() {

        JOptionPane.showMessageDialog(null, "Line could not be added. Start-/Endpoint are thesame.");
    }

    public static void showToManyLinesMessage() {

        JOptionPane.showMessageDialog(null, "Line could not be added. The maximum number of border lines to a single point is two.");
    }

    public static void showWrongFileMessage() {

        JOptionPane.showMessageDialog(null, "File does not contain polygon.");
    }

    public static void showAboutMessage() {
        JOptionPane.showMessageDialog(null, "Triangulation Editor\n\nBuild by:\n-Daan Rijkers       (rijkers.daan@gmail.com)\n-Vincent Bingley (vincent.bingley@gmail.com)", "About", JOptionPane.INFORMATION_MESSAGE);
       
    }
}
