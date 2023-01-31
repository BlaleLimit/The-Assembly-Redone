/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Assembly.Errors;

public class SessionException extends RuntimeException
{
    public SessionException(){
        super("Session is already destroyed or was not created");
    }
}
