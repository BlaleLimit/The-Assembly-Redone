/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Assembly.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AccessInventory {
    
    private final Connection con;
    
    //initialize connection
    public AccessInventory(Connection con)
    {this.con = con;}
    
    public void close()
    {   try {
        con.close();
        } catch (SQLException ex) 
        { ex.printStackTrace();}
    }
    
    //<editor-fold defaultstate="collapsed" desc="AccessInventory Select Queries">
    //Select Query statements
    
    //gets prod_ID up to description from INVENTORY table
    public String[] showProdInfo(String name){
        List prodInfo = new ArrayList();
        String query = "SELECT * FROM INVENTORY WHERE model_name = ?";
        try(PreparedStatement ps = con.prepareStatement(query)){
            ps.setString(1, name);
            try(ResultSet rs = ps.executeQuery())
            {
                String[] info;
                if(rs.next()){
                    prodInfo.add(rs.getInt("product_ID"));
                    prodInfo.add(rs.getString("model_name"));
                    prodInfo.add(rs.getInt("price"));
                    prodInfo.add(rs.getInt("stock"));
                    prodInfo.add(rs.getString("product_type"));
                    prodInfo.add(rs.getString("description"));
                }
                info = (String[])prodInfo.toArray();
                return info;
            }
        } catch (SQLException ex)
        {ex.printStackTrace();}
        return null;
    }
    
    public String[] showProdInfo(int num){
        List prodInfo = new ArrayList();
        String query = "SELECT * FROM INVENTORY WHERE product_id = ?";
        try(PreparedStatement ps = con.prepareStatement(query)){
            ps.setInt(1, num);
            try(ResultSet rs = ps.executeQuery())
            {
                String[] info;
                if(rs.next()){
                    prodInfo.add(rs.getInt("product_ID"));
                    prodInfo.add(rs.getString("model_name"));
                    prodInfo.add(rs.getInt("price"));
                    prodInfo.add(rs.getInt("stock"));
                    prodInfo.add(rs.getString("product_type"));
                    prodInfo.add(rs.getString("description"));
                }
                info = (String[])prodInfo.toArray();
                return info;
            }
        } catch (SQLException ex)
        {ex.printStackTrace();}
        return null;
    }  
        
    public int getProductID(String name){
        String query = "SELECT product_id FROM INVENTORY WHERE model_name = ?";
        try(PreparedStatement ps = con.prepareStatement(query)){
            ps.setString(1, name);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next())
                    return rs.getInt("Product_ID");
            }
        } catch (SQLException ex)
        {ex.printStackTrace();}
       return 0;
    }
    
    public String getProductName(String name){
        String query = "SELECT model_name FROM INVENTORY WHERE model_name = ?";
        try(PreparedStatement ps = con.prepareStatement(query)){
            ps.setString(1, name);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next())
                    return rs.getString("model_name");
                else
                   return "";
            }
        } catch (SQLException ex)
        {ex.printStackTrace();}
       return "";
    }
    
    public String getProductName(int num){
        String query = "SELECT model_name FROM INVENTORY WHERE product_id = ?";
        try(PreparedStatement ps = con.prepareStatement(query)){
            ps.setInt(1, num);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next())
                    return rs.getString("model_name");
                else
                   return "";
            }
        } catch (SQLException ex)
        {ex.printStackTrace();}
        return "";
    }
    
    public int getProductStock(int num){
        String query = "SELECT stock FROM INVENTORY WHERE product_id = ?";
        try(PreparedStatement ps = con.prepareStatement(query)){
            ps.setInt(1, num);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next())
                    return rs.getInt("stock");
                else
                   return 0;
            }
        } catch (SQLException ex)
        {ex.printStackTrace();}
        return 0;
    }
    
     public int getProductStock(String prodName){
        String query = "SELECT stock FROM INVENTORY WHERE model_name = ?";
        try(PreparedStatement ps = con.prepareStatement(query)){
            ps.setString(1, prodName);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next())
                    return rs.getInt("stock");
                else
                   return 0;
            }
        } catch (SQLException ex)
        {ex.printStackTrace();}
        return 0;
    }
     
    public int getPrice(String prodName){
        String query = "SELECT price FROM INVENTORY WHERE model_name = ?";
        try(PreparedStatement ps = con.prepareStatement(query)){
            ps.setString(1, prodName);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next())
                    return rs.getInt("price");
                else
                   return 0;
            }
        } catch (SQLException ex)
        {ex.printStackTrace();}
        return 0;
    }
     
    public int getPrice(int prodId){
        String query = "SELECT price FROM INVENTORY WHERE product_id = ?";
        try(PreparedStatement ps = con.prepareStatement(query)){
            ps.setInt(1, prodId);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next())
                    return rs.getInt("price");
                else
                   return 0;
            }
        } catch (SQLException ex)
        {ex.printStackTrace();}
        return 0;
    }
    
    
    public String[] getProductDesc(String name){
        String desc = "";
        String query = "SELECT description FROM INVENTORY WHERE model_name = ?";
        try(PreparedStatement ps = con.prepareStatement(query)){
            ps.setString(1, name);
            try(ResultSet rs = ps.executeQuery())
            {
                String[] descArr;
                if(rs.next()){
                    desc = rs.getString("description").trim();
                }
                descArr = desc.split(";");
                return descArr;
            }
        } catch (SQLException ex)
        {ex.printStackTrace();}
        return null;
    }
    
    public String[] getProductDesc(int prod_id){
        
        String query = "SELECT description FROM INVENTORY WHERE product_id = ?";
        try(PreparedStatement ps = con.prepareStatement(query)){
            ps.setInt(1, prod_id);
            try(ResultSet rs = ps.executeQuery())
            {
                String[] descArr;
                String desc = "";
                if(rs.next()){
                    desc = rs.getString("description").trim();
                }
                descArr = desc.split(";");
                return descArr;
            }
        } catch (SQLException ex)
        {ex.printStackTrace();}
        return null;
    }
    
    public String[] getSearchResult(String search){
        String query = "SELECT MODEL_NAME, PRODUCT_TYPE, PRICE, STOCK, DESCRIPTION FROM INVENTORY WHERE "
                + "UPPER(MODEL_NAME) LIKE UPPER(?) "
                + "OR UPPER(PRODUCT_TYPE) LIKE UPPER(?)";
        try(PreparedStatement ps = con.prepareStatement(query)){
            ps.setString(1, "%" + search + "%");
            ps.setString(2, "%" + search + "%");
            try(ResultSet rs = ps.executeQuery()){
                ArrayList<String> arrTemp = new ArrayList<String>();
                while(rs.next()){
                    arrTemp.add(rs.getString(1));
                    arrTemp.add(rs.getString(2));
                    arrTemp.add(rs.getString(3));
                    arrTemp.add(rs.getString(4));
                    arrTemp.add(rs.getString(5));
                }
                String[] arr = new String[arrTemp.size()];
                arr = arrTemp.toArray(arr);
                return arr;
            }
        }catch (SQLException ex)
        {ex.printStackTrace();}
        return null;
    }
    
    public String[] getSearchResult(){
        
        String query = "SELECT MODEL_NAME, PRODUCT_TYPE, PRICE, STOCK, DESCRIPTION FROM INVENTORY";
       
        try(PreparedStatement ps = con.prepareStatement(query)){

            try(ResultSet rs = ps.executeQuery()){
                ArrayList<String> arrTemp = new ArrayList<String>();
                while(rs.next()){
                    arrTemp.add(rs.getString(1));
                    arrTemp.add(rs.getString(2));
                    arrTemp.add(rs.getString(3));
                    arrTemp.add(rs.getString(4));
                    arrTemp.add(rs.getString(5));
                }
                String[] arr = new String[arrTemp.size()];
                arr = arrTemp.toArray(arr);
                return arr;
            }
        }catch (SQLException ex)
        {ex.printStackTrace();}
        return null;
    }

    // End of Select Query statements
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="AccessInventory DML Queries">
    //DML Query statements
     public void addProduct(String[] prodInfo){
        String query = "INSERT INTO INVENTORY "
            + "(MODEL_NAME, PRICE, STOCK, PRODUCT_TYPE, DESCRIPTION) "
            + "VALUES (?, ?, ?, ?, ?)";
        try(PreparedStatement ps = con.prepareStatement(query)){
            int cnt = 1;
            for(String info : prodInfo){
                ps.setString(cnt, info);
                cnt++;
            }
            ps.executeUpdate();
        } catch (SQLException ex)
         {ex.printStackTrace();}
    }
     
    public void removeProduct(String prodName){
        String query = "DELETE FROM INVENTORY WHERE MODEL_NAME = ?";
        try(PreparedStatement ps = con.prepareStatement(query)){
            ps.setString(1, prodName);
            ps.executeUpdate();
        } catch (SQLException ex)
         {ex.printStackTrace();}
    }
    
    public void reduceItemStock(int reducedStock, String prod){
        String query1 = "SELECT stock FROM INVENTORY where model_name= ?";
        String query2 ="UPDATE INVENTORY "
                        + "SET stock = ?"
                        + "WHERE model_name = ?";
        try(PreparedStatement ps = con.prepareStatement(query1)){
            ps.setString(1,prod);
            int currentStock = 0;
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next())
                    currentStock = rs.getInt("stock"); 
            }
            try(PreparedStatement ps2 = con.prepareStatement(query2)){
                   System.out.println("The Stock in total is(current + added): "+ (currentStock - reducedStock));
                   if((currentStock - reducedStock) < 0)
                       return;
                   else{
                       ps2.setInt(1, currentStock - reducedStock);
                       ps2.setString(2, prod);
                       ps2.executeUpdate();
                   }
            }
        }catch (SQLException ex)
        {ex.printStackTrace();}
    }
    
    public void reduceItemStock(int reducedStock, int prod_id){
        String query1 = "SELECT stock FROM INVENTORY where prod_id = ?";
        String query2 =
                "UPDATE INVENTORY "
                + "SET stock = ?"
                + "WHERE prod_ID = ?";
        try(PreparedStatement ps = con.prepareStatement(query1)){
            ps.setInt(1,prod_id);
            int currentStock = 0;
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next())
                    currentStock = rs.getInt("stock"); 
            }
            try(PreparedStatement ps2 = con.prepareStatement(query2)){
                System.out.println("The Stock in total is(current + added): "+ (currentStock - reducedStock));
                if((currentStock - reducedStock) < 0)
                    return;
                else{
                    ps2.setInt(1, currentStock - reducedStock);
                    ps2.setInt(2, prod_id);
                    ps2.executeUpdate();
                }
            }
        }catch (SQLException ex)
        {ex.printStackTrace();}
    }
    
    public void addItemStock(int addedStock, int prodId){
        String query1 = "SELECT stock FROM INVENTORY where prod_id = ?";
        String query2 =
                "UPDATE INVENTORY "
                + "SET stock = ?"
                + "WHERE prod_ID = ?";
        try(PreparedStatement ps = con.prepareStatement(query1)){
            ps.setInt(1,prodId);
            int currentStock = 0;
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next())
                    currentStock = rs.getInt("stock"); 
            }
            try(PreparedStatement ps2 = con.prepareStatement(query2)){
                System.out.println("The Stock in total is(current + added): "+ currentStock + addedStock);
                ps2.setInt(1, addedStock + currentStock);
                ps2.setInt(2, prodId);
                ps2.executeUpdate();
            }
        }catch (SQLException ex)
        {ex.printStackTrace();}
    }
    
    public void addItemStock(int addedStock, String prod){
        String query1 = "SELECT stock FROM INVENTORY where model_name = ?";
        String query2 = "UPDATE INVENTORY "
                        + "SET stock = ?"
                        + "WHERE model_name = ?";
        try(PreparedStatement ps = con.prepareStatement(query1)){
            ps.setString(1,prod);
            int currentStock = 0;
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next())
                    currentStock = rs.getInt("stock"); 
            }
            try(PreparedStatement ps2 = con.prepareStatement(query2)){
                   System.out.println("The Stock in total is(current + added): "+ currentStock + addedStock);
                   ps2.setInt(1, addedStock);
                   ps2.setString(2, prod);
                   ps2.executeUpdate();
            }
        }catch (SQLException ex)
        {ex.printStackTrace();}
    }
    
    public void updatePrice(int price, String prod){
        String query1 = "SELECT price FROM INVENTORY where model_name = ?";
        String query2 =
                "UPDATE INVENTORY "
                + "SET price = ?"
                + "WHERE model_name = ?";
        try(PreparedStatement ps = con.prepareStatement(query1)){
            ps.setString(1, prod);
            try(ResultSet rs = ps.executeQuery()){
                int currentPrice = 0;
                if(rs.next())
                    currentPrice = rs.getInt("price"); 
                try(PreparedStatement ps2 = con.prepareStatement(query2)){
                    System.out.println("The Price is (oldPrice -> newPrice): " + currentPrice + price);
                    ps2.setInt(1, price);
                    ps2.setString(2, prod);
                    ps2.executeUpdate();
                }
            }
        }catch (SQLException ex)
        {ex.printStackTrace();}
 }
    
    public void updateDesc(String desc, String prod){
        String query1 = "SELECT description FROM INVENTORY where model_name = ?";
        String query2 =
                "UPDATE INVENTORY "
                + "SET description = ?"
                + "WHERE model_name = ?";
        try(PreparedStatement ps = con.prepareStatement(query1)){
            ps.setString(1, prod);
            try(ResultSet rs = ps.executeQuery()){
                String currentDesc;
                if(rs.next())
                    currentDesc = rs.getString("description"); 
                try(PreparedStatement ps2 = con.prepareStatement(query2)){
                    System.out.println("The Description has been changed");
                    ps2.setString(1, desc);
                    ps2.setString(2, prod);
                    ps2.executeUpdate();
                }
            }
        }catch (SQLException ex)
        {ex.printStackTrace();}
}
    
    //End of DML Query statements
    //</editor-fold>
    
}
