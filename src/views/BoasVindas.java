/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package views;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import dal.ConnectionModule;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;

public class BoasVindas extends javax.swing.JInternalFrame {

    Connection database = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    public BoasVindas() {
        initComponents();
        database = ConnectionModule.conector();
        
        javax.swing.plaf.InternalFrameUI ui = this.getUI();
        if (ui instanceof javax.swing.plaf.basic.BasicInternalFrameUI) {
            javax.swing.plaf.basic.BasicInternalFrameUI basicUI = (javax.swing.plaf.basic.BasicInternalFrameUI) ui;

            // Remover o MouseMotionListener da barra de título
            basicUI.getNorthPane().removeMouseMotionListener(
                basicUI.getNorthPane().getMouseMotionListeners()[0]
            );

            // Remover a barra de título
            basicUI.getNorthPane().setVisible(false);

            // Remover a borda do internal frame (opcional)
            this.setBorder(null);
        }
    }
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setBorder(null);
        setForeground(java.awt.Color.gray);
        setTitle("Boas vindas");
        setAlignmentX(0.0F);
        setAlignmentY(0.0F);
        setPreferredSize(new java.awt.Dimension(908, 604));
        setVisible(true);

        jLabel1.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Selecione qualquer opção no menu superior para prosseguir.");

        jLabel2.setFont(new java.awt.Font("Tw Cen MT", 0, 36)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Boas vindas ao AutoGyn!");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 896, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 896, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(185, 185, 185)
                .addComponent(jLabel2)
                .addGap(6, 6, 6)
                .addComponent(jLabel1))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}
