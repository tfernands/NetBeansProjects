/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author TERMINAL
 */
public class SQLSearchTextField extends javax.swing.JLayeredPane {
    
    private static final String TEXT_KEY = "getText\\(\\)";
    
    private Connection con;
    private String templateSQL;
    private final ActionListener comboBoxAction;
    private final DefaultComboBoxModel comboBoxModel;
    
    public SQLSearchTextField(){
        initComponents();
        comboBoxModel = new DefaultComboBoxModel<>();
        comboBox.setModel(comboBoxModel);
        textField.getDocument().addDocumentListener(new DocumentListener(){
             @Override
            public void insertUpdate(DocumentEvent de) {
                updateSugestions();
            }
            @Override
            public void removeUpdate(DocumentEvent de) {
                updateSugestions();
            }
            @Override
            public void changedUpdate(DocumentEvent de) {}
            
        });
        comboBoxAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                comboBoxActionEvent(ae);
            }
        };
    }
    
    public void define(Connection connection, String templateSQL){
        this.con = connection;
        this.templateSQL = templateSQL;
        label.setText("");
        setText("");
        query();
    }
    
    public void setText(String text){
        textField.setText(text);
    }
    
    public String getText(){
        return textField.getText();
    }
    
    public void setLabel(String label){
        this.label.setText(label);
    }
    
    public JLabel getLabel(){
        return label;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        comboBoxLayer = new javax.swing.JPanel();
        comboBox = new javax.swing.JComboBox<>();
        labelLayer = new javax.swing.JPanel();
        textFieldLayer = new javax.swing.JPanel();
        textField = new javax.swing.JTextField();

        setLayout(new javax.swing.OverlayLayout(this));

        comboBoxLayer.setFocusable(false);
        comboBoxLayer.setOpaque(false);

        comboBox.setEditable(true);
        comboBox.setRequestFocusEnabled(true);

        javax.swing.GroupLayout comboBoxLayerLayout = new javax.swing.GroupLayout(comboBoxLayer);
        comboBoxLayer.setLayout(comboBoxLayerLayout);
        comboBoxLayerLayout.setHorizontalGroup(
            comboBoxLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(comboBox, 0, 111, Short.MAX_VALUE)
        );
        comboBoxLayerLayout.setVerticalGroup(
            comboBoxLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, comboBoxLayerLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(comboBox))
        );

        add(comboBoxLayer);

        labelLayer.setFocusable(false);
        labelLayer.setLayout(null);

        label.setForeground(new java.awt.Color(153, 153, 153));
        label.setText("USE DEFINE FUNCTION");
        label.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
        label.setFocusable(false);
        labelLayer.add(label);
        label.setBounds(0, 0, 157, 23);

        setLayer(labelLayer, javax.swing.JLayeredPane.PALETTE_LAYER);
        add(labelLayer);

        textFieldLayer.setFocusable(false);
        textFieldLayer.setOpaque(false);

        textField.setOpaque(false);
        textField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textFieldKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout textFieldLayerLayout = new javax.swing.GroupLayout(textFieldLayer);
        textFieldLayer.setLayout(textFieldLayerLayout);
        textFieldLayerLayout.setHorizontalGroup(
            textFieldLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, textFieldLayerLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(textField, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE))
        );
        textFieldLayerLayout.setVerticalGroup(
            textFieldLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(textFieldLayerLayout.createSequentialGroup()
                .addComponent(textField)
                .addGap(0, 0, 0))
        );

        setLayer(textFieldLayer, javax.swing.JLayeredPane.MODAL_LAYER);
        add(textFieldLayer);
    }// </editor-fold>//GEN-END:initComponents

    private void textFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textFieldKeyPressed
        System.out.print("textField KEY: "+evt.getKeyCode()+" -> ");
        switch(evt.getKeyCode()){
            case 10:
            System.out.println("ENTER (copy comboBox selected item to textField)");
            setText((String) comboBox.getSelectedItem());
            break;
            case 40:
            System.out.println("DOWN (set comboBox focus)");
            comboBox.requestFocus();
            break;
            default:
            System.out.println(evt.getKeyChar()+" (not maped)");
            break;
        }
    }//GEN-LAST:event_textFieldKeyPressed
    
    private void comboBoxActionEvent(ActionEvent ev){
        if (textField.hasFocus()){
            setText((String) comboBox.getSelectedItem());
        }else{
            if (ev.getActionCommand().equals("comboBoxEdited")){
                setText((String) comboBox.getSelectedItem());
                textField.requestFocus();
                comboBox.hidePopup();
            }else{
                setLabelSugestion((String)comboBoxModel.getSelectedItem());
            }
        }
    }
    
    private void updateSugestions(){
        //temporary remove listener to prevent callback loops
        comboBox.removeActionListener(comboBoxAction);
        //query sql and get results startion with condition
        query();
        //sugestion
        if (textField.getText().length() == 0 ||  comboBoxModel.getSize() == 0){
            label.setText("");
        }else{
            setLabelSugestion((String)comboBoxModel.getElementAt(0));
        }
        //add listener back
        comboBox.addActionListener(comboBoxAction);
        comboBox.hidePopup();
        comboBox.showPopup();
    }
    
    private void query(){
        comboBoxModel.removeAllElements();
        String sql = sqlFromTemplate(templateSQL);
        try{
            ResultSet rs = con.createStatement().executeQuery(sql);
            while(rs.next()){
                comboBoxModel.addElement(rs.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SQLSearchTextField.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private String sqlFromTemplate(String templateSQL){
        String sql = "";
        String[] spots = templateSQL.split(TEXT_KEY);
        for (int i = 0; i < spots.length; i++){
            sql += spots[i];
            if (i < spots.length-1) sql += textField.getText();
        }
        return sql;
    }
    
    private void setLabelSugestion(String sugestion){
        int x = textField.getFontMetrics(textField.getFont()).stringWidth(textField.getText());
        label.setText(sugestion.replaceFirst(textField.getText(), ""));
        label.setBounds(x,0,textField.getSize().width-x, textField.getSize().height);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> comboBox;
    private javax.swing.JPanel comboBoxLayer;
    private final javax.swing.JLabel label = new javax.swing.JLabel();
    private javax.swing.JPanel labelLayer;
    private javax.swing.JTextField textField;
    private javax.swing.JPanel textFieldLayer;
    // End of variables declaration//GEN-END:variables
}