/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Assembly.Database;

import Assembly.Controllers.InvoiceServlet;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.derby.client.am.Types;

public class AccessTransactions {
    
    private final Connection con;
    private int userId;
    
    public AccessTransactions(Connection con)
    {this.con = con;}
    
    public void setShopperID(int userId)
    {this.userId = userId;}
    
    public void close()
    {   try {
        con.close();
        } catch (SQLException ex) 
        { ex.printStackTrace();}
    }
    
    public boolean discExists(String disc){
        String query  = "SELECT DISCOUNT_CODES FROM DISCOUNTS";
        if(disc.equals("")){
            try(PreparedStatement ps = con.prepareStatement(query)){
                try(ResultSet rs = ps.executeQuery()){
                        while(rs.next()){
                            if(!disc.equals(rs.getString("DISCOUNT_CODES"))){
                                return false;
                            }    
                        }
                        return true;
                    }
                } catch (SQLException ex) 
                {ex.printStackTrace();}
        }
        return false;
    }
    
    public void addTransact(Date today, int orderNum, int codeId){
        String queryTrans = 
               "INSERT INTO TRANSACTION_HISTORY(DATE_OF_REPORT, ORDER_NUMBER, USER_ID, CODE_ID) "
                + "VALUES(?,?,?,?)";
        try(PreparedStatement ps = con.prepareStatement(queryTrans)){
            ps.setDate(1, today);
            ps.setInt(2, orderNum);
            System.out.println("ORDER NUMBER transact: " + orderNum);
            ps.setInt(3, userId);
            if(codeId == 0)
                ps.setNull(4, Types.INTEGER);
            else
                ps.setInt(4, codeId);
            int rows = ps.executeUpdate();
            System.out.println("AFFECTED ROWS, TRANSACT: " +rows);
        }catch(SQLException e)
        {e.printStackTrace();}
    }
    
    public int getOrderID(int orderNum){
        String queryOrder= "SELECT ORDER_ID FROM TRANSACTION_HISTORY WHERE ORDER_NUMBER = ?";
        try(PreparedStatement ps = con.prepareStatement(queryOrder)){
            ps.setInt(1, orderNum);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next())
                    return rs.getInt("ORDER_ID");
            }
        } catch (SQLException ex) {
            Logger.getLogger(InvoiceServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    public int getOrderNum(int orderId){
        String queryOrder= "SELECT ORDER_NUMBER FROM TRANSACTION_HISTORY WHERE ORDER_ID = ?";
        try(PreparedStatement ps = con.prepareStatement(queryOrder)){
            ps.setInt(1, orderId);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next())
                    return rs.getInt("ORDER_NUMBER");
            }
        } catch (SQLException ex) {
            Logger.getLogger(InvoiceServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
          
    public int getNewNum(int orderNum){
        int orderNum2 = 0;
        int orderNumRet = orderNum;
        do{
            String queryOrderNum = "SELECT ORDER_NUMBER FROM TRANSACTION_HISTORY WHERE ORDER_NUMBER = ?";
            try(PreparedStatement ps = con.prepareStatement(queryOrderNum)){
                ps.setInt(1, orderNum);
                try(ResultSet rs = ps.executeQuery()){
                    if(rs.next()){
                        orderNum2 = rs.getInt("order_number");
                        orderNumRet = getOrderNumber(50000, 1);
                    }
                }
             } catch (SQLException ex) {
                Logger.getLogger(InvoiceServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }while(orderNum2 == orderNum);
        return orderNumRet;
    }
    public int getOrderNumber(int min, int max) {
        return (int)((Math.random() * (max - min)) + min);
    }
    
    public boolean isNull(String word){
        return word == null && word.isEmpty();
    }
}
