<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="Css/helppage.css">
        <link rel="stylesheet" href="Css/generalCss.css">
        <title>Help</title>
    </head>
    <body>
        <jsp:include page="/HeadFoot/navBar.jsp"/>
        <div class="login-div">
            <div class="">
                <img src="Logos/logo.png" alt="" width="300">
            </div>
            <div class="form">
                <div class="title">Name</div>
                <div class="name">
                    <input type="text" placeholder="">
                </div>
                <div class="title">Order Number</div>
                <div class="orderid">
                    <input type="text" placeholder="">
                </div>
                <div class="title">Problem Description</div>
                <div class="problem">
                    <textarea id="" name="wissues" rows="25" cols="130">
                        
                    </textarea>
                </div>
            </div>

            <button type="submit" class="submit-button">SUBMIT</button>
        </div>
        
    </body>

</html>
