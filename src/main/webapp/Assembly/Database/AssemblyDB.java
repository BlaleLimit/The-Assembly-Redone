/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Assembly.Database;

import Assembly.Database.Unlock.KeyDentials;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AssemblyDB implements KeyDentials{

    // default connection
    public static Connection connect(){
        try{
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(URL, USERDB, PASSDB);
            return con;
        } catch (ClassNotFoundException | SQLException ex) 
        {ex.printStackTrace();}
        return null;
    }
    
    // with driver
    public static Connection connect(String url, String user, String pass){
        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(URL, user, pass);
            return con;
        } catch (ClassNotFoundException | SQLException ex) 
        {ex.printStackTrace();}
        return null;
    }
    
    // no database credentials 
    public static Connection connect(String driver, String url, String user, String pass){
        try {
            Class.forName(driver);
            Connection con = DriverManager.getConnection(URL, user, pass);
            return con;
        } catch (ClassNotFoundException | SQLException ex) 
        {ex.printStackTrace();}
        return null;
    }
}
