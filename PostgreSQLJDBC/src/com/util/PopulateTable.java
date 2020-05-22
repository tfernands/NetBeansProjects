/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

import java.sql.ResultSet;
import java.sql.SQLException;import javax.swing.JTable;
;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author TERMINAL
 */
public class PopulateTable {
    
    public static void populate(JTable table, String Query){
        BDConnection.connect();
        try{
            ResultSet rs = BDConnection.con.createStatement().executeQuery(Query);
            //To remove previously added rows
            ((DefaultTableModel) table.getModel()).setRowCount(0);
            ((DefaultTableModel) table.getModel()).setColumnCount(0);
            int columns = rs.getMetaData().getColumnCount();
            for (int i = 1; i <= columns; i++){
                ((DefaultTableModel) table.getModel()).addColumn(rs.getMetaData().getColumnName(i));
            }
            while(rs.next()){  
                Object[] row = new Object[columns];
                for (int i = 1; i <= columns; i++){  
                    row[i - 1] = rs.getObject(i);
                }
                ((DefaultTableModel) table.getModel()).insertRow(rs.getRow()-1,row);
            }
            BDConnection.close();
        }
        catch(SQLException e){
        }
    }
    
}
