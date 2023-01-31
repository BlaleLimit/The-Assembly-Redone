
package Assembly.Database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AccessReports{
    
    private final Connection con;
    
    public AccessReports(Connection con)
    {this.con = con;}
    
    public void close()
    {   try {
        con.close();
        } catch (SQLException ex) 
        { ex.printStackTrace();}
    }
    
    public void addReport(Date today, int orderId, int prodId, 
    String prodName, double price, int quantity){
        String query = 
                "INSERT INTO "
                + "DAILY_REPORT(DATE_OF_REPORT, ORDER_ID, PRODUCT_ID, MODEL_NAME, PRICE, QUANTITY)"
                + "VALUES(?,?,?,?,?,?)";
        
        try(PreparedStatement psTr = con.prepareStatement(query)){
            psTr.setDate(1, today);
            psTr.setInt(2, orderId);
            psTr.setInt(3, prodId);
            psTr.setString(4, prodName);
            psTr.setDouble(5, price);
            psTr.setInt(6, quantity);
            int rows = psTr.executeUpdate();
            System.out.println("AFFECTED ROWS, INSERT INTO REPORT : " +rows);
        }catch(SQLException e)
        {e.printStackTrace();}
    } 
    
    
}
