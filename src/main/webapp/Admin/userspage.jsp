<%@page import="java.util.Arrays"%>
<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="Css/admin.css">
        <title>Users Page</title>
        <% 
                response.setHeader("Expires", "0");
                response.setHeader("Cache-Control","must-revalidate, post-check=0, pre-check=0");
                response.setHeader("Pragma", "public");
        %>
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
            <a href="ProductsServlet" class="textLink">Manage Products</a>
            <a href="Admin/admin.jsp" class="textLink">Admin Page</a>
        </div>
        <br><br><h1 align="center">
            List of Users:  
        </h1>        
	<table border="1" align="center">
            <tr>
                <th>User_ID</th>
                <th>Username</th>
                <th>Shipping Address</th>
                <th>Billing Address</th>
                <th>Contact Number</th>
            </tr>
        <%
            String results[] = (String[]) request.getAttribute("Results");
   
            for (int i = 0; i < results.length - 1; i += 5)
            {
        %>      
                <tr>
                   
                    <td><center><%= results[i] %></center></td>                 <!-- User_id-->
                    <td><center><%= results[i + 1] %></center></td>             <!-- Username-->
                    <td><center><%= results[i + 2] %></center></td>             <!-- Shipping address-->
                    <td><center><%= results[i + 3] %></center></td>             <!-- Billing address-->
                    <td><center><%= results[i + 4] %></center></td>          <!-- Contact number-->
                </tr>           
        <% } %> 
        </table>
    </body>
</html>