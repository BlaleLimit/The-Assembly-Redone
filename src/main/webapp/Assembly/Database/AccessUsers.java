/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Assembly.Database;

import Assembly.Crypto.Safe;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
public class AccessUsers {
    
    private final Connection con;
    
    // requires connection 
    public AccessUsers(Connection con)
    {this.con = con;}
    
    public void close()
    {   try {
        con.close();
        } catch (SQLException ex) 
        { ex.printStackTrace();}
    }
    
    // Select Queries
    
    //<editor-fold defaultstate="collapsed" desc="AccessUsers Select Queries">
    //gets User_ID up to contact_num from users table
    
    public String[] showUserInfo(){
        String query = "SELECT USER_ID, USERNAME, SHIPPING_ADDRESS, BILLING_ADDRESS, CONTACT_NUM FROM USERS";
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
        } catch (SQLException ex)
        {ex.printStackTrace();}
        return null;
    }
    
    public String[] showUserInfo(String user){
        String query = "SELECT * FROM USERS WHERE username = ?";
        try(PreparedStatement ps = con.prepareStatement(query)){
            ps.setString(1, user);
            try(ResultSet rs = ps.executeQuery()){
                ArrayList<String> arrTemp = new ArrayList<String>();
                while(rs.next()){
                    arrTemp.add(rs.getString("User_ID")); // 1
                    arrTemp.add(rs.getString("username"));
                    arrTemp.add(rs.getString("first_name"));
                    arrTemp.add(rs.getString("last_name"));
                    arrTemp.add(rs.getString("shipping_address"));
                    arrTemp.add(rs.getString("billing_address"));
                    arrTemp.add(Safe.decrypt(rs.getString("password"))); // 8
                    arrTemp.add(rs.getString("email"));
                    arrTemp.add(rs.getString("contact_num"));
                }
                String[] arr = new String[arrTemp.size()];
                arr = arrTemp.toArray(arr);
                return arr;
            }
        } catch (SQLException ex)
        {ex.printStackTrace();}
        return null;
    }
        
    public int getUserID(String user){
       String query = "SELECT user_id FROM USERS WHERE USERNAME = ? OR email = ?";
       try(PreparedStatement ps = con.prepareStatement(query)){
            ps.setString(1, user);
            ps.setString(2, user);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next())
                    return rs.getInt("user_id");
                else
                    return 0;
            }
        } catch (SQLException ex)
        {ex.printStackTrace();}
        return 0;
    }
    
    public String getUserName(int num){
       String query = "SELECT username FROM USERS WHERE user_id= ?";
       try(PreparedStatement ps = con.prepareStatement(query)){
            ps.setInt(1, num);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next())
                    return rs.getString("Username");
                else
                    return "";
            }
        } catch (SQLException ex)
        {ex.printStackTrace();}
        return null;
    }
    
    public String getFirst_LastName(int num){
       String query = "SELECT First_name, Last_Name FROM USERS WHERE user_id= ?";
       try(PreparedStatement ps = con.prepareStatement(query)){
            ps.setInt(1, num);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next())
                    return rs.getString("First_name")+" "+rs.getString("Last_name");
                else
                    return "";
            }
        } catch (SQLException ex)
        {ex.printStackTrace();}
        return null;
    }
    
    public String getFirst_LastName(String userNameOrEmail){
       String query = "SELECT First_name, Last_Name FROM USERS WHERE username= ? OR email =?";
       try(PreparedStatement ps = con.prepareStatement(query)){
            ps.setString(1, userNameOrEmail);
            ps.setString(2, userNameOrEmail);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next())
                    return rs.getString("First_name")+" "+rs.getString("Last_name");
                else
                    return "";
            }
        } catch (SQLException ex)
        {ex.printStackTrace();}
        return null;
    }
    
    public String getUserName(String emailOrUser){
       String query = "SELECT username FROM USERS WHERE email= ? or username = ?";
       try(PreparedStatement ps = con.prepareStatement(query)){
            ps.setString(1, emailOrUser);
            ps.setString(2, emailOrUser);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next())
                    return rs.getString("Username");
                else
                    return "";
            }
        } catch (SQLException ex)
        {ex.printStackTrace();}
        return null;
    }
    
    public String getEmail(String userOrEmail){
       String query = "SELECT email FROM USERS WHERE USERNAME = ? or EMAIL = ?";
       try(PreparedStatement ps = con.prepareStatement(query)){
            ps.setString(1, userOrEmail);
            ps.setString(2, userOrEmail);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next())
                    return rs.getString("email");
                else
                    return "";
            }
        } catch (SQLException ex)
        {ex.printStackTrace();}
        return null;
    }
    
    public String getEmail(int num){
       String query = "SELECT email FROM USERS WHERE user_id= ?";
       try(PreparedStatement ps = con.prepareStatement(query)){
            ps.setInt(1, num);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next())
                    return rs.getString("email");
                else
                    return "";
            }
        } catch (SQLException ex)
        {ex.printStackTrace();}
        return null;
    }
    
    public String getPassWord(String userOrEmail){
        String query = "SELECT password FROM USERS WHERE USERNAME = ? OR EMAIL =?";
        try(PreparedStatement ps = con.prepareStatement(query)){
            ps.setString(1, userOrEmail);
            ps.setString(2, userOrEmail);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next())
                    return Safe.decrypt(rs.getString("Password"));
                else
                    return "";
            }
        } catch (SQLException ex)
         {ex.printStackTrace();}
        return null;
    }

    public boolean checkIfAdmin(String user, String pass, int id){
        String query = "SELECT admin_id, username, password FROM ADMINS WHERE USERNAME = ? AND password = ? AND admin_id = ?";
        try(PreparedStatement ps = con.prepareStatement(query)){
            ps.setString(1, user);
            ps.setString(2, Safe.encrypt(pass));
            ps.setInt(3, id);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next())
                    return true;
                else
                    return false;
            }
        } catch (SQLException ex)
         {ex.printStackTrace();}
        return false;
    }
    
    public int getAdminID(String user){
        String query = "SELECT admin_id FROM ADMINS WHERE USERNAME = ?";
        try(PreparedStatement ps = con.prepareStatement(query)){
            ps.setString(1, user);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next())
                    return rs.getInt("Admin_ID");
                else
                    return 0;
            }
        } catch (SQLException ex)
         {ex.printStackTrace();}
        return 0;
    }
    // end of Select Queries
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="AccessUsers DMLQueries">    
    // DML Queries
    public void insertUser(String[] userInfo){
        String query = 
        "INSERT INTO USERS "
        + "(username, first_name, last_name, shipping_address, billing_address, password, email, contact_num) "
        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try(PreparedStatement ps = con.prepareStatement(query)){
            int cnt = 1;
            for(String info : userInfo){
                if(cnt == 6)
                    ps.setString(6, Safe.encrypt(info));
                else
                    ps.setString(cnt, info);
                cnt++;
            }
            ps.executeUpdate();
        } catch (SQLException ex)
         {ex.printStackTrace();}
    }
    
    public void deleteUser(String user){
        String query ="DELETE FROM USERS WHERE username = ?";
        try(PreparedStatement ps = con.prepareStatement(query)){
            ps.setString(1, user);
            ps.executeUpdate();
        } catch (SQLException ex)
         {ex.printStackTrace();}
    }
    
     
    public void updateName(String lastName, String firstName, String userName, String user){
        String query =
                "UPDATE USERS "
                + "SET Username = ?, Last_Name = ?, First_Name = ? "
                + "WHERE username = ?";
        try(PreparedStatement ps = con.prepareStatement(query)){
            ps.setString(1, userName);
            ps.setString(2, lastName);
            ps.setString(3, firstName);
            ps.setString(4, user);
            ps.executeUpdate();
        } catch (SQLException ex)
         {ex.printStackTrace();}
    }
    
    public void updatePassWord(String newPass, String user){
        String query =
                "UPDATE USERS "
                + "SET PASSWORD = ? "
                + "WHERE USERNAME = ?";
        try(PreparedStatement ps = con.prepareStatement(query)){
            ps.setString(1, Safe.encrypt(newPass));
            ps.setString(2, user);
            ps.executeUpdate();
        } catch (SQLException ex)
         {ex.printStackTrace();}
    }
    public void updateInfo(String[] newInfo, String user){
        String query =
                "UPDATE USERS "
                + "SET Shipping_Address = ?, Billing_Address = ?, Email = ?, Contact_Num = ? "
                + "WHERE username = ?";
        try(PreparedStatement ps = con.prepareStatement(query)){
            int cnt = 1;
            for(String info : newInfo){
                ps.setString(cnt, info);
                cnt++;
            }
            ps.setString(5, user);
            ps.executeUpdate();
        } catch (SQLException ex)
         {ex.printStackTrace();}
    }
    
    
    // added methods updateUserName(), updateName(), updatePassWord(), updateInfo()
    public void updateUserName(String username, String user){
            String query =
                    "UPDATE USERS "
                    + "SET username = ?"
                    + "WHERE username = ?";
            try(PreparedStatement ps = con.prepareStatement(query)){
                ps.setString(1, username);
                ps.setString(2, user);
                ps.executeUpdate();
            } catch (SQLException ex)
             {ex.printStackTrace();}
        }

    public void updateName(String lastName, String firstName, String user){
            String query =
                    "UPDATE USERS "
                    + "SET Last_Name = ?, First_Name = ? "
                    + "WHERE username = ?";
            try(PreparedStatement ps = con.prepareStatement(query)){
                ps.setString(1, lastName);
                ps.setString(2, firstName);
                ps.setString(3, user);
                ps.executeUpdate();
            } catch (SQLException ex)
             {ex.printStackTrace();}
        }

    //end of DML Queries
    //</editor-fold>
}
