
package Assembly.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccessCart{
    
    private final Connection con;
    private int userId;
    
    //must provide connection in order to use getter queries
    public AccessCart(Connection con)
    {this.con = con;}
    
    // set shopper ID to initialize a specific user's shopping cart
    public void setShopperID(int userId)
    {this.userId = userId;}
    
    public void close()
    {   try {
        con.close();
        } catch (SQLException ex) 
        { ex.printStackTrace();}
    }
    
    //<editor-fold defaultstate="collapsed" desc="AccessCartQueries">
    public ResultSet showCart(int userId){
        String query = "SELECT * FROM SHOPPING_CART WHERE USER_ID = ?";
        try{
            PreparedStatement ps = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            return rs;
            
        } catch (SQLException ex) 
        {ex.printStackTrace();}
        return null;
    }
    
    public ResultSet showCart(){
        String query = "SELECT * FROM SHOPPING_CART WHERE USER_ID = ?";
        try{
            PreparedStatement ps = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            return rs;
            
        } catch (SQLException ex) 
        {ex.printStackTrace();}
        return null;
    }
    
    public int getItemQuantity(int prodId){
        String query = "SELECT quantity FROM SHOPPING_CART WHERE USER_ID = ? AND product_id = ?";
        try(PreparedStatement ps = con.prepareStatement(query)){
            ps.setInt(1, userId);
            ps.setInt(2, prodId);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next())
                    return rs.getInt("quantity");
            }
        } catch (SQLException ex) 
        {ex.printStackTrace();}
        return 0;
    }
    //end of Select queries
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="AccessCartDMLQueries">
    public void addAmountToItem(int prodId, int addedAmt){
        String updateQuery = 
                "UPDATE SHOPPING_CART "
                + "SET QUANTITY = ? "  
                + "WHERE USER_ID = ? AND product_id = ?";
        try(PreparedStatement ps = con.prepareStatement(updateQuery)){
            ps.setInt(1, addedAmt);
            ps.setInt(2, userId);
            ps.setInt(3, prodId);
            ps.executeUpdate();
        }catch (SQLException ex) 
        {ex.printStackTrace();}
    }
    
    public void addItem(int prodId){
        String query = "INSERT INTO SHOPPING_CART(user_id, product_id, quantity) "
                + "VALUES(?,?,?)";
        String queryCheck = "SELECT PRODUCT_ID, quantity FROM SHOPPING_CART WHERE PRODUCT_ID = ? AND USER_ID = ?";
        
        try(PreparedStatement ps = con.prepareStatement(queryCheck)){
            ps.setInt(1, prodId);
            ps.setInt(2, userId);
            try(ResultSet rs = ps.executeQuery()){
                int quantity;
                if(rs.next()){
                    quantity = rs.getInt("quantity") + 1;
                    this.addAmountToItem(prodId, quantity);
                }else{
                     try(PreparedStatement ps2 = con.prepareStatement(query)){
                         ps2.setInt(1, userId);
                         ps2.setInt(2, prodId);
                         ps2.setInt(3, 1);
                         int rows = ps2.executeUpdate();
                         System.out.println("AFFECTED ROWS: " + rows);
                     }
                }    
            }
        } catch (SQLException ex) 
        {ex.printStackTrace();}
    }
    
    public void deleteItem(int prodId){
        String query = "DELETE FROM SHOPPING_CART WHERE user_id = ? AND product_Id = ?";
        try(PreparedStatement ps = con.prepareStatement(query)){
            ps.setInt(1, userId);
            ps.setInt(2, prodId);
            ps.executeUpdate();
        } catch (SQLException ex) 
        {ex.printStackTrace();}
    }
    
    //</editor-fold>
}
