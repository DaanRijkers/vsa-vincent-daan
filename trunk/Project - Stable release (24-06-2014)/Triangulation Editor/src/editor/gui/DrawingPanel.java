/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editor.gui;

import editor.domain.Grid;
import editor.domain.IDrawable;
import editor.domain.Knot;
import editor.domain.Line;
import editor.domain.Mode;
import editor.domain.Point;
import editor.domain.Triangle;
import editor.service.KnotService;
import editor.service.Options;
import editor.service.PolygonService;
import java.awt.AWTException;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Robot;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageProducer;
import java.awt.image.Raster;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import sun.dc.pr.Rasterizer;

/**
 *
 * @author Daan
 */
public class DrawingPanel extends javax.swing.JPanel {

    // Parameters for drawing
    private boolean lineVis;
    private boolean showGrid;
    private boolean stickGrid;
    private boolean fillShapes;
    private boolean multiSelect;
    private Mode mode;
    private int lineMode;

    // Parameters for keeping scale
    private double scale;
    private PolygonService te;
    private Grid grid;
    private Line mouseLine;
    // Parameters for dragging
    private int prevX;
    private int prevY;
    private boolean dragVis;
    private List<IDrawable> selectedObjects;
    private Point startLinePoint;
    
    private BufferedImage screenShot;
    
    //private Triangle selectedTriangle;

    /**
     * Creates new form DrawingPanel
     */
    public DrawingPanel() {
        initComponents();
        initValues();
    }

    ////////////////////////////////////////////
    ////////////// TEST METHOD /////////////////
    ////////////////////////////////////////////    
    private Triangle t1 = null;

    private void test(Graphics2D g) {
        Point p1 = new Point(20, 20);
        Point p2 = new Point(60, 100);
        Point p3 = new Point(100, 20);

        Line l1 = new Line(p1, p2, Line.BORDER_OUTER_SEGMENT);
        Line l2 = new Line(p2, p3, Line.BORDER_OUTER_SEGMENT);
        Line l3 = new Line(p3, p1, Line.BORDER_OUTER_SEGMENT);

        List<Line> lines = new ArrayList<>();
        lines.add(l1);
        lines.add(l2);
        lines.add(l3);

        t1 = new Triangle(lines, Color.MAGENTA, 10);
        t1.draw(g, scale);
        
//        Knot k1 = new Knot(200, 200);
//        Knot k2 = new Knot(400, 100);
//        Point p4 = new Point(350, 300); 
//        p4.getKnots().add(k1);
//        p4.getKnots().add(k2);
//        
//        p4.draw(g, scale);
    }
    ////////////////////////////////////////////
    //////////////// END TEST //////////////////
    ////////////////////////////////////////////   

    public void initValues() {
        this.lineVis = false;
        this.showGrid = true;
        this.stickGrid = false;
        this.fillShapes = false;
        this.multiSelect = false;
        this.mode = Mode.POINT;
        this.lineMode = Line.BORDER_OUTER_SEGMENT;

        this.scale = 1;

        this.te = null;
        this.mouseLine = new Line(null);

        this.prevX = -1;
        this.prevY = -1;
        this.dragVis = false;
        this.selectedObjects = new ArrayList<>();
        this.startLinePoint = null;
        //this.selectedTriangle = null;

        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                createMouseLine(e);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                moveSelection(e);
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
        //super.paintComponents(g);
        g.setPaintMode();

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

        // Draw polygon
        if (te != null && te.getPolygon() != null) {
            te.getPolygon().draw((Graphics2D) g, scale);
        }

        //test((Graphics2D) g);
    }

    public void refresh() {
        this.screenShot = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        
        this.getGraphics().clearRect(0, 0, this.getWidth(), this.getHeight());
        this.update(this.getGraphics());
        
        screenShot.getGraphics().clearRect(0, 0, this.getWidth(), this.getHeight());
        this.paint(screenShot.getGraphics());
    }

    private void renderDraggedSelection() {
        //if (mode == Mode.SELECT && !selectedObjects.isEmpty()) {
        if (!selectedObjects.isEmpty()) {
            Graphics2D g2d = (Graphics2D) getGraphics();
            g2d.setXORMode(Color.GRAY);
            te.getPolygon().draw(g2d, scale);
        }
        dragVis = !dragVis;
    }

    private void renderLine() {
        if (mode == Mode.LINE && mouseLine != null && mouseLine.getStartPoint() != null && mouseLine.getEndPoint() != null) {
            Graphics2D g2d = (Graphics2D) getGraphics();
            g2d.setXORMode(Color.GRAY);
            mouseLine.draw(g2d, scale);
        }
        lineVis = !lineVis;
    }

    private void createMouseLine(MouseEvent e) {
        if (lineVis) {
            renderLine();
        }
        //if (mouseLine.getStartPoint() == null && !te.getPolygon().getPoints().isEmpty()) {
        //mouseLine = new Line(null);
        //}
        if (mouseLine != null) {
            mouseLine.setEndPoint(new Point(e.getX(), e.getY()));
            renderLine();
        }
    }

    private void moveSelection(MouseEvent e) {
        if (dragVis) {
            renderDraggedSelection();
        }
        if (prevX != -1 && prevY != -1) {
            te.getPolygon().moveSelection(e.getX() - prevX, e.getY() - prevY);
        }
        prevX = e.getX();
        prevY = e.getY();

        renderDraggedSelection();
    }

    private int roundToSpacing(int coordinate) {
        double modulo = (coordinate % (grid.getSpacing() * scale));
        int base = (int) ((coordinate - modulo) / (grid.getSpacing() * scale));

        if (modulo > (grid.getSpacing() * scale / 2)) {
            base++;
        }

        return (int) (base * grid.getSpacing() * scale);
    }

    private void mouseClickPointMode(java.awt.event.MouseEvent evt) {
        int x = evt.getX();
        int y = evt.getY();
        if (stickGrid) {
            x = roundToSpacing(x);
            y = roundToSpacing(y);
        }

        IDrawable connectPoint = te.getPolygon().checkSelection(x, y, multiSelect);
        // TODO TIDY UP:   
        //System.out.println((connectPoint == null));
        if (connectPoint == null || !(connectPoint instanceof Point)) {
            te.getPolygon().addPoint(x, y);

            if (connectPoint instanceof Line) {
                ((Line) connectPoint).setSelected(false);
            }

//        } else if (!te.getPolygon().canConnectToPoint((Point)connectPoint)) {
//            JOptionPane.showMessageDialog(this, "These two points can not be connected");
//        } else {
//            // TODO: ASK USER WETHER HE WANTS TO CONNECT OR HIGHLIGHT (CONTINUE FROM) THIS POINT
//            int reply = JOptionPane.showConfirmDialog(this,
//                    "Do you want to connect the segment to this point?",
//                    "Connect segments",
//                    JOptionPane.YES_NO_OPTION);
//            if (reply == JOptionPane.YES_OPTION) {
//                te.getPolygon().connectSegment((Point) connectPoint);
//            }
//        }
            te.getPolygon().clearSelection();
        }
        this.update(this.getGraphics());
    }

    private void mouseClickLineMode(java.awt.event.MouseEvent evt) {
        IDrawable selectedObject = te.getPolygon().checkSelection(evt.getX(), evt.getY(), false);

        if (selectedObject != null && selectedObject instanceof Point) {
            if (startLinePoint == null) {
                this.startLinePoint = (Point) selectedObject;
                this.startLinePoint.setSelected(false);
                this.startLinePoint.setHighlighted(true);
                this.mouseLine = new Line(startLinePoint);
            } else if (this.startLinePoint != (Point) selectedObject) {
                te.getPolygon().addLine(new Line(startLinePoint, (Point) selectedObject, lineMode), true);
                this.startLinePoint.setHighlighted(false);
                this.startLinePoint = null;
                this.mouseLine = null;
                System.out.println("line added");
            } else {
                this.startLinePoint = null;
                this.mouseLine = null;
                ((Point) selectedObject).setHighlighted(false);
            }
        }

        if (!(selectedObject instanceof Line)) {
            te.getPolygon().clearSelection();
        }
        this.update(this.getGraphics());
    }

    private void mouseClickKnotMode(java.awt.event.MouseEvent evt) {
        //IDrawable d = te.getPolygon().checkSelection(evt.getX(), evt.getY(), multiSelect);

        IDrawable selectedObject = te.getPolygon().checkSelection(evt.getX(), evt.getY(), false);
        
        if (selectedObject instanceof Point) {
            if (selectedObjects.size() > 0) {
                selectedObjects.clear();
                te.getPolygon().setDrawKnotcheck(null);
            } else {
                selectedObjects.add(selectedObject);
                te.getPolygon().setDrawKnotcheck((Point)selectedObject);
            }
            this.refresh();
        } else if (selectedObject instanceof Knot){
            selectedObjects.clear();
            selectedObjects.add(selectedObject);
            te.getPolygon().setDrawKnotcheck(((Knot)selectedObject).getPoint());
            this.update(this.getGraphics());
        } else if (selectedObject == null) {
            if (selectedObjects.size() == 1 && Options.isShowKnotPlacementCheck()){
                Color rColor = new Color(getGraphicsConfiguration().getColorModel().getRGB(screenShot.getRGB(evt.getX(), evt.getY())));
                System.out.println("Pixelcolor: "+rColor);
                if(rColor.equals(KnotService.specialGreen))
                    te.getPolygon().addKnot(evt.getX(), evt.getY(), selectedObjects.get(0));
                selectedObjects.clear();
            } else if(selectedObjects.size() == 1){
                te.getPolygon().addKnot(evt.getX(), evt.getY(), selectedObjects.get(0));
            } else {
                selectedObjects.clear();
            }
            this.update(this.getGraphics());
        }
    }

    private void mouseClickSelectMode(java.awt.event.MouseEvent evt) {
        te.getPolygon().checkSelection(evt.getX(), evt.getY(), multiSelect);
        this.update(this.getGraphics());
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
        te.getPolygon().setDrawKnotcheck(null);
        te.getPolygon().checkSelection(0, 0, false);
        //selectedTriangle = null;
        this.refresh();
    }

    public void setLineMode(int mode) {
        this.lineMode = mode;
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

    public void setTriangulationEditor(PolygonService te) {
        this.te = te;
    }

    public void setSelectMode(boolean selectMode) {
        this.multiSelect = selectMode;
    }

    public void disableMouseLine() {
        //this.refresh();
        this.mouseLine = null;
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
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
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
        this.prevX = -1;
        this.prevY = -1;

        if (te.getPolygon() != null) {
            this.selectedObjects = te.getPolygon().getSelectedObjects();
        }
    }//GEN-LAST:event_formMousePressed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        // Check if control is being held down
        multiSelect = evt.isControlDown();

        // Check if pressed key is 'delete'
        if (evt.getKeyCode() == 127) { 
            te.getPolygon().deleteSelectedItems();
            refresh();
            //this.repaint();
        }
    }//GEN-LAST:event_formKeyPressed

    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
        // Check if control is being held down
        multiSelect = evt.isControlDown();
    }//GEN-LAST:event_formKeyReleased

    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
        if (prevX == -1 && prevY == -1) {
//            if (mode == Mode.POINT) {
//                mouseClickPointMode(evt);
//
//            } else if (mode == Mode.SELECT) {
//                mouseClickSelectMode(evt);
//
//            } else if (mode == Mode.LINE) {
//                mouseClickLineMode(evt);
//            }

            switch (mode) {
                case POINT:
                    mouseClickPointMode(evt);
                    break;
                case LINE:
                    mouseClickLineMode(evt);
                    break;
                case KNOT:
                    mouseClickKnotMode(evt);
                    break;
                case SELECT:
                    mouseClickLineMode(evt);
                    break;

            }
        } else {
            this.prevX = -1;
            this.prevY = -1;
            if (!this.selectedObjects.isEmpty()) {
                this.refresh();
            }

            this.selectedObjects = new ArrayList<>();
        }
    }//GEN-LAST:event_formMouseReleased
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
