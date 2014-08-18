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
    
   
    
    public Polygon loadFile(String path) {
        // this.polygon = read polygon from file
        return polygon;
    }
    
    public void SaveFile(String path, String name) {
        // write this.polygon
    }

    public Polygon getPolygon() {
        return polygon;
    }

    public void setPolygon(Polygon polygon) {
        this.polygon = polygon;
    }
}
