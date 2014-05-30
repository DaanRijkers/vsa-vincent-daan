/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editor.gui;

import editor.service.TriangulationService;
import editor.domain.Grid;
import editor.domain.IDrawable;
import editor.domain.Line;
import editor.domain.Mode;
import editor.domain.Point;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Ellipse2D;
import javax.swing.JOptionPane;

/**
 *
 * @author Daan
 */
public class DrawingPanel extends javax.swing.JPanel {

    // Parameters for drawing
    private boolean showGrid;
    private boolean stickGrid;
    private boolean fillShapes;
    private boolean multiSelect;
    private Mode mode;

    // Parameters for keeping scale
    private double scale;

    private TriangulationService te;

    private Grid grid;
    private Line mouseLine;

    /**
     * Creates new form DrawingPanel
     */
    public DrawingPanel() {
        initComponents();
        initValues();
    }

    public void initValues() {
        this.showGrid = false;
        this.stickGrid = false;
        this.fillShapes = false;
        this.multiSelect = false;
        this.mode = Mode.DRAW;

        this.scale = 1;

        this.te = null;
        this.mouseLine = new Line(null);

        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                createMouseLine(e);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                createMouseLine(e);
            }
        });

        this.addComponentListener(new ComponentAdapter() {

            @Override
            public void componentResized(ComponentEvent e) {
                grid = new Grid(25, e.getComponent().getWidth(), e.getComponent().getHeight());
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paintComponents(g);

        // Draw gridlines
        if (showGrid) {
            if (grid == null) {
                grid = new Grid(25, this.getWidth(), this.getHeight());
            }
            grid.draw((Graphics2D) g, scale);
        }

        // Draw upper borderline
        g.setColor(Color.BLACK);
        g.drawLine(0, 0, this.getWidth(), 0);

        // Draw line following mouse
        if (mode == Mode.DRAW && mouseLine.getStartPoint() != null && mouseLine.getEndPoint() != null) {
            mouseLine.draw((Graphics2D) g, scale);
        }

        // Draw polygon
        if (te != null) {
            te.getPolygon().draw((Graphics2D) g, scale);
        }
    }

    public void refresh() {
        this.getGraphics().clearRect(0, 0, this.getWidth(), this.getHeight());
        this.update(this.getGraphics());
    }

    private void createMouseLine(MouseEvent e) {
//        if (mouseLine.getStartPoint() == null) {
//            mouseLine = new Line(te.getPolygon().getMouseLineStartPoint());
//        }
//        mouseLine.setEndPoint(new Point(e.getX(), e.getY()));
//        
//        //this.getGraphics().clearRect(0, 0, this.getWidth(), this.getHeight());
//        //this.update(getGraphics());        
//        this.repaint();
    }
private int roundToSpacing(int coordinate){
        double modulo = (coordinate % (grid.getSpacing() * scale));
        System.out.println("-----");
        System.out.println("modulo: " + modulo);
        int base = (int)((coordinate - modulo)/ (grid.getSpacing() * scale));
        System.out.println("base: " + base);
        if(modulo > (grid.getSpacing() * scale / 2)) {
            base++;
        }
        System.out.println("final: " + (int)(base * grid.getSpacing() * scale));
        return (int)(base * grid.getSpacing() * scale);
    }
    
    private void mouseClickDrawMode(java.awt.event.MouseEvent evt) {
        int x = evt.getX();
        int y = evt.getY();
        if(stickGrid){
            x = roundToSpacing(x);
            y = roundToSpacing(y);
        }
        System.out.println((int)0.9);
        IDrawable connectPoint = te.getPolygon().checkSelection(x, y, false);

        if (connectPoint == null || !(connectPoint instanceof Point)) {
            te.getPolygon().addPoint(x, y);
        } else {
            int reply = JOptionPane.showConfirmDialog(this,
                    "Do you want to connect the segment to this point?",
                    "Connect segments",
                    JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                te.getPolygon().connectSegment((Point) connectPoint);
            }
        }
        te.getPolygon().clearSelection();
        //mouseLine = new Line(new Point(evt.getX(), evt.getY()));
        this.update(this.getGraphics());
    }

    private void mouseClickSelectMode(java.awt.event.MouseEvent evt) {
        te.getPolygon().checkSelection(evt.getX(), evt.getY(), multiSelect);
        this.update(this.getGraphics());
    }

    private void mouseClickLineMode(java.awt.event.MouseEvent evt) {

    }

    // <editor-fold desc="All getter & setter methods are placed within">
    public void switchShowGrid() {
        this.showGrid = !this.showGrid;
        refresh();
    }

    public void switchStickGrid() {
        this.stickGrid = !this.stickGrid;
    }

    public void switchFillShapes() {
        this.fillShapes = !this.fillShapes;
        refresh();
    }

    public double getScale() {
        return scale;
    }

    public void setShowGrid(boolean showGrid) {
        this.showGrid = showGrid;
    }

    public void setStickGrid(boolean stickGrid) {
        this.stickGrid = stickGrid;
    }

    public void setFillShapes(boolean fillShapes) {
        this.fillShapes = fillShapes;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
        te.getPolygon().checkSelection(0, 0, false);
        this.update(this.getGraphics());
    }

    void zoomIn() {
        if (this.scale < 8) {
            this.scale *= 2;
            refresh();
        }
    }

    public void zoomOut() {
        if (this.scale > 0.25) {
            this.scale /= 2;
            refresh();
        }
    }

    public void setTriangulationEditor(TriangulationService te) {
        this.te = te;
    }

    public void setSelectMode(boolean selectMode) {
        this.multiSelect = selectMode;
    }

    // </editor-fold>
    //
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                formKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 746, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 496, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        if (mode == Mode.DRAW) {
            mouseClickDrawMode(evt);

        } else if (mode == Mode.SELECT) {
            mouseClickSelectMode(evt);

        } else if (mode == Mode.LINE) {
            mouseClickLineMode(evt);
        }
    }//GEN-LAST:event_formMousePressed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        // Check if control is being held down
        multiSelect = evt.isControlDown();

        // Check if pressed key is 'delete'
        if (evt.getKeyCode() == 127 && mode == Mode.SELECT) {
            te.getPolygon().deleteSelectedItems();
            refresh();
            //this.repaint();
        }
    }//GEN-LAST:event_formKeyPressed

    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
        // Check if control is being held down
        multiSelect = evt.isControlDown();
    }//GEN-LAST:event_formKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
