package Assembly.Controllers;

import Assembly.Database.AccessUsers;
import Assembly.Database.AssemblyDB;

import java.io.IOException;

import java.sql.Connection;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ProfileServlet extends HttpServlet {

    // variables
    private static String userParam, userID, userName, passParam, 
            passTemp, firstName, lastName, newPass,
            confirmPass, emailParam, shipAdd, billAdd, contact;
    
    private static String[] newInfo, userInfo;
            
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
        try (Connection con = AssemblyDB.connect()){
            AccessUsers au = new AccessUsers(con);
            
            HttpSession session = request.getSession();
            userParam = (String)session.getAttribute("user");
                 
           
            switch (request.getQueryString()) {
                case "changeUserName":
                    userName = request.getParameter("User");;
                    
                    if(userName.equals(au.getUserName(userName))){
                        userInfo = au.showUserInfo(userParam);
                        request.setAttribute("msg", "Username already exists");
                    }
                    else{
                        au.updateUserName(userName, userParam);
                        session.setAttribute("user", userName);
                        userParam = (String)session.getAttribute("user");
                    }
                    break;
                    
                case "changeName":
                    lastName = request.getParameter("Last");
                    firstName = request.getParameter("First");
                    
                    au.updateName(lastName, firstName, userParam);
                    break;
                    
                case "changePass":
                    passParam = request.getParameter("currentPass");
                    passTemp = au.getPassWord(userParam);
                    newPass = request.getParameter("newPass");
                    confirmPass = request.getParameter("confirmPass");
                    if (passTemp.equals(passParam) && newPass.equals(confirmPass)){
                        au.updatePassWord(newPass, userParam);
                    }
                    else{
                        userInfo = au.showUserInfo(userParam);
                        request.setAttribute("results", userInfo);
                        request.setAttribute("msg", "Failed to Change Password");
                    }
                    break;
                    
                case "changeInfo":
                    shipAdd = request.getParameter("ShipAdd");
                    billAdd = request.getParameter("BillAdd");
                    emailParam = request.getParameter("Email");
                    contact = request.getParameter("Number");
                    newInfo = new String[] {shipAdd, billAdd, emailParam, contact};
                    
                    au.updateInfo(newInfo, userParam);
                    break;
            
                default:
            }
            
            userInfo = au.showUserInfo(userParam);   
            request.setAttribute("results", userInfo);
            request.getRequestDispatcher("profile.jsp").forward(request, response);
            au.close();
         }catch(SQLException e)
        {e.printStackTrace();}
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
