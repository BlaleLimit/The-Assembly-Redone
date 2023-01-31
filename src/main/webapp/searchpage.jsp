<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="Css/searchpage.css">
        <link rel="stylesheet" href="Css/generalCss.css">
        <title>Search Results Page</title>
    </head>
    <body>
        <jsp:include page="/HeadFoot/navBar.jsp"/>
        <br><br><h1 align="center">
            Search Results:  
            <% 
                response.setHeader("Expires", "0");
                response.setHeader("Cache-Control","must-revalidate, post-check=0, pre-check=0");
                response.setHeader("Pragma", "public");
                String search = (String)request.getParameter("Search");
                String savedSearch = (String)session.getAttribute("searchSaved");
                if((search == null || search.isEmpty())&& savedSearch != null)
                    search = savedSearch;
                
                if((search == null || search.isEmpty()) && (savedSearch == null || savedSearch.isEmpty()))
                   search = "";
 
                out.println("\""+search+"\"");
                
            %>
        </h1>
	<table border="1" align="center">
            <tr>
                <th>Image</th>
                <th>Model Name</th>
                <th>Product Type</th>
                <th>Price(Php)</th>
                <th>Stock</th>
                <th>Description</th>
            </tr>
        <%
            DecimalFormat df = new DecimalFormat("###,##0.00");
            String results[] = (String[]) request.getAttribute("Results");
            
                
            for (int i = 0; i < results.length - 1; i += 5)
            {
        %>      
                <tr>
                    <td><center><img src="<%= "inventory/" + results[i] + ".jpg" %>" height=100px alt="alt"></center></td>
                    <td>
                        <center>
                        <%= results[i] %>
                        <br><br>
                        <form action="SearchServlet" method="get">
                            <input type="hidden" name ="addItem" value="<%= results[i] %>">
                            <button type="submit">ADD TO CART</button>
                        </form>
                        </center>
                    </td>                                          <!-- Product type-->
                    <td><center><%= results[i + 1] %></center></td>                                      <!-- Product type-->
                    <td><center><%= df.format(Double.parseDouble(results[i + 2]))%></center></td>    <!-- Price (in Php)-->
                    <td><center><%= results[i + 3] %></center></td>                                      <!-- stock-->
                    <td><%= results[i + 4].replaceAll(";", "<br>") %></td>              <!-- desc-->
                </tr>           
        <% } %> 
        </table>
        
    </body>

</html>