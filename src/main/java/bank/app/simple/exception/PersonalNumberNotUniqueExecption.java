package bank.app.simple.exception;

public class PersonalNumberNotUniqueExecption extends BankException {
    public PersonalNumberNotUniqueExecption(String personalNumber) {
        super("User with user number=" + personalNumber + " is already exist");
    }
}
