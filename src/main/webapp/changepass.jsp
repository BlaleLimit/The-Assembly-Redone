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
        <div id="profile-box">
            <form method="POST" action="ProfileServlet?changePass">
                <br><label for="current-box">Current Password: </label>
                <input id="current-box" type="password" name="currentPass" placeholder="Current Password" autocomplete="off" required>
                <br><label for="new-box">New Password: </label>
                <input id="new-box" minlength="4" maxlength="20" type="password" name="newPass" placeholder="New Password" autocomplete="off" required>
                <br><label for="confirm-box">Confirm Password: </label>
                <input id="confirm-box" minlength="4" maxlength="20" type="password" name="confirmPass" placeholder="Confirm Password" autocomplete="off" required>
                <br><button type="submit" value="Change Password" id="change-password">Change Password</button>
            </form>
            <br>
        </div>
        </body>

</html>