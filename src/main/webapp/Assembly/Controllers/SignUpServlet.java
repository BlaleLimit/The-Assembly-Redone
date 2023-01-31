package Assembly.Controllers;

import Assembly.Database.AssemblyDB;
import Assembly.Errors.CaptchaException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import Assembly.Errors.ConfirmPassException;
import Assembly.Errors.UserFoundException;

import Assembly.Database.AccessUsers;
import jakarta.servlet.http.HttpSession;
import nl.captcha.Captcha;

public class SignUpServlet extends HttpServlet{
    
    //  Variables
    private String userParam,passParam, userTemp, 
            confirmParam, captchaAns, emailParam,
            shipAdd, firstName, lastName,
            billAdd, emailTemp;
    
    String[] userInfo;
    AssemblyDB db;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException{ 

       try(Connection con = AssemblyDB.connect()) {
            AccessUsers au = new AccessUsers(con);
            
            //extracts data from the field
            userParam = request.getParameter("User");
            passParam = request.getParameter("Pass");
            emailParam = request.getParameter("Email");
            shipAdd = request.getParameter("ShipAdd");
            billAdd = request.getParameter("BillAdd");
            firstName = request.getParameter("First");
            lastName = request.getParameter("Last");
            confirmParam = request.getParameter("ConfirmPass");
            captchaAns = request.getParameter("captchaAns");
            
            // checks if username or email exists
            userTemp = au.getUserName(userParam);
            emailTemp = au.getEmail(emailParam);
            
            // creates session for the captcha
            HttpSession session = request.getSession();
            Captcha captcha = (Captcha)session.getAttribute("captcha");

            // errors: existing username, email, unmatched passwords, and wrong captcha
            if((userTemp.equals(userParam))){
                request.setAttribute("msg", "Username already exists");
                throw new UserFoundException();
            }
            if(emailTemp.equals(emailParam)){
                request.setAttribute("msg", "Email already exists");
                throw new UserFoundException();
            }
            if((!passParam.equals(confirmParam))){
                request.setAttribute("msg", "Passwords do not match");
                throw new ConfirmPassException();
            } 
            if(!captcha.isCorrect(captchaAns)){ 
                request.setAttribute("msg", "wrong captcha identification");
                throw new CaptchaException();
            }         
            //no error, proceed to code below
            
            //  executes query and returns the result()
            userInfo = new String[] {userParam, firstName, lastName, shipAdd, 
            billAdd, passParam, emailParam, ""};
            
            au.insertUser(userInfo);

            System.out.println("System added user ["+ userParam +"]");
            request.setAttribute("msg", "Signup successful, please login to proceed");
            request.getRequestDispatcher("loginpage.jsp").forward(request, response); 

        }catch(SQLException e)
        {e.printStackTrace();}   
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
