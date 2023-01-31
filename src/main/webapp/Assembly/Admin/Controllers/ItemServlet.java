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

public class ItemServlet extends HttpServlet {
    
    // variables
    private String modelParam, priceParam, stockParam, prodParam, descParam, modelTemp;
    
    private int price, quantity;
    
    private String[] itemInfo;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
             
        try(Connection con = AssemblyDB.connect()){
            AccessInventory ai = new AccessInventory(con);
            
            switch(request.getQueryString()){
                case "addItem":            
                    modelParam = request.getParameter("addProd");
                    priceParam = request.getParameter("addPrice");
                    stockParam = request.getParameter("addStock");
                    prodParam = request.getParameter("addProdType");
                    descParam = request.getParameter("addDesc");

                    // checks if item exists
                    modelTemp = ai.getProductName(modelParam);

                    itemInfo = new String[] {modelParam, priceParam, stockParam, prodParam, descParam};

                    if (modelTemp.equals("") && itemInfo != null){
                        ai.addProduct(itemInfo);
                        request.setAttribute("msg", "Success on Adding Product");
                        request.getRequestDispatcher("ProductsServlet").forward(request, response);
                    }
                    else{
                        request.setAttribute("msg", "Failed to Add Product");
                        request.getRequestDispatcher("ProductsServlet").forward(request, response);
                    }
                    break;
                
                case "removeItem":
                    modelParam = request.getParameter("removeProd");
                    if (modelParam != null){
                        ai.removeProduct(modelParam);
                        request.setAttribute("msg", "Success on Removing Product");
                        request.getRequestDispatcher("ProductsServlet").forward(request, response);
                    }
                    else{
                        request.setAttribute("msg", "Failed to Remove Product");
                        request.getRequestDispatcher("ProductsServlet").forward(request, response);
                    }
                    break;
                    
                case "updatePrice":
                    modelParam = request.getParameter("prod");
                    priceParam = request.getParameter("price");
                    price = Integer.parseInt(priceParam);
                    if (modelParam != null && priceParam != null){
                        ai.updatePrice(price, modelParam);
                        request.setAttribute("msg", "Success on Updating Product Price");
                        request.getRequestDispatcher("ProductsServlet").forward(request, response);
                    }
                    else{
                        request.setAttribute("msg", "Failed to Update Product Price");
                        request.getRequestDispatcher("ProductsServlet").forward(request, response);
                    }
                    break;
                    
                case "updateStock":
                    modelParam = request.getParameter("prod");
                    stockParam = request.getParameter("quantity");
                    quantity = Integer.parseInt(stockParam);
                    if (modelParam != null && stockParam != null){
                        ai.addItemStock(quantity, modelParam);
                        request.setAttribute("msg", "Success on Updating Product Stock");
                        request.getRequestDispatcher("ProductsServlet").forward(request, response);
                    }
                    else{
                        request.setAttribute("msg", "Failed to Update Product Stock");
                        request.getRequestDispatcher("ProductsServlet").forward(request, response);
                    }
                    break;
               
                default:
                    response.sendRedirect("Admin/admin.jsp");
            }
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
