/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Assembly.Errors;

/**
 *
 * @author Haffiny-nardrei
 */
public class CaptchaException extends RuntimeException{
    public CaptchaException(){
        super("Captcha identification was wrong");
    }
}
