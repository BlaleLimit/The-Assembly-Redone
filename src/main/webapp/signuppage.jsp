<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="Css/LoginSignup.css">
        <link rel="stylesheet" href="Css/generalCss.css">
    </head>
    <body>
        <jsp:include page="/HeadFoot/navBar.jsp"/>
        <div class="login-div">
            <form method = "post" action="SignUpServlet">
            <div class="logo">
                <img src="Logos/logo.png" alt="" width="300">
            </div>  
            <h2 class="title">Sign Up</h2>
                <%
                    response.setHeader("Expires", "0");
                    response.setHeader("Cache-Control","must-revalidate, post-check=0, pre-check=0");
                    response.setHeader("Pragma", "public");
                
                    String msg = (String)request.getAttribute("msg");
                    if(msg == null)
                        msg ="";
                    
                    out.println("<h2><center>" + msg + "</center></h2>");
                %>
               <div class="form">
                    <table class="signup-div">
                        <tr>
                            <td>
                                <div class="username">
                                    <input type="text" name="User" placeholder="Username" autocomplete="off" minlength="4" maxlength="20" required>
                                </div>
                            </td>
                            <td>
                            <div class="username">
                                <input type="email" name="Email" placeholder="Email Address" autocomplete="off" maxlength="50"required>
                            </div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="username">
                                    <input type="text" name="First" placeholder="First Name" autocomplete="off" maxlength = "40" required>
                                </div>
                            </td>
                            <td>
                            <div class="username">
                                <input type="text" name="Last" placeholder="Last Name" autocomplete="off" required maxlength = "40" >
                            </div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="username">
                                    <input type="text" name="ShipAdd" placeholder="Shipping Address" autocomplete="off" required>
                                </div>
                            </td>
                            <td>
                                <div class="username">
                                    <input type="text" name="BillAdd" placeholder="Billing Address" autocomplete="off" required>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="password">
                                    <input type="password" name="Pass" placeholder="Password" autocomplete="off" minlength="9" maxlength = "30" required>
                                </div>
                            </td>
                            <td>
                                <div class="password">
                                    <input type="password" name="ConfirmPass" placeholder="Confirm Password" autocomplete="off" minlength="9" maxlength = "30" required>
                                </div>
                            </td>
                        </tr>
                    </table>
                    <center><img src="Captcha.jpg"></center>
                    <div class="password">
                        <input type="text" name="captchaAns" placeholder="Captcha Answer" autocomplete="off" required>
                    </div>
                </div>
                <button type="submit" class="signup-button">SIGN UP</button>
            </form>
        </div>
    </body>

</html>
