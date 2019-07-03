package bank.app.simple.exception;

public class AccountNotFoundException extends BankException {

    public AccountNotFoundException(String reason) {
        super("Account with " + reason + " is not found");
    }
}
