<%-- 
    Document   : navBar
    Created on : 05 19, 21, 9:56:04 PM
    Author     : Haffiny-nardrei
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="Css/navbar.css">
        <link rel="stylesheet" href="Css/generalCss.css">
    </head>
    <%
        String uri = request.getRequestURI();
        String pageName = uri.substring(uri.lastIndexOf("/")+1);
    %>
    <div class="topnav">
        <a href="homepage.jsp" style="float:left"><img src="Logos/logo.png" alt=""></a>
        <%
            String user = (String)session.getAttribute("user");
            if(user != null) {
                out.print("<a class=\"active textLink\" href=\"LogoutServlet\" "
                        + "onClick = \"alert('You have successfully logged out, Thank you and shop again! :)')\"> Log out </a>");
                out.print("<a href=\"ProfileServlet?profile\" class=\"textLink\" >Logged in: ["+ user +"]</a>");
            } else {
                out.print("<a class=\"active textLink\" href=\"loginpage.jsp\"> Login/Sign up </a>");
            }
        %>
        <a href="AddToCartServlet" class="textLink">Shopping cart</a>
        <a href="helppage.jsp" class="textLink">Help</a>
        <div class="search-container">
            <form action="SearchServlet">
                <input type="search" placeholder="Search.." name="Search" id="search-field">
                <input type="hidden"  name="saveSearch" value="<%= (String)session.getAttribute("searchSaved")%>">
                <button type="submit"><i class="fa fa-search"></i></button>
            </form>
        </div>
    </div>
</html>
