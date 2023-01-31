
package Assembly.Errors;

public class AuthenticationException extends RuntimeException 
{
    public AuthenticationException()
    {
       super("Incorrect login credentials");
    }
}
