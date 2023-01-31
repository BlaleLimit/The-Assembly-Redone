<%-- 
    Document   : homepage
    Created on : 05 19, 21, 9:57:08 PM
    Author     : Haffiny-nardrei
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="../Css/admin.css">
        <title>The Computer part store - TheAssemblyPC.com</title>
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
                    out.print("<a class=\"active textLink\" href=\"..//LogoutServlet\" "
                            + "onClick = \"alert('You have successfully logged out, Admin "+ admin +"')\"> Log out </a>");
                    out.print("<a href=\"..//profile.jsp\" class=\"textLink\" onclick=\"return false;\">Logged in: ["+ admin +"]</a>");
                }else{
                    out.print("<a class=\"active textLink\" href=\"..//loginpage.jsp\"> Login/Sign up </a>");
                }
            %>
            <a href="../ProductsServlet" class="textLink">Manage Products</a>
            <a href="../UsersServlet" class="textLink">Manage Users</a>
            <%
                String msg = (String)request.getAttribute("msg");
                if(msg == null)
                    msg = "";
                out.print("<h3><center>" + msg + "</center></h3>");
            %>
        </div>
        
        <h2>Welcome Admin [<%= admin %>]!</h2>
        <img width="450px" src="../Logos/logo.png" alt="">
        
        <form action="../ReportServlet" method="get">
            <center><button type="submit">Generate Report</button></center>
        </form>
    </body>
</html>
