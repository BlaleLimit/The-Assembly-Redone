<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="Css/homepage.css">
        <link rel="stylesheet" href="Css/generalCss.css">
        <title>The Computer part store - TheAssemblyPC.com</title>
        <%
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control","must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
        %>
    </head>
    <body>
        <jsp:include page="/HeadFoot/navBar.jsp"/>
        <div class="centerHeader">
            <div class="header">
                <center><h1>Welcome to</h1></center>
                <center><img src="Logos/LogoWhite.png"></center>
            </div>
        </div>
        <div class="productContainer">
            <form action="SearchServlet" method="get">
            <table border = "1" width="500px">
                <tr>
                    <td colspan="2" class="tr1">
                        <img src="inventory/GEFORCE RTX 3090.jpg" class="specialImg">
                        <div class="bottom">
                            <h4>GEFORCE RTX 3090</h4>
                            <button type="submit"  name="item" value ="GEFORCE RTX 3090">ADD TO CART</button>
                        </div>
                    </td>
                    <td>
                        <img src="inventory/Corsair ML120 PRO.jpg">
                        <div class="bottom">
                            <h4>Corsair ML120 PRO</h4>
                            <button type="submit"  name="item" value ="Corsair ML120 PRO">ADD TO CART</button>
                        </div>
                    </td>
                    <td>
                        <img src="inventory/MSI MAG Forge 100R.jpg">
                        <div class="bottom">
                            <h4>MSI MAG Forge 100R</h4>
                            <button type="submit"  name="item" value ="MSI MAG Forge 100R">ADD TO CART</button>
                        </div>
                    </td>
                </tr>
                <tr class="tr">
                    <td>
                        <img src="inventory/ROG Ryuo 240.jpg">
                        <div class="bottom">
                            <h4>ROG Ryuo 240</h4>
                            <button type="submit"  name="item" value ="ROG Ryuo 240">ADD TO CART</button>
                        </div>
                    </td>
                    <td>
                        <img src="inventory/Asus ROG Strix Fusion 500.jpg">
                        <div class="bottom">
                            <h4>Asus ROG Strix Fusion 500</h4>
                            <button type="submit"  name="item" value ="Asus ROG Strix Fusion 500">ADD TO CART</button>
                        </div>    
                    </td>
                    <td>
                        <img src="inventory/Ducky One 2 SF.jpg">
                        <div class="bottom">
                            <h4>Ducky One 2 SF</h4>
                            <button type="submit"  name="item" value ="Ducky One 2 SF">ADD TO CART</button>
                        </div>    
                    </td>
                    <td>
                        <img src="inventory/MSI Gaming Pro GF75 Thin 9SC-074PH.jpg">
                        <div class="bottom">
                            <h4>MSI Gaming Pro GF75 Thin 9SC-074PH</h4>
                            <button type="submit"  name="item" value ="MSI Gaming Pro GF75 Thin 9SC-074PH">ADD TO CART</button>
                        </div>
                    </td>
                </tr>
                <tr class="tr">
                    <td>
                        <img src="inventory/Corsair Glaive.jpg">
                        <div class="bottom">
                            <h4>Corsair Glaive</h4>
                            <button type="submit"  name="item" value ="Corsair Glaive">ADD TO CART</button>
                        </div>    
                    </td>
                    <td>
                        <img src="inventory/MSI OPTIX G241VC.jpg">
                        <div class="bottom">
                            <h4>MSI OPTIX G241VC</h4>
                            <button type="submit"  name="item" value ="MSI OPTIX G241VC">ADD TO CART</button>
                        </div>
                    </td>
                    <td>
                        <img src="inventory/ASROCK B550M Steel Legend.jpg">
                        <div class="bottom">
                            <h4>ASROCK B550M Steel Legend</h4>
                            <button type="submit"  name="item" value ="ASROCK B550M Steel Legend">ADD TO CART</button>
                        </div>
                    </td>
                    <td>
                        <img src="inventory/Corsair MM200.jpg">
                        <div class="bottom">
                            <h4>Corsair MM200</h4>
                            <button type="submit"  name="item" value ="Corsair MM200">ADD TO CART</button>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <img src="inventory/MSI MPG A750GF.jpg">
                        <div class="bottom">
                            <h4>MSI MPG A750GF</h4>
                            <button type="submit"  name="item" value ="MSI MPG A750GF">ADD TO CART</button>
                        </div>
                    </td>
                    <td>
                        <img src="inventory/Ryzen 5 5600X.jpg">
                        <div class="bottom">
                            <h4>Ryzen 5 5600X</h4>
                            <button type="submit"  name="item" value ="Ryzen 5 5600X">ADD TO CART</button>
                        </div>
                    </td>
                    <td>
                        <img src="inventory/Corsair Vengeance RGB PRO 16GB.jpg">
                        <div class="bottom">
                            <h4>Corsair Vengeance RGB PRO 16GB</h4>
                            <button type="submit"  name="item" value ="Corsair Vengeance RGB PRO 16GB">ADD TO CART</button>
                        </div>
                    </td>
                    <td>
                        <img src="inventory/Seagate BarraCuda 120 250GB.jpg">
                        <div class="bottom">
                            <h4>Seagate BarraCuda 120 250GB</h4>
                            <button type="submit" name="item" value ="Seagate BarraCuda 120 250GB">ADD TO CART</button>
                        </div>
                    </td>
                </tr>
            </table>
            </form>
        </div>
        <footer>
            <jsp:include page="/HeadFoot/footer.jsp"/>
        </footer>
    </body>
</html>
