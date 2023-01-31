package Assembly.Admin.Controllers;

import Assembly.Database.AccessUsers;
import Assembly.Database.AssemblyDB;

import java.io.IOException;

import java.sql.Connection;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UsersServlet extends HttpServlet {

    // variables
    String[] results;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         try(Connection con = AssemblyDB.connect()){
            AccessUsers au = new AccessUsers(con);
       
            results = au.showUserInfo();
            
            if (results != null){
                request.setAttribute("Results", results);
                request.getRequestDispatcher("Admin/userspage.jsp").forward(request,response); 
            }
            else{
                response.sendRedirect("Admin/admin.jsp");
            }
            au.close();  
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        finally{}
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
