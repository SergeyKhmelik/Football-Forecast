package exceptions;

public class NoSuchUserException extends Throwable {

    private static final String MESSAGE = "Wrong user name or password.";

    @Override
    public String getMessage() {
        return MESSAGE;
    }

}
