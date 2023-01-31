
package Assembly.Errors;

public class NullValueException extends RuntimeException
{
    public NullValueException()
    {
        super("There are no values on username and password, please try again");
    }
}
