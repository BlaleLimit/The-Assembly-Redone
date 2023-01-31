package Assembly.Controllers;

import static Assembly.Crypto.Safe.decrypt;
import java.sql.Connection;
import java.sql.SQLException;
import jakarta.servlet.http.HttpServlet;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AccessDB extends HttpServlet 
{
    public static void main(String args[])
    {
        try
        {
            String driver = "com.mysql.cj.jdbc.Driver";
            Class.forName(driver);
            System.out.println("LOADED DRIVER ---> " + driver);

            String url = "http://localhost:8080/TheAssemblyRedo";
            Connection con = DriverManager.getConnection(url, "root", "password");
            System.out.println("CONNECTION ESTABLISHED ---> ");
            
            String query = "SELECT * FROM USERS";
            PreparedStatement ps = con.prepareStatement(query);   
            
            try (ResultSet rs = ps.executeQuery())
            {
                while (rs.next())
                {
                    System.out.print(rs.getString(1) + "\t");
                    System.out.print(rs.getString(2) + "\t");
                    System.out.print(rs.getString(3) + "\t");
                    System.out.print(rs.getString(4) + "\t");
                    System.out.print(decrypt(rs.getString(7)) + "\t");
                    System.out.print(rs.getString(8) + "\t");
                    System.out.println();
                }
            }
            catch(Exception e){e.printStackTrace();}
        }
       
        catch (ClassNotFoundException | SQLException ex) 
        {
            ex.printStackTrace();
        }
    }    
}

