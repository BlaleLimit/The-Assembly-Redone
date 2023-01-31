package Assembly.Controllers;

import Assembly.Database.AssemblyDB;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import Assembly.Database.AccessUsers;
import Assembly.Errors.AuthenticationException;

public class LoginServlet extends HttpServlet 
{
    // Variables
    private static String userParam, passParam, 
            userTemp, userEmail, passTemp;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException{

        try(Connection con = AssemblyDB.connect()){
            AccessUsers au = new AccessUsers(con);
            
            userParam = request.getParameter("UserOrEmail");
            passParam = request.getParameter("Pass");
            
            userEmail = au.getEmail(userParam);   //gets email from db if it exists
            userTemp = au.getUserName(userParam); //gets username from db if it exists
            passTemp = au.getPassWord(userParam); //gets password from db if it exists
            final int ID = au.getAdminID(userParam); 
                     
            if(au.checkIfAdmin(userParam, passParam, ID)){
                // checks if is admin, if so redirect to AdminPage
                HttpSession sessAdmin = request.getSession();
                sessAdmin.setAttribute("ID", ID);
                sessAdmin.setAttribute("admin", userParam);
                response.sendRedirect("Admin/admin.jsp"); 
                
            }else if(correctLogin(userTemp, userParam, passParam, passTemp) || 
                     correctLogin(userEmail, userParam, passParam, passTemp)){
                //creates session object
                HttpSession session = request.getSession();
                session.setAttribute("user", userTemp);
                session.setAttribute("userID", au.getUserID(userTemp));
                response.sendRedirect("homepage.jsp");
            }else{
                request.setAttribute("msg", "Incorrect Username/Password");
                throw new AuthenticationException();
            }
            au.close();
        }catch(SQLException e)
        {e.printStackTrace();}
        finally{}
    }
    
    public static boolean isNull(String word){
        return word == null && word.isEmpty();
    }
    
    public static boolean correctLogin(String userTemp, String userParam, String passParam, String passTemp){
        return (userTemp.equals(userParam) && !isNull(userParam)) && 
               (passTemp.equals(passParam) && !isNull(passParam));
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
            throws ServletException, IOException{
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
            throws ServletException, IOException{
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
