package bank.app.simple.exception;

public class AccountNumberNotUnicException extends BankException {

    public AccountNumberNotUnicException(String accountNumber) {
        super("Account with number=" + accountNumber + " is already exist");
    }
}
