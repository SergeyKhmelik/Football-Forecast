package exceptions;

public class NoSuchRoleException extends Throwable {
    
    @Override
    public String getMessage() {
        return "There is no role with that name or id.";
    }

}
