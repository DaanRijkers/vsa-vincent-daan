/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editor.gui;

import editor.domain.Line;
import editor.domain.Mode;
import editor.domain.Polygon;
import editor.service.FileHandler;
import editor.service.MessageService;
import editor.service.Options;
import editor.service.TriangulateService;
import editor.service.TriangulationService;
import java.awt.KeyboardFocusManager;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JToggleButton;

/**
 *
 * @author Daan
 */
public class EditorForm extends javax.swing.JFrame {

    private TriangulationService te;

    /**
     * Creates new form EditorForm
     */
    public EditorForm() {
        initComponents();
        this.te = new TriangulationService();
        panelGrid.setTriangulationEditor(te);
        setMode(btnPoint, Mode.POINT);

//        this.addFocusListener(new FocusListener() {
//
//            @Override
//            public void focusGained(FocusEvent e) {
//                panelGrid.requestFocusInWindow();
//                panelGrid.requestFocus();
//                System.out.println(e.getComponent() + " - " + e.getOppositeComponent());
//            }
//
//            @Override
//            public void focusLost(FocusEvent e) {
//                
//                panelGrid.requestFocusInWindow();
//                panelGrid.requestFocus();
//            }
//        });
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                panelGrid.disableMouseLine();
                panelGrid.requestFocusInWindow();
            }
        });

//        manager.addKeyEventDispatcher(new KeyEventDispatcher() {
//
//            @Override
//            public boolean dispatchKeyEvent(KeyEvent e) {
//                panelGrid.requestFocusInWindow();
//                return false;
//            }
//        });
    }

    private void setMode(JToggleButton btn, Mode mode) {
        btnPoint.setSelected((btnPoint == btn));
        btnLine.setSelected((btnLine == btn));
        btnKnot.setSelected((btnKnot == btn));
        panelGrid.setMode(mode);
        
        rdbtnInnerBorder.setVisible((btnLine == btn));
        rdbtnOuterBorder.setVisible((btnLine == btn));
        rdbtnInnerLine.setVisible((btnLine == btn));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grpRadioButtons = new javax.swing.ButtonGroup();
        btnPoint = new javax.swing.JToggleButton();
        btnLine = new javax.swing.JToggleButton();
        panelGrid = new editor.gui.DrawingPanel();
        lblCoordinates = new javax.swing.JLabel();
        btnKnot = new javax.swing.JToggleButton();
        rdbtnOuterBorder = new javax.swing.JRadioButton();
        rdbtnInnerBorder = new javax.swing.JRadioButton();
        rdbtnInnerLine = new javax.swing.JRadioButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        menuFileNew = new javax.swing.JMenuItem();
        menuFileOpen = new javax.swing.JMenuItem();
        menuFileSave = new javax.swing.JMenuItem();
        menuFileSaveAs = new javax.swing.JMenuItem();
        menuEdit = new javax.swing.JMenu();
        menuEditSelect = new javax.swing.JMenuItem();
        menuEditDeselect = new javax.swing.JMenuItem();
        menuEditDelete = new javax.swing.JMenuItem();
        menuView = new javax.swing.JMenu();
        menuViewGrid = new javax.swing.JCheckBoxMenuItem();
        menuViewScale = new javax.swing.JCheckBoxMenuItem();
        menuZoom = new javax.swing.JMenu();
        menuZoomIn = new javax.swing.JMenuItem();
        menuZoomOut = new javax.swing.JMenuItem();
        menuTools = new javax.swing.JMenu();
        menuToolsTriangulate = new javax.swing.JMenuItem();
        menuToolsStick = new javax.swing.JCheckBoxMenuItem();
        menuToolsOptions = new javax.swing.JMenuItem();
        menuToolsAbout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Triangulation Editor");

        btnPoint.setText("Point");
        btnPoint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPointActionPerformed(evt);
            }
        });

        btnLine.setText("Line");
        btnLine.setMaximumSize(new java.awt.Dimension(71, 23));
        btnLine.setMinimumSize(new java.awt.Dimension(71, 23));
        btnLine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLineActionPerformed(evt);
            }
        });

        panelGrid.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                panelGridMouseMoved(evt);
            }
        });

        lblCoordinates.setText("x:- y:-");

        btnKnot.setText("Knot");
        btnKnot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKnotActionPerformed(evt);
            }
        });

        grpRadioButtons.add(rdbtnOuterBorder);
        rdbtnOuterBorder.setSelected(true);
        rdbtnOuterBorder.setText("Outer border");
        rdbtnOuterBorder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbtnOuterBorderActionPerformed(evt);
            }
        });

        grpRadioButtons.add(rdbtnInnerBorder);
        rdbtnInnerBorder.setText("Inner border");
        rdbtnInnerBorder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbtnInnerBorderActionPerformed(evt);
            }
        });

        grpRadioButtons.add(rdbtnInnerLine);
        rdbtnInnerLine.setText("Inner line");
        rdbtnInnerLine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbtnInnerLineActionPerformed(evt);
            }
        });

        menuFile.setText("File");

        menuFileNew.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        menuFileNew.setText("New file..");
        menuFileNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuFileNewActionPerformed(evt);
            }
        });
        menuFile.add(menuFileNew);

        menuFileOpen.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        menuFileOpen.setText("Open file..");
        menuFileOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuFileOpenActionPerformed(evt);
            }
        });
        menuFile.add(menuFileOpen);

        menuFileSave.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        menuFileSave.setText("Save");
        menuFileSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuFileSaveActionPerformed(evt);
            }
        });
        menuFile.add(menuFileSave);

        menuFileSaveAs.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        menuFileSaveAs.setText("Save as..");
        menuFileSaveAs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuFileSaveAsActionPerformed(evt);
            }
        });
        menuFile.add(menuFileSaveAs);

        jMenuBar1.add(menuFile);

        menuEdit.setText("Edit");

        menuEditSelect.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        menuEditSelect.setText("Select all");
        menuEditSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuEditSelectActionPerformed(evt);
            }
        });
        menuEdit.add(menuEditSelect);

        menuEditDeselect.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        menuEditDeselect.setText("Deselect all");
        menuEditDeselect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuEditDeselectActionPerformed(evt);
            }
        });
        menuEdit.add(menuEditDeselect);

        menuEditDelete.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        menuEditDelete.setText("Delete all");
        menuEditDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuEditDeleteActionPerformed(evt);
            }
        });
        menuEdit.add(menuEditDelete);

        jMenuBar1.add(menuEdit);

        menuView.setText("View");

        menuViewGrid.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.CTRL_MASK));
        menuViewGrid.setSelected(true);
        menuViewGrid.setText("Show grid");
        menuViewGrid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuViewGridActionPerformed(evt);
            }
        });
        menuView.add(menuViewGrid);

        menuViewScale.setSelected(true);
        menuViewScale.setText("Show scale");
        menuViewScale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuViewScaleActionPerformed(evt);
            }
        });
        menuView.add(menuViewScale);

        menuZoom.setText("Zoom");

        menuZoomIn.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ADD, 0));
        menuZoomIn.setText("Zoom in");
        menuZoomIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuZoomInActionPerformed(evt);
            }
        });
        menuZoom.add(menuZoomIn);

        menuZoomOut.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_SUBTRACT, 0));
        menuZoomOut.setText("Zoom out");
        menuZoomOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuZoomOutActionPerformed(evt);
            }
        });
        menuZoom.add(menuZoomOut);

        menuView.add(menuZoom);

        jMenuBar1.add(menuView);

        menuTools.setText("Tools");

        menuToolsTriangulate.setText("Auto-triangulate");
        menuToolsTriangulate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuToolsTriangulateActionPerformed(evt);
            }
        });
        menuTools.add(menuToolsTriangulate);

        menuToolsStick.setText("Stick to grid");
        menuToolsStick.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuToolsStickActionPerformed(evt);
            }
        });
        menuTools.add(menuToolsStick);

        menuToolsOptions.setText("Options");
        menuToolsOptions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuToolsOptionsActionPerformed(evt);
            }
        });
        menuTools.add(menuToolsOptions);

        menuToolsAbout.setText("About");
        menuToolsAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuToolsAboutActionPerformed(evt);
            }
        });
        menuTools.add(menuToolsAbout);

        jMenuBar1.add(menuTools);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnPoint, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLine, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnKnot, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(rdbtnOuterBorder)
                .addGap(18, 18, 18)
                .addComponent(rdbtnInnerBorder)
                .addGap(18, 18, 18)
                .addComponent(rdbtnInnerLine)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblCoordinates)
                .addContainerGap())
            .addComponent(panelGrid, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPoint)
                    .addComponent(btnLine, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCoordinates)
                    .addComponent(btnKnot)
                    .addComponent(rdbtnOuterBorder)
                    .addComponent(rdbtnInnerBorder)
                    .addComponent(rdbtnInnerLine))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelGrid, javax.swing.GroupLayout.DEFAULT_SIZE, 508, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuFileNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuFileNewActionPerformed
        te.setPolygon(new Polygon());
        FileHandler.clearOpenedFile();
        //te.clearFile();
        panelGrid.refresh();
    }//GEN-LAST:event_menuFileNewActionPerformed

    private void menuFileOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuFileOpenActionPerformed
        te.setPolygon(FileHandler.readFromFile());
        //te.loadFile();
        panelGrid.refresh();
    }//GEN-LAST:event_menuFileOpenActionPerformed

    private void menuFileSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuFileSaveActionPerformed
        FileHandler.writeToFile(te.getPolygon(), false);
        //te.saveFile(false);
    }//GEN-LAST:event_menuFileSaveActionPerformed

    private void menuFileSaveAsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuFileSaveAsActionPerformed
        FileHandler.writeToFile(te.getPolygon(), true);
        //te.saveFile(true);
    }//GEN-LAST:event_menuFileSaveAsActionPerformed

    private void menuEditSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuEditSelectActionPerformed
        te.getPolygon().selectAll();
        panelGrid.refresh();
    }//GEN-LAST:event_menuEditSelectActionPerformed

    private void menuEditDeselectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuEditDeselectActionPerformed
        te.getPolygon().clearSelection();
        panelGrid.refresh();
    }//GEN-LAST:event_menuEditDeselectActionPerformed

    private void menuEditDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuEditDeleteActionPerformed
        te.setPolygon(new Polygon());
        panelGrid.refresh();
    }//GEN-LAST:event_menuEditDeleteActionPerformed

    private void menuViewGridActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuViewGridActionPerformed
        panelGrid.switchShowGrid();
    }//GEN-LAST:event_menuViewGridActionPerformed

    private void menuViewScaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuViewScaleActionPerformed
        Options.setShowScale(!Options.isShowScale());  
        panelGrid.refresh();
    }//GEN-LAST:event_menuViewScaleActionPerformed

    private void menuZoomInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuZoomInActionPerformed
        panelGrid.zoomIn();
    }//GEN-LAST:event_menuZoomInActionPerformed

    private void menuZoomOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuZoomOutActionPerformed
        panelGrid.zoomOut();
    }//GEN-LAST:event_menuZoomOutActionPerformed

    private void menuToolsStickActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuToolsStickActionPerformed
        panelGrid.setStickGrid(((JCheckBoxMenuItem) evt.getSource()).getState());
    }//GEN-LAST:event_menuToolsStickActionPerformed

    private void btnPointActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPointActionPerformed

        setMode((JToggleButton) evt.getSource(), Mode.POINT);
    }//GEN-LAST:event_btnPointActionPerformed

    private void btnLineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLineActionPerformed
//        Mode mode = Mode.LINE_BORDER_INNER;
//        if (rdbtnInnerBorder.isSelected())
//            mode = Mode.LINE_BORDER_INNER;
//        else if (rdbtnOuterBorder.isSelected()) 
//            mode = Mode.LINE_BORDER_OUTER;
//        else 
//            mode = Mode.LINE_INNER;
        setMode((JToggleButton) evt.getSource(), Mode.LINE);
    }//GEN-LAST:event_btnLineActionPerformed

    private void panelGridMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelGridMouseMoved
        lblCoordinates.setText("x:" + (int) (evt.getX() / panelGrid.getScale()) + 
                " y:" + (int) (evt.getY() / panelGrid.getScale()));
    }//GEN-LAST:event_panelGridMouseMoved

    private void btnKnotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKnotActionPerformed
        setMode((JToggleButton) evt.getSource(), Mode.KNOT);
    }//GEN-LAST:event_btnKnotActionPerformed

    private void rdbtnOuterBorderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbtnOuterBorderActionPerformed
        //setMode(btnLine, Mode.LINE_BORDER_OUTER);
        panelGrid.setLineMode(Line.BORDER_OUTER_SEGMENT);
    }//GEN-LAST:event_rdbtnOuterBorderActionPerformed

    private void rdbtnInnerBorderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbtnInnerBorderActionPerformed
        panelGrid.setLineMode(Line.BORDER_INNER_SEGMENT);
    }//GEN-LAST:event_rdbtnInnerBorderActionPerformed

    private void rdbtnInnerLineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbtnInnerLineActionPerformed
        panelGrid.setLineMode(Line.INNER_SEGMENT);
    }//GEN-LAST:event_rdbtnInnerLineActionPerformed

    private void menuToolsOptionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuToolsOptionsActionPerformed
        OptionsForm options = new OptionsForm();
        options.setVisible(true);
    }//GEN-LAST:event_menuToolsOptionsActionPerformed

    private void menuToolsAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuToolsAboutActionPerformed
        MessageService.showAboutMessage();
    }//GEN-LAST:event_menuToolsAboutActionPerformed

    private void menuToolsTriangulateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuToolsTriangulateActionPerformed
        TriangulateService.autoTriangulatePolygon(te.getPolygon());
        panelGrid.refresh();
    }//GEN-LAST:event_menuToolsTriangulateActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EditorForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditorForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditorForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditorForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EditorForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton btnKnot;
    private javax.swing.JToggleButton btnLine;
    private javax.swing.JToggleButton btnPoint;
    private javax.swing.ButtonGroup grpRadioButtons;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JLabel lblCoordinates;
    private javax.swing.JMenu menuEdit;
    private javax.swing.JMenuItem menuEditDelete;
    private javax.swing.JMenuItem menuEditDeselect;
    private javax.swing.JMenuItem menuEditSelect;
    private javax.swing.JMenu menuFile;
    private javax.swing.JMenuItem menuFileNew;
    private javax.swing.JMenuItem menuFileOpen;
    private javax.swing.JMenuItem menuFileSave;
    private javax.swing.JMenuItem menuFileSaveAs;
    private javax.swing.JMenu menuTools;
    private javax.swing.JMenuItem menuToolsAbout;
    private javax.swing.JMenuItem menuToolsOptions;
    private javax.swing.JCheckBoxMenuItem menuToolsStick;
    private javax.swing.JMenuItem menuToolsTriangulate;
    private javax.swing.JMenu menuView;
    private javax.swing.JCheckBoxMenuItem menuViewGrid;
    private javax.swing.JCheckBoxMenuItem menuViewScale;
    private javax.swing.JMenu menuZoom;
    private javax.swing.JMenuItem menuZoomIn;
    private javax.swing.JMenuItem menuZoomOut;
    private editor.gui.DrawingPanel panelGrid;
    private javax.swing.JRadioButton rdbtnInnerBorder;
    private javax.swing.JRadioButton rdbtnInnerLine;
    private javax.swing.JRadioButton rdbtnOuterBorder;
    // End of variables declaration//GEN-END:variables
}
