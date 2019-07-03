package bank.app.simple.exception;

public class NotEnoughMoneyException extends BankException {

    public NotEnoughMoneyException(String operation) {
        super("Not enough money for this operation \"" + operation + "\"");
    }
}
