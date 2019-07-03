package bank.app.simple.service;

import bank.app.simple.entity.Account;
import bank.app.simple.entity.User;
import bank.app.simple.exception.AccountNotFoundException;
import bank.app.simple.exception.AccountNumberNotUnicException;
import bank.app.simple.exception.BankException;
import bank.app.simple.exception.NotEnoughMoneyException;

import java.io.IOException;
import java.util.List;

public interface AccountService {

    void addAccount(Account account) throws AccountNumberNotUnicException;

    Account findAccountById(Long id) throws AccountNotFoundException;

    Account findAccountByNumber(String accountNumber) throws AccountNotFoundException;

    List<Account> findAllAccounts();

    List<Account> findAllUserAccounts(String personalNumber);

    void updateAccount(Account account) throws AccountNotFoundException;

    void transferMoney(double sum, String fromNumber, String toNumber) throws BankException, IOException;

    void depositAccount(double sum, String toNumber) throws AccountNotFoundException;

    void withdrawFromAccount(double sum, String fromNumber) throws AccountNotFoundException, NotEnoughMoneyException;

    void deleteAccount(Long id) throws AccountNotFoundException;

}
