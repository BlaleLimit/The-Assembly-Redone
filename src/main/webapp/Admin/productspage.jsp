<%@page import="java.sql.SQLException"%>
<%@page import="Assembly.Database.AssemblyDB"%>
<%@page import="java.sql.Connection"%>
<%@page import="Assembly.Database.AccessInventory"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="Css/admin.css">
        <title>List of Products Page</title>
    </head>
    <body>
         <div class="topnav">
            <%
                String admin = (String)session.getAttribute("admin");

                if(admin != null){
                    out.print("<a class=\"active textLink\" href=\"LogoutServlet\" "
                            + "onClick = \"alert('You have successfully logged out, Admin "+ admin +"')\"> Log out </a>");
                    out.print("<a href=\"..//profile.jsp\" class=\"textLink\" onclick=\"return false;\">Logged in: ["+ admin +"]</a>");
                }else{
                    out.print("<a class=\"active textLink\" href=\"..//loginpage.jsp\"> Login/Sign up </a>");
                } 
            %>
            <a href="UsersServlet" class="textLink">Manage Users</a>
            <a href="Admin/admin.jsp" class="textLink">Admin Page</a>
        </div>
        <br><br><h1 align="center">
            List of Products:  
        </h1>
            <%
                String msg = (String)request.getAttribute("msg");
                if(msg == null)
                    msg = "";
                out.print("<h3><center>" + msg + "</center></h3>");
            %>
        <br><br>
        <div class="search-container">
            <form action="ProductsServlet">
                <input type="text" placeholder="Search.." name="Search" id="search-field"></input>
                <button value="Search" type="submit">SEARCH / SHOW ALL</button>
            </form>
        </div>
	<table border="1" align="center">
            <tr>
                <th>Model Name</th>
                <th>Product Type</th>
                <th>Price (Php)</th>
                <th>Stock</th>
                <th>Description</th>
            </tr>
            
            <tr>
                <form action="ItemServlet?addItem" method="POST">
                    <td>
                        <center><input type="text" name="addProd" id="add-item" required></center>
                        <center><button type="submit">ADD PRODUCT</button></center>
                    </td>
                    <td><center><input type="text" name="addProdType" id="add-item" required></td></center>
                    <td><center><input type="text" name="addPrice" id="add-item" required></td></center>
                    <td><center><input type="text" name="addStock" id="add-item" required></td></center>
                    <td>
                        <center><textarea name="addDesc" id="add-item" rows="8" cols="50" required></textarea></center>
                    </td>
                </form>        
            </tr>
            
        <%
            DecimalFormat df = new DecimalFormat("###,##0.00");
            String results[] = (String[]) request.getAttribute("Results");
            
            for (int i = 0; i < results.length - 1; i += 5)
            {
        %>      
                <tr>
                    <td>
                        <center><%= results[i] %></center>                                      <!-- Model name-->
                        <form action="ItemServlet?removeItem" method="POST">
                            <input type="hidden" name ="removeProd" value="<%= results[i] %>">
                            <center><button type="submit">REMOVE ITEM</button></center>
                        </form>         
                    </td>                                                                    
                    <td><center><%= results[i + 1] %></center></td>                             <!-- Product type-->
                    <td>
                        <center><%= df.format(Double.parseDouble(results[i + 2]))%></center>    <!-- Price (in Php)-->
                        <form action="ItemServlet?updatePrice" method="POST">
                            <input type="hidden" name ="prod" value="<%= results[i] %>">
                            <center><input type="text" name="price" required></center>
                            <center><button type="submit">UPDATE PRICE</button>
                        </form>   
                    </td>
                    <td>
                        <center><%= results[i + 3] %></center>                                  <!-- stock-->
                        <form action="ItemServlet?updateStock" method="POST">
                            <input type="hidden" name ="prod" value="<%= results[i] %>">
                            <center><input type="number" name="quantity" min="1"  max="100" value="<%= results[i + 3] %>" required></center>
                            <center><button type="submit">UPDATE STOCK</button></center>
                        </form>
                    </td>                     
                    <td><%= results[i + 4].replaceAll(";", "<br>") %></td>                          <!-- desc-->                          
                </tr>           
        <% } %> 
        </table>
    </body>
</html>