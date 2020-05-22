/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author TERMINAL
 */
public class BDConnection {
    
    Connection con = null;
    Statement stm = null;
    
    public void connect(String baseName, String local, String user, String key){
           System.out.println("Connecting to database...");
        try {
            con = DriverManager.getConnection("jdbc:postgresql://"+local+":5432"+"/"+baseName,user,key);
        } catch (SQLException ex) {
            Logger.getLogger(BDConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ArrayList<String> getTablesList(String startWith) throws SQLException{
        ArrayList<String> list = new ArrayList<>();
        try (Statement stm = con.createStatement()) {
            String sql = "SELECT table_name FROM information_schema.tables WHERE table_schema = 'public' AND table_name LIKE '"+startWith+"%' ORDER BY table_name";
            ResultSet rs = stm.executeQuery(sql);
            while(rs.next())
            list.add(rs.getString(1));
            //STEP 6: Clean-up environment
        }
        return list;
    }
    
    public ArrayList<String> getTablesList() throws SQLException{
        ArrayList<String> list = new ArrayList<>();
        try (Statement stm = con.createStatement()) {
            String sql = "SELECT table_name FROM information_schema.tables WHERE table_schema = 'public' ORDER BY table_name";
            ResultSet rs = stm.executeQuery(sql);
            while(rs.next())
            list.add(rs.getString(1));
            //STEP 6: Clean-up environment
        }
        return list;
    }
     
}
