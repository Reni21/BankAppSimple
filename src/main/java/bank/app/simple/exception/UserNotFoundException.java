package bank.app.simple.exception;

public class UserNotFoundException extends BankException {

    public UserNotFoundException(String reason) {
        super("User with " + reason + " is not found");
    }
}
