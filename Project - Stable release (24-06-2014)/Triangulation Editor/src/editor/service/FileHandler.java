/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editor.service;

import editor.domain.Polygon;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Daan
 */
public class FileHandler {

    private static File openedFile = null;

    public static void clearOpenedFile() {
        openedFile = null;
    }
    
    public static Polygon readFromFile() {
        openedFile = getOpenFile();
        
        if (openedFile == null) {
            return new Polygon();
        }
        
        ObjectInputStream ois = null;
        try {
            FileInputStream fout = new FileInputStream(openedFile.getAbsolutePath());
            ois = new ObjectInputStream(fout);

            return (Polygon) ois.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "File does not contain polygon.");
            openedFile = null;
            
        } finally {
            try {
                if (ois != null)
                    ois.close();
            } catch (IOException ex) {
                Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return null;

    }

    public static void writeToFile(Polygon p, boolean saveAs) {
        if (openedFile == null || saveAs) {
            openedFile = getSaveFile();
        }

        ObjectOutputStream oos = null;
        try {
            FileOutputStream fout = new FileOutputStream(openedFile.getAbsolutePath());//"C:\\Users\\Daan\\Desktop\\TestPolygon.pol"); // 
            oos = new ObjectOutputStream(fout);

            oos.writeObject(p);
        } catch (IOException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                oos.close();
            } catch (IOException ex) {
                Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private static File getSaveFile() {

        JFileChooser fc = new JFileChooser();

        int returnVal = fc.showSaveDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            return file;
        }
        return null;
    }
    
    private static File getOpenFile() {
        JFileChooser fc = new JFileChooser();
        int returnVal = fc.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            return file;
        }
        return null;
    }
}
