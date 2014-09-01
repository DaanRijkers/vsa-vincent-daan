package editor.service;


import editor.domain.Polygon;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Daan
 */
public class TriangulationService {
    
    private Polygon polygon;
    
    public TriangulationService() {
        this.polygon = new Polygon();
    }
    
    public void clearFile() {
        this.polygon = new Polygon();
        FileHandler.clearOpenedFile();
        TriangulateService.resetAutonumeric();
    }
    
    public Polygon loadFile() {
        TriangulateService.resetAutonumeric();
        this.polygon = FileHandler.readFromFile();
        return polygon;
    }
    
    public void saveFile(boolean saveAs) {
        FileHandler.writeToFile(polygon, saveAs);
    }

    public Polygon getPolygon() {
        return polygon;
    }

    public void setPolygon(Polygon polygon) {
        this.polygon = polygon;
    }
}
