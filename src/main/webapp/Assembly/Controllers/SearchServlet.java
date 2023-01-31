package Assembly.Controllers;

import Assembly.Database.AccessCart;
import Assembly.Database.AccessInventory;
import Assembly.Database.AssemblyDB;
import Assembly.Errors.SessionException;

import java.io.IOException;

import java.sql.Connection;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class SearchServlet extends HttpServlet {

    // variables
    private String searchParam;
    
    private String[] resultParam;
    private int userId;
    private String searchSaved;
    private String item;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
             
        try(Connection con = AssemblyDB.connect()){
            AccessInventory ai = new AccessInventory(con);
            AccessCart ac = new AccessCart(con);
            HttpSession session = request.getSession();
            searchParam = request.getParameter("Search");
            item = request.getParameter("item");
            
            /**
            *    if the add to cart button was clicked and user is logged in, proceed with adding
            *    item to cart, otherwise throw SessionException() and redirect to login
            */  
            if(request.getParameter("addItem") != null && !request.getParameter("addItem").isEmpty()){
                if(session.getAttribute("userID") == null){
                    request.setAttribute("msg", "Login right now and start shopping!");
                    session.invalidate();
                    throw new SessionException();
                }else{
                    userId = (int)session.getAttribute("userID");
                    ac.setShopperID(userId);
                    ac.addItem(ai.getProductID(request.getParameter("addItem")));
                    searchSaved = request.getParameter("savedSearch");
                    
                }
            }
            if(item != null && !item.isEmpty()){
                searchParam = item;
            }    
            
            if(searchSaved != null && searchParam == null){
                searchParam = searchSaved;
            }
            
            
            if(searchParam == null || searchParam.isEmpty())
                resultParam = ai.getSearchResult();
            else
                resultParam = ai.getSearchResult(searchParam);
            
            request.setAttribute("Results", resultParam);
            request.getRequestDispatcher("searchpage.jsp").forward(request,response);
            
            ac.close();
            ai.close();
        }
        catch(SQLException e)
        {e.printStackTrace();}
        finally{}
    }
    
    public static boolean isNull(String word)
    {return word == null && word.isEmpty();}

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
