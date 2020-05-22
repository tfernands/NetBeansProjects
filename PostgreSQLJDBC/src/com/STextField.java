/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.OverlayLayout;
import sun.awt.resources.awt;


/**
 *
 * @author TERMINAL
 */
public class STextField extends javax.swing.JLayeredPane {
    
    private JPanel labelPanel;
    private JLabel label;
    private JComboBox<String> comboBox;
    private JTextField boxTextField;
    private String[] options;
    
    transient ActionEvent aev;
    
    public STextField(){
        initComponents();
    }
    
    public void setOptions(String[] optionsList){
        options = optionsList;
        comboBox.setModel(
                new javax.swing.DefaultComboBoxModel<>(options)
        );
    }

    public void addActionListener(ActionListener a){
        a.actionPerformed(aev);
    }
    
    private void update(String txt){
        String sugestion = null;
        
        if (txt.length() == 0){
            label.setVisible(false);
            comboBox.hidePopup();
            return;
        }
        for (int i = 0; i < comboBox.getModel().getSize(); i++){
            if (comboBox.getItemAt(i).startsWith(txt)){
                int sStart = boxTextField.getSelectionStart();
                int sEnd = boxTextField.getSelectionEnd();
                String tempString = boxTextField.getText();
                
                comboBox.setSelectedIndex(i);
                
                boxTextField.setText(tempString);
                boxTextField.setSelectionStart(sStart);
                boxTextField.setSelectionEnd(sEnd);
                sugestion = comboBox.getModel().getElementAt(i).replaceFirst(txt, "");
                break;
            }
        }
        
        if (sugestion != null){
            int x = boxTextField.getFontMetrics(boxTextField.getFont()).stringWidth(txt);
            label.setBounds(x,1,boxTextField.getWidth()-x,boxTextField.getHeight());
            label.setText(sugestion);
            label.setVisible(true);
            comboBox.showPopup();
        }else{
            label.setVisible(false);
            comboBox.hidePopup();
        }
    }
    
    private void initComponents() {
        comboBox = new javax.swing.JComboBox<>();
        boxTextField = (JTextField) comboBox.getEditor().getEditorComponent();
        labelPanel = new JPanel();
        label = new JLabel();

        setLayout(new OverlayLayout(this));
        setOpaque(false);
        setBackground(boxTextField.getBackground());

        comboBox.setOpaque(false);
        comboBox.setEditable(true);
        boxTextField.setOpaque(false);
        setLayer(comboBox, 0);
        add(comboBox);

        labelPanel.setOpaque(false);
        labelPanel.setLayout(null);
        
        label.setText("teste");
        label.setForeground(new java.awt.Color(150, 150, 150));
        label.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        labelPanel.add(label);
        
        setLayer(labelPanel, 1);
        add(labelPanel);
        
        comboBox.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                label.setVisible(false);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        boxTextField.addKeyListener(new KeyListener(){
            @Override
            public void keyTyped(KeyEvent ke) {
                String txt = boxTextField.getText();
                String selected = boxTextField.getSelectedText();
                if (ke.getKeyChar() != KeyEvent.VK_ENTER && ke.getKeyChar() != KeyEvent.VK_BACK_SPACE){
                    if (selected != null){
                        txt = txt.replace(selected, ke.getKeyChar()+"");
                    }else{
                        txt = txt.substring(0, boxTextField.getCaretPosition())+ke.getKeyChar()+txt.substring(boxTextField.getCaretPosition(),txt.length());
                    }
                }
                update(txt);
                if (ke.getKeyChar() == KeyEvent.VK_ENTER){
                    label.setVisible(false);
                    comboBox.hidePopup();
                    boxTextField.setText((String) comboBox.getSelectedItem());
                    aev.getSource();
                }
            }
            @Override
            public void keyPressed(KeyEvent ke) {
                label.setVisible(false);
            }
            @Override
            public void keyReleased(KeyEvent ke) {}
        });
        
    }
    
}
