/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Assembly.Crypto;

import java.awt.Color;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import nl.captcha.Captcha;
import nl.captcha.backgrounds.GradiatedBackgroundProducer;
import nl.captcha.gimpy.FishEyeGimpyRenderer;
import nl.captcha.servlet.CaptchaServletUtil;

public class AssemblyCaptcha extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException { 
        /*
            this entire servlet is an image
        */
        
        // configurations for the captcha
        HttpSession session = request.getSession(); 
        Captcha captcha = new Captcha.Builder(200, 70)//(height, width)  
        .addText()
        .addNoise()
        .gimp(new FishEyeGimpyRenderer())          
        .addBackground(new GradiatedBackgroundProducer(Color.GREEN, Color.ORANGE))  
        .build();
        session.setAttribute("captcha", captcha);   
        
        // has response.setContentType(image), this is what sets the servlet as image 
        // CaptchaServletUtil.writeImage(response, captcha.getImage());
    }
}
