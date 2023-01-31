<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <%
        String user = (String)session.getAttribute("user");
    %>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="Css/profile.css">
        <link rel="stylesheet" href="Css/generalCss.css">
        <title><%= user %>'s Profile</title>
    </head>
    <body>
        <jsp:include page="/HeadFoot/navBar.jsp"/>
        <%
            String results[] = (String[])request.getAttribute("results");
        %>
            <div id="main-box">
                <h2>Profile Details for User [<%= user %>]:</h2>
            </div>
     
            <div id="profile-box">
                <%
                    String msg = (String)request.getAttribute("msg");
                    if(msg == null)
                        msg = "";
                    out.print("<h3><center>" + msg + "</center></h3>");
                %>
                <form method="POST" action="ProfileServlet?changeUserName">
                    <br><center><strong>Change Information:</strong></center>
                    <br><label for="name-box">Current Username: [<%= results[1] %>]</label>
                    <input id="name-box" value="<%= results[1] %>" type="text" name="User" 
                           minlength="4" maxlength="20" placeholder="New Username" autocomplete="off" required>
                    <br><button type="submit" value="Change Username">Change Username</button>    
                </form>
                <form method="POST" action="ProfileServlet?changeName">
                    <br><label for="first-box">First Name: [<%= results[2] %>]</label>
                    <input id="first-box" value="<%= results[2] %>" type="text" name="First" placeholder="New First Name" autocomplete="off" required>
                    <br><label for="last-box">Last Name: [<%= results[3] %>]</label>
                    <input id="last-box" value="<%= results[3] %>" type="text" name="Last" placeholder="New Last Name" autocomplete="off" required>
                    <br><button type="submit" value="Change Name">Change Name</button>              
                </form>
                <form method="POST" action="changepass.jsp">
                    <br><button type="submit" value="Change Password" id="change-password">Change Password</button><br>
                </form>
                <form method="POST" action="ProfileServlet?changeInfo">
                    <br><label for="ship-box">Shipping Address: [<%= results[4] %>]</label>
                    <input id="ship-box" value="<%= results[4] %>" type="text" name="ShipAdd" placeholder="New Shipping Address" autocomplete="off" required>
                    <br><label for="bill-box">Billing Address: [<%= results[5] %>]</label>
                    <input id="bill-box" value="<%= results[5] %>" type="text" name="BillAdd" placeholder="New Billing Address" autocomplete="off" required>
                    <br><label for="email-box">Email: [<%= results[7] %>]</label>
                    <input id="email-box" value="<%= results[7] %>" type="text" name="Email" placeholder="New Email" autocomplete="off" required>
                    <br><label for="contact-box">Contact Number: [<%= results[8] %>]</label>
                    <input id="contact-box" value="<%= results[8] %>" type="text" name="Number" placeholder="New Number" autocomplete="off" required>
                    <br><button type="submit" value="Change Info">Change Information</button>
                </form>
                <br>
            </div>
        </body>
   
</html>