/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Assembly.Controllers;

import Assembly.Database.AccessCart;
import Assembly.Database.AccessInventory;
import Assembly.Database.AssemblyDB;
import Assembly.Errors.SessionException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AddToCartServlet extends HttpServlet {
    
    int userId;
    int prodId;
    int quantity;
    int price; 
    int model_ID;
    ArrayList<String> itemName;
    ArrayList<Double> itemPrice;
    ArrayList<Integer> itemQuantity;
    ArrayList<Integer> itemStock;
    ArrayList<Integer> itemsCart;
    HttpSession session;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException{
        session = request.getSession();
        if(session.getAttribute("userID") != null){
            
            try(Connection con = AssemblyDB.connect()){
                AccessCart ac = new AccessCart(con);
                AccessInventory ai = new AccessInventory(con);
                userId = (int)session.getAttribute("userID");
                ac.setShopperID(userId);
                
                // if remove button was pressed, remove item from database 
                if(request.getParameter("remove") != null && !request.getParameter("remove").isEmpty()){
                    ac.deleteItem(ai.getProductID((request.getParameter("remove"))));
                }
                //adjusts the quantity of the item
                if(request.getParameter("item") != null && !request.getParameter("item").isEmpty()){
                    quantity = Integer.parseInt(request.getParameter("quantity"));
                    model_ID = ai.getProductID(request.getParameter("item"));
                    ac.addAmountToItem(model_ID, quantity);
                }
                ResultSet rs = ac.showCart();
                itemName = new ArrayList<>();
                itemPrice = new ArrayList<>();
                itemQuantity = new ArrayList<>();
                itemStock = new ArrayList<>();
                itemsCart= new ArrayList<>();
                 
                while(rs.next()){
                    prodId = rs.getInt("product_id");
                    itemsCart.add(prodId);
                    itemName.add(ai.getProductName(prodId));
                    itemPrice.add((double)ai.getPrice(prodId));
                    itemStock.add(ai.getProductStock(prodId));
                    itemQuantity.add(rs.getInt("quantity"));
                }
                session.setAttribute("prodIdCart" , itemsCart);
                session.setAttribute("itemName" , itemName);
                session.setAttribute("itemPrice" , itemPrice);
                session.setAttribute("itemStock" , itemStock);
                session.setAttribute("itemQuantity" , itemQuantity);
                request.getRequestDispatcher("cart.jsp").forward(request, response);
                
                rs.close();
                ai.close();
                ac.close();
            } catch (SQLException ex) 
            {ex.printStackTrace();}
        }else{
            request.setAttribute("msg", "Login right now and start shopping!");
            session.invalidate();
            throw new SessionException();
        }
        
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
