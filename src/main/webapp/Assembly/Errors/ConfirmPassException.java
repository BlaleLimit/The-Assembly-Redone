package Assembly.Errors;

public class ConfirmPassException extends RuntimeException{
    public ConfirmPassException(){
        super("Password does not match");
    }
}
