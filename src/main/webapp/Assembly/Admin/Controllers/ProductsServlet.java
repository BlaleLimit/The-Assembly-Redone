package Assembly.Admin.Controllers;

import Assembly.Database.AccessInventory;
import Assembly.Database.AssemblyDB;

import java.io.IOException;

import java.sql.Connection;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ProductsServlet extends HttpServlet {

    // variables
    private String searchParam;
    
    private String[] resultParam;
    private int adminID;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
             
        try(Connection con = AssemblyDB.connect()){
            AccessInventory ai = new AccessInventory(con);
            HttpSession session = request.getSession();
            
            searchParam = request.getParameter("Search");
            adminID = (int)session.getAttribute("ID");
             
            if(searchParam == null || searchParam.isEmpty())
                resultParam = ai.getSearchResult();
            else
                resultParam = ai.getSearchResult(searchParam);
            
            request.setAttribute("Results", resultParam);
            request.getRequestDispatcher("Admin/productspage.jsp").forward(request,response);
            ai.close();
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
