/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import com.util.ReaderWriter;
import com.util.Config;
import com.util.PopulateTable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/**
 *
 * @author TERMINAL
 */
public class FormExport extends javax.swing.JFrame {
    
    private static final DefaultListModel tabelasModel = new DefaultListModel();
    private final DefaultTableModel tableModel = new DefaultTableModel();
    private EditorTabela editTab = new EditorTabela();
    
    public FormExport() {
        initComponents();
        tablesList.setModel(tabelasModel);
        jTable2.setModel(tableModel);
        editTab.setVisible(false);
        for (String table_name : Config.tableFields.keySet()){
            tabelasModel.addElement(table_name);
        }
    }
    
    public static void addTable(String table_name, ArrayList<String> content){
        Config.tableFields.put(table_name, content);
        tabelasModel.addElement(table_name);
    }
    
    public static boolean containsTable(String table_name){
        return Config.tableFields.containsKey(table_name);
    }
    
    public static void removeTable(String table_name){
        Config.tableFields.remove(table_name);
        tabelasModel.removeElement(table_name);
    }
    
    public static ArrayList<String> getTable(String table_name){
        return Config.tableFields.get(table_name);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        textFieldDirectory = new javax.swing.JTextField();
        btnExportar = new javax.swing.JButton();
        btn_save = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablesList = new javax.swing.JList<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        label_nome_tabela = new javax.swing.JLabel();

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel3.setBackground(new java.awt.Color(102, 102, 102));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 204, 204));
        jLabel1.setText("Exportar para");

        textFieldDirectory.setBackground(new java.awt.Color(102, 102, 102));
        textFieldDirectory.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        textFieldDirectory.setForeground(new java.awt.Color(255, 255, 255));
        textFieldDirectory.setText(com.util.Config.exportDirectory);
        textFieldDirectory.setBorder(null);
        textFieldDirectory.setMinimumSize(new java.awt.Dimension(15, 15));
        textFieldDirectory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldDirectoryActionPerformed(evt);
            }
        });

        btnExportar.setText("Exportar");
        btnExportar.setToolTipText("");
        btnExportar.setOpaque(false);
        btnExportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportarActionPerformed(evt);
            }
        });

        btn_save.setText("Salvar");
        btn_save.setOpaque(false);
        btn_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_saveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(6, 6, 6)
                .addComponent(textFieldDirectory, javax.swing.GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExportar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_save)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(textFieldDirectory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExportar)
                    .addComponent(btn_save))
                .addContainerGap())
        );

        getContentPane().add(jPanel3, java.awt.BorderLayout.NORTH);

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));

        jLabel3.setText("Tabelas");

        tablesList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tablesList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablesListMouseClicked(evt);
            }
        });
        tablesList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                tablesListValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(tablesList);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jTable2.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane3.setViewportView(jTable2);

        jButton1.setText("editar");
        jButton1.setOpaque(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        label_nome_tabela.setForeground(new java.awt.Color(102, 102, 102));
        label_nome_tabela.setToolTipText("");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(label_nome_tabela)
                        .addGap(0, 409, Short.MAX_VALUE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_nome_tabela))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE))
                .addContainerGap())
        );

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void textFieldDirectoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldDirectoryActionPerformed
        Config.exportDirectory = textFieldDirectory.getText();
        textFieldDirectory.nextFocus();
    }//GEN-LAST:event_textFieldDirectoryActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        editTab.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tablesListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_tablesListValueChanged
        if (evt.getValueIsAdjusting()){
            String sql = "SELECT ";
            for (String field: Config.tableFields.get(tablesList.getSelectedValue())){
                sql += field+", ";
            }
            sql = sql.substring(0,sql.length()-2)+" FROM "+tablesList.getSelectedValue();
            System.out.println(sql);
            PopulateTable.populate(jTable2, sql);
            label_nome_tabela.setText(tablesList.getSelectedValue());
        }
    }//GEN-LAST:event_tablesListValueChanged

    private void tablesListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablesListMouseClicked
        if (evt.getClickCount() == 2){
            int index = tablesList.locationToIndex(evt.getPoint());
            editTab.selectTable(tablesList.getModel().getElementAt(index));
            editTab.setVisible(true);
        }
    }//GEN-LAST:event_tablesListMouseClicked

    private void btnExportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportarActionPerformed
        try {
            ReaderWriter.export();
        } catch (TransformerException | SQLException | ParserConfigurationException ex) {
            Logger.getLogger(FormExport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnExportarActionPerformed

    private void btn_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saveActionPerformed
        try {
            ReaderWriter.saveConfigs();
            JOptionPane.showMessageDialog(null, "Configurações salvas", "Config", JOptionPane.INFORMATION_MESSAGE);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(FormExport.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(FormExport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_saveActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExportar;
    private javax.swing.JButton btn_save;
    private javax.swing.JButton jButton1;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable2;
    private javax.swing.JLabel label_nome_tabela;
    private javax.swing.JList<String> tablesList;
    private javax.swing.JTextField textFieldDirectory;
    // End of variables declaration//GEN-END:variables
}