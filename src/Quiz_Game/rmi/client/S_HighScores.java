/*
 * This is the program to show high scores.
 * @author  Rasu Mayurathan
 * @version 1.0
 * @since   2021-11-22
 */
package Quiz_Game.rmi.client;

import Quiz_Game.Interface.Interface;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.Window;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.rowset.CachedRowSet;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Mayu
 */
public class S_HighScores extends javax.swing.JFrame {

    /**
     * Creates new form HighScores
     */
    Point location;//Point class represents a location in a two-dimensional (x, y) coordinate space.
    Point pressed;
    
    public S_HighScores() {
        initComponents();
        
         try {
        /*The suspect that this block of statement can throw
         *exception so we handled it by placing these statements
         *inside try and handled the exception in catch block
         */
         
        Interface b = (Interface) Naming.lookup("rmi://localhost:1099/Server"); //It calls naming lookup and url
        
            
      
        CachedRowSet details = b.marksdetails();
        ResultSetMetaData rsmd = details.getMetaData();
        int columnCount = rsmd.getColumnCount();
        Vector<Object> columnNames = new Vector<>(columnCount);
        for (int i = 0 ; i < columnCount; i++) {
            columnNames.add(rsmd.getColumnLabel(i + 1));
        }
        Vector<Object> rows = new Vector<>();
        while (details.next()) {
            Vector<Object> row = new Vector<>(columnCount);
            for (int i = 0; i < columnCount; i++) {
                row.add(details.getObject(i + 1));
            }
            rows.add(row);
        }
        DefaultTableModel tblModel = new DefaultTableModel(rows, columnNames);
        jTable1.setModel(tblModel);
        
        //This code segment is to customize the jtable
        TableColumnModel columnModel = jTable1.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(130);
        columnModel.getColumn(1).setPreferredWidth(130);
        columnModel.getColumn(2).setPreferredWidth(130);
            
        jTable1.setBackground(new Color(0,0,0,79));
        jTable1.setForeground(Color.black);
        jTable1.setRowHeight(30);
            
        JTableHeader tableHeader = jTable1.getTableHeader();
        tableHeader.setBackground(Color.white);
        tableHeader.setForeground(Color.BLACK);
        Font headerFont = new Font("Verdana", Font.PLAIN, 20);
        tableHeader.setFont(headerFont);
        
        jTable1.setOpaque(false);/*from  w w  w . ja  va 2s  .  c  o  m*/
        ((DefaultTableCellRenderer) jTable1.getDefaultRenderer(Object.class))
               .setOpaque(false);
        ((DefaultTableCellRenderer) jTable1.getDefaultRenderer(String.class))
                .setOpaque(false);
        ((JComponent) jTable1.getDefaultRenderer(Boolean.class))
                .setOpaque(false);
        
        
        tableHeader.setOpaque(false);
        jScrollPane1.setOpaque(false);
        jScrollPane1.getViewport().setOpaque(false);
        
        jTable1.getTableHeader().setOpaque(false);
        jScrollPane1.getColumnHeader().setOpaque(false);
        
        jTable1.setShowGrid(true);
        jTable1.setGridColor(Color.white);
       
       /*
        * This block will only execute if any  exception occurs in try block
        */
        }
        catch (NotBoundException | RemoteException | SQLException e) {
             JOptionPane.showMessageDialog(null, e);
        } catch (MalformedURLException ex) {
            Logger.getLogger(S_HighScores.class.getName()).log(Level.SEVERE, null, ex);
        } 
 
        
        
        
        
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        navigation_bar = new javax.swing.JPanel();
        close = new javax.swing.JLabel();
        minimize = new javax.swing.JLabel();
        back = new javax.swing.JLabel();
        HighScorelbl = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        TopScoreBg = new javax.swing.JLabel();
        Background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        navigation_bar.setBackground(new java.awt.Color(0, 0, 0));
        navigation_bar.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(255, 255, 255)));
        navigation_bar.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                navigation_barMouseDragged(evt);
            }
        });
        navigation_bar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                navigation_barMousePressed(evt);
            }
        });

        close.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/close-white-28.png"))); // NOI18N
        close.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeMouseClicked(evt);
            }
        });

        minimize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/minimize-white-28.png"))); // NOI18N
        minimize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                minimizeMouseClicked(evt);
            }
        });

        back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Back-white-28.png"))); // NOI18N
        back.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout navigation_barLayout = new javax.swing.GroupLayout(navigation_bar);
        navigation_bar.setLayout(navigation_barLayout);
        navigation_barLayout.setHorizontalGroup(
            navigation_barLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, navigation_barLayout.createSequentialGroup()
                .addGap(0, 486, Short.MAX_VALUE)
                .addComponent(back)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(minimize)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(close, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        navigation_barLayout.setVerticalGroup(
            navigation_barLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navigation_barLayout.createSequentialGroup()
                .addGroup(navigation_barLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(minimize, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(back, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(close, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(navigation_bar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 590, 30));

        HighScorelbl.setFont(new java.awt.Font("Tempus Sans ITC", 1, 30)); // NOI18N
        HighScorelbl.setText("Top Scores");
        getContentPane().add(HighScorelbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 50, 190, 60));

        jTable1.setBackground(new java.awt.Color(52, 52, 52));
        jTable1.setFont(new java.awt.Font("Ubuntu Mono", 0, 20)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 120, 410, 550));

        TopScoreBg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Student,Admin background.png"))); // NOI18N
        getContentPane().add(TopScoreBg, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, -30, 230, -1));

        Background.setBackground(new java.awt.Color(52, 52, 52));
        Background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/HighScoreBg.png"))); // NOI18N
        Background.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 1, 1, new java.awt.Color(255, 255, 255)));
        getContentPane().add(Background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 27, 590, 690));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void minimizeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minimizeMouseClicked
        //Thsi code segment is to minimize the Highscore GUI.
        setState(JFrame.ICONIFIED);
    }//GEN-LAST:event_minimizeMouseClicked

    private void backMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backMouseClicked
        //This code segment is to go to StudentPanelGUI
         setVisible(false);
         new StudentPanel().setVisible(true);
    }//GEN-LAST:event_backMouseClicked

    private void closeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeMouseClicked
        //This code segment is to close the highscore gui and to go to IndexGUI
        setVisible(false);
        new AA_IndexGUI().setVisible(true);
    }//GEN-LAST:event_closeMouseClicked

    private void navigation_barMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navigation_barMouseDragged
        //The "mouse dragged" event. This MouseEvent occurs when the mouse position changes while a mouse button is pressed.
        Point dragged = evt.getLocationOnScreen(); //Returns the absolute x, y position of the event.
        int x = (int)(location.x + dragged.getX() - pressed.getX());
        int y = (int)(location.y + dragged.getY() - pressed.getY());
        Window window = SwingUtilities.windowForComponent(evt.getComponent());
        window.setLocation(x, y);
    }//GEN-LAST:event_navigation_barMouseDragged

    private void navigation_barMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navigation_barMousePressed
         //The "mouse pressed" event. This MouseEvent occurs when a mouse button is pushed down.
         pressed = evt.getLocationOnScreen();//Returns the absolute x, y position of the event.
         Window window = SwingUtilities.windowForComponent(evt.getComponent());  //Creating object of Window.
         location = window.getLocation();//Returns the absolute x, y position of the event.
    }//GEN-LAST:event_navigation_barMousePressed

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
                if ("windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(S_HighScores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(S_HighScores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(S_HighScores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(S_HighScores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new S_HighScores().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Background;
    private javax.swing.JLabel HighScorelbl;
    private javax.swing.JLabel TopScoreBg;
    private javax.swing.JLabel back;
    private javax.swing.JLabel close;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel minimize;
    private javax.swing.JPanel navigation_bar;
    // End of variables declaration//GEN-END:variables
}