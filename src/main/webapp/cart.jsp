<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="Assembly.Errors.SessionException"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="Css/cart.css">
    <link rel="stylesheet" href="Css/generalCss.css">
    <%
        String name = (String)session.getAttribute("user");
        if(name == null){
            name = "";
        }else{
           out.print("<title>" + name + "'s cart" + "</title>"); 
        }
        if(session.getAttribute("userID") == null){
            request.setAttribute("msg", "Login right now and start shopping!");
            throw new SessionException();
        }
    %>
</head>
<body>
    <jsp:include page="/HeadFoot/navBar.jsp"/>
    <div class="small-container">
        <table border="1">
            <tr>
                <th>Product</th>
                <th>Information</th>
                <th>Quantity</th>
                <th>Subtotal(in Php)</th>
            </tr>
            <%
                response.setHeader("Expires", "0");
                response.setHeader("Cache-Control","must-revalidate, post-check=0, pre-check=0");
                response.setHeader("Pragma", "public");
                
                if(session.getAttribute("userID") == null){
                    request.setAttribute("msg", "Your session has expired, please login again");
                    throw new SessionException();
                }
                ArrayList<String> itemName = (ArrayList)session.getAttribute("itemName");
                ArrayList<Double> itemPrice = (ArrayList)session.getAttribute("itemPrice");
                ArrayList<Integer> itemQuan = (ArrayList)session.getAttribute("itemQuantity");
                ArrayList<Integer> itemStock = (ArrayList)session.getAttribute("itemStock");
                Iterator<String> iter = itemName.iterator();
                Iterator<Double> iter2 = itemPrice.iterator();
                Iterator<Integer> iter3 = itemQuan.iterator();
                Iterator<Integer> iter4 = itemStock.iterator();
           
                double total = 0;
                DecimalFormat df = new DecimalFormat("###,##0.00");
                
                while(iter.hasNext()){
                    String itemNameF = iter.next();
                    double price = iter2.next().intValue();
                    int quan = iter3.next().intValue();
                    int stock = iter4.next().intValue();
                    double subTotal = price*quan; 
            %>
            <tr>
                <td>
                    <center><img src= "<%="inventory/"+itemNameF+".jpg"%>"></center>
                </td>
                <td>
                    <div class="cart-info">
                        <div>
                            <p><%= itemNameF %></p>
                            <small>Item Price: <%=df.format(price)%></small>
                            <br>
                            <small>Current Stock left: <%=stock%></small>
                            <br><br>
                            <form action= "AddToCartServlet" method="get">
                                <input type="hidden" name="remove" value= "<%=itemNameF%>">
                                <button type="submit">REMOVE ITEM</button>
                            </form>
                        </div>
                    </div>
                </td>
                    <td>
                        <form action="AddToCartServlet" method="get">
                            <center><p>Current Quantity in Cart: [<%= quan %>]</p></center>
                            <center><input type="number" name="quantity" min="1"  max="<%= stock %>" value="<%= quan %>"></center>
                            <input type="hidden" name="item" value="<%=itemNameF%>">
                            <br>       
                            <center><button type="submit">Update</button></center>
                        </form>
                    </td> 
                <td><center> <%= df.format(subTotal) +" Php" %> </center></td> 
            </tr>
            <%total += subTotal;}%>
        </table>  
    </div>
    <%
       if(total < 1)
            out.print("<center><h1 style=\"padding-top: 30px;\">" + "Shop now and add items to your cart! :)" + "</h1></center>");
       
       String msg = (String)request.getAttribute("msg");
       if(msg != null && !msg.isEmpty() && total > 0)
            out.print("<center><h1 style=\"padding-top: 30px;\">" + msg + "</h1></center>");
    %>
    
    <div class="total-price" >
        
        <form action="InvoiceServlet" method = "get">
        <table border="15" style="background: white; width: 100%;">
            <tr>
                <td colspan="2">TOTAL</td>
                <%out.print("<td colspan=\"2\">"+ df.format(total) +" Php"+"</td>");%>
            </tr>
            <%if(total > 0){%>
            <tr>
                <td colspan="2"> 
                    <label for="Cash">Pay With Cash</label>
                    <input type="radio" id="" name="paymentMethod" value="Cash" required>
                </td>
                <td colspan="2"> 
                    <label for="Cashless">Cashless transaction</label>
                    <input type="radio" id="" name="paymentMethod" value="Cashless" required>
                </td>
            </tr>
            <tr>
                <td colspan="4">
                    <center><p>Enter discount code(if applicable):</center>
                </td>
            </tr>
            <tr>
                <td colspan="4">
                    <center>
                        <input type="text" style="width: 70%;" name="discCode" autocomplete="off">
                    </center>
                </td>
            </tr>
            
                <tr>
                    <td colspan="4">
                        <p>Click below to pay out(make sure to press update and double check before continuing):</p>
                    </td>   
                </tr>
                <tr>
                    <td colspan="4">
                        <center><button type ="submit">Check out</button></center>
                    </td>
                </tr>
            <%}else{%>
                <tr>
                    <td colspan="4">
                        <p>Add Items so you could check out, have fun shopping!</p>
                    </td>   
                </tr>
            <%}%>
        </table>
        </form>
    </div>
</body>
</html>
<script>
window.onload = function() {
    if(!window.location.hash) {
            window.location = window.location + '#loaded';
            window.location.reload();
    }
};
</script>
 