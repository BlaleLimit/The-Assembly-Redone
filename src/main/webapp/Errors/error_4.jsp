<%-- 
    Document   : Error404
    Created on : 02 28, 21, 12:53:22 AM
    Author     : Haffiny-nardrei
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Page not found</title>
    </head>
    <body style="margin: 0;">
        <jsp:include page="/HeadFoot/navBar.jsp"/>
        
        <img src="" class='centerimg'>
        
        <div style="margin-top: 10%">
            <h1 style = 'text-align: center;'>
                [error 404 Page not found]<br><br><br>
                
                Please click the button below or use the navigation bar on the top<br><br>
                
                Have fun shopping :)
            </h1>
            <form action='homepage.jsp'>
                <center><button type = 'submit'> Go back to home page</button></center>
            </form>
        </div>    
    </body>
    <br>
    <footer class='center' style='text-align: center;'>
        
    </footer>
</html>
