/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Assembly.Errors;

public class UserFoundException extends RuntimeException{
    public UserFoundException(){
        super("User exists in the database");
    }
}
