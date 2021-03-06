/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import com.util.Config;
import com.util.BDConnection;
import com.util.ReaderWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;

/**
 *
 * @author TERMINAL
 */
public class FormConexão extends javax.swing.JFrame {

    public FormConexão() {
        try {
            ReaderWriter.loadConfigs();
        } catch (ParserConfigurationException | SAXException | IOException | TransformerException ex) {
            Logger.getLogger(FormConexão.class.getName()).log(Level.SEVERE, null, ex);
        }
        initComponents();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        label_base = new javax.swing.JLabel();
        field_base_name = new javax.swing.JTextField();
        label_url = new javax.swing.JLabel();
        field_base_url = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        labelUser = new javax.swing.JLabel();
        field_base_user = new javax.swing.JTextField();
        labelKey = new javax.swing.JLabel();
        field_base_key = new javax.swing.JPasswordField();
        btn_connect = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Configurar conexão");
        setBackground(new java.awt.Color(204, 204, 204));
        setLocation(new java.awt.Point(100, 100));
        setLocationByPlatform(true);
        setResizable(false);
        setType(java.awt.Window.Type.UTILITY);

        jPanel3.setBackground(new java.awt.Color(102, 102, 102));
        jPanel3.setForeground(new java.awt.Color(255, 255, 255));
        jPanel3.setFocusable(false);

        label_base.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label_base.setForeground(new java.awt.Color(204, 204, 204));
        label_base.setText("Base ");

        field_base_name.setBackground(new java.awt.Color(102, 102, 102));
        field_base_name.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        field_base_name.setForeground(new java.awt.Color(255, 255, 255));
        field_base_name.setText(com.util.Config.base_name);
        field_base_name.setBorder(null);
        field_base_name.setMinimumSize(new java.awt.Dimension(15, 15));
        field_base_name.setNextFocusableComponent(field_base_url);
        field_base_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                field_base_nameActionPerformed(evt);
            }
        });

        label_url.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label_url.setForeground(new java.awt.Color(204, 204, 204));
        label_url.setText("URL ");

        field_base_url.setBackground(new java.awt.Color(102, 102, 102));
        field_base_url.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        field_base_url.setForeground(new java.awt.Color(255, 255, 255));
        field_base_url.setText(com.util.Config.base_url);
        field_base_url.setBorder(null);
        field_base_url.setMinimumSize(new java.awt.Dimension(15, 15));
        field_base_url.setNextFocusableComponent(field_base_user);
        field_base_url.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                field_base_urlActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label_base)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(field_base_name, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label_url)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(field_base_url, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_base)
                    .addComponent(field_base_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_url)
                    .addComponent(field_base_url, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        getContentPane().add(jPanel3, java.awt.BorderLayout.NORTH);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setFocusable(false);

        labelUser.setText("Usuario");

        field_base_user.setText(com.util.Config.base_user);
        field_base_user.setFocusCycleRoot(true);
        field_base_user.setNextFocusableComponent(field_base_key);
        field_base_user.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                field_base_userActionPerformed(evt);
            }
        });

        labelKey.setText("Senha");

        field_base_key.setText(com.util.Config.base_key);
        field_base_key.setNextFocusableComponent(btn_connect);
        field_base_key.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                field_base_keyActionPerformed(evt);
            }
        });

        btn_connect.setText("Conectar");
        btn_connect.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_connect.setOpaque(false);
        btn_connect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_connectActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelUser)
                    .addComponent(labelKey))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(field_base_key, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                    .addComponent(field_base_user))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btn_connect)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelUser)
                    .addComponent(field_base_user, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelKey)
                    .addComponent(field_base_key, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_connect)
                .addGap(4, 4, 4))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void field_base_keyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_field_base_keyActionPerformed
        field_base_key.nextFocus();
    }//GEN-LAST:event_field_base_keyActionPerformed

    private void btn_connectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_connectActionPerformed
        Config.base_name = field_base_name.getText();
        Config.base_url = field_base_url.getText();
        Config.base_user = field_base_user.getText();
        Config.base_key = field_base_key.getText();
        BDConnection.connect();
        BDConnection.close();
        FormExport e = new FormExport();
        e.setVisible(true);
    }//GEN-LAST:event_btn_connectActionPerformed

    private void field_base_userActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_field_base_userActionPerformed
        field_base_user.nextFocus();
    }//GEN-LAST:event_field_base_userActionPerformed

    private void field_base_urlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_field_base_urlActionPerformed
        field_base_url.nextFocus();
    }//GEN-LAST:event_field_base_urlActionPerformed

    private void field_base_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_field_base_nameActionPerformed
        field_base_name.nextFocus();
    }//GEN-LAST:event_field_base_nameActionPerformed

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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormConexão.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new FormConexão().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_connect;
    private javax.swing.JPasswordField field_base_key;
    private javax.swing.JTextField field_base_name;
    private javax.swing.JTextField field_base_url;
    private javax.swing.JTextField field_base_user;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel labelKey;
    private javax.swing.JLabel labelUser;
    private javax.swing.JLabel label_base;
    private javax.swing.JLabel label_url;
    // End of variables declaration//GEN-END:variables
}
