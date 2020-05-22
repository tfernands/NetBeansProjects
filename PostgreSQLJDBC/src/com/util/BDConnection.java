/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author TERMINAL
 */
public class BDConnection {
    
    public static Connection con = null;
    public static Statement stm = null;
    
    public static void connect(){
        try {
            con = DriverManager.getConnection("jdbc:postgresql://"+Config.base_url+":5432"+"/"+Config.base_name, Config.base_user, Config.base_key);
            System.out.println("Connection status: connected");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Error conexão não realizada", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(BDConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void close(){
        try {
            BDConnection.con.close();
            System.out.println("Connection status: conection closed");
        } catch (SQLException ex) {
            Logger.getLogger(BDConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
