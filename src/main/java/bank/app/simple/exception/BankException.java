package bank.app.simple.exception;

public abstract class BankException extends Exception {

    public BankException(String reason) {
        super(reason);
    }
}
