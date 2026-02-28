package exceptions;

public class AccountNotFoundException extends RuntimeException {

    //made this with generate> constructor > msg(2nd option) .
    public AccountNotFoundException(String message) {
        super(message);
    }

}
