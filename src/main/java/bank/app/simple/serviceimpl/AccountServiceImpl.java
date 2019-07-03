package bank.app.simple.serviceimpl;

import bank.app.simple.dao.AccountDao;
import bank.app.simple.dao.TransactionDao;
import bank.app.simple.entity.Account;
import bank.app.simple.entity.Currency;
import bank.app.simple.entity.Transaction;
import bank.app.simple.entity.TransactionType;
import bank.app.simple.exception.AccountNotFoundException;
import bank.app.simple.exception.AccountNumberNotUnicException;
import bank.app.simple.exception.BankException;
import bank.app.simple.exception.NotEnoughMoneyException;
import bank.app.simple.service.AccountService;
import bank.app.simple.service.ExchangeRateService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class AccountServiceImpl implements AccountService {
    private final AccountDao accountDao;
    private final TransactionDao transDao;
    private final ExchangeRateService exchangeRateService;

    public AccountServiceImpl(AccountDao accountDao,
                              TransactionDao transDao,
                              ExchangeRateService exchangeRateService) {
        this.accountDao = accountDao;
        this.transDao = transDao;
        this.exchangeRateService = exchangeRateService;
    }

    @Override
    public void addAccount(Account account) throws AccountNumberNotUnicException {
        Long id = accountDao.create(account);
        if (id == null) {
            throw new AccountNumberNotUnicException(account.getAccountNumber());
        }
        account.setId(id);
    }

    @Override
    public Account findAccountByNumber(String accountNumber) {
        return accountDao.findAccountByNumber(accountNumber);
    }

    @Override
    public List<Account> findAllAccounts() {
        return accountDao.findAll();
    }

    @Override
    public List<Account> findAllUserAccounts(String personalNumber) {
        return accountDao.findAllUserAccounts(personalNumber);
    }

    @Override
    public void updateAccount(Account account) throws AccountNotFoundException {
        checkAccountExists(account.getId());
        accountDao.update(account);
    }

    @Override
    public void transferMoney(double sum, String fromNumber, String toNumber) throws BankException {
        Account accFrom = checkAccountNumberExists(fromNumber);
        Account accTo = checkAccountNumberExists(toNumber);

        if (accFrom.getBalance() >= sum) {
            accFrom.setBalance(accFrom.getBalance() - sum);
            transDao.create(
                    new Transaction(TransactionType.WITHDRAW, accFrom, accTo, sum, LocalDateTime.now()));

            if (accFrom.getCurrency() == accTo.getCurrency()) {
                accTo.setBalance(accTo.getBalance() + sum);
                transDao.create(
                        new Transaction(TransactionType.DEPOSITE, accFrom, accTo, sum, LocalDateTime.now()));

            } else {
                double convertedSum =
                        exchangeRateService.convertMoney(accFrom.getCurrency(), accTo.getCurrency(), sum);
                accTo.setBalance(accTo.getBalance() + convertedSum);
                transDao.create(
                        new Transaction(TransactionType.DEPOSITE, accFrom, accTo, convertedSum, LocalDateTime.now()));
            }

            updateAccount(accFrom);
            updateAccount(accTo);
        } else {
            throw new NotEnoughMoneyException("transfer");
        }
    }

    @Override
    public void depositAccount(double sum, String accountNumber) throws AccountNotFoundException, IllegalArgumentException {
        if (sum <= 0) {
            throw new IllegalArgumentException("Incorrect sum data");
        }

        Account account = checkAccountNumberExists(accountNumber);
        account.setBalance(account.getBalance() + sum);
        updateAccount(account);
        transDao.create(
                new Transaction(TransactionType.DEPOSITE, null, account, sum, LocalDateTime.now()));
    }

    @Override
    public void withdrawFromAccount(double sum, String fromNumber) throws AccountNotFoundException, NotEnoughMoneyException {
        if (sum <= 0) {
            throw new IllegalArgumentException("Incorrect sum data");
        }

        Account account = checkAccountNumberExists(fromNumber);
        double balance = account.getBalance();
        if (balance >= sum) {
            account.setBalance(balance - sum);
            updateAccount(account);
            transDao.create(
                    new Transaction(TransactionType.WITHDRAW, account, null, sum, LocalDateTime.now()));
        } else {
            throw new NotEnoughMoneyException("withdraw");
        }
    }

    @Override
    public void deleteAccount(Long id) throws AccountNotFoundException {
        Account account = checkAccountExists(id);
        accountDao.delete(account, id);
    }

    @Override
    public double totalUserBalansInUAH(String personalNumber) {
        List<Account> accounts = accountDao.findAllUserAccounts(personalNumber);
        double totalBalans = 0.0;
        if (accounts.isEmpty()) {
            return totalBalans;
        }
        for (Account account : accounts) {
            double accBalans = account.getBalance();
            if (account.getCurrency() == Currency.UAH) {
                totalBalans += accBalans;
            } else {
                totalBalans += exchangeRateService.convertMoney(account.getCurrency(), Currency.UAH, accBalans);
            }
        }
        return totalBalans;
    }

    private Account checkAccountExists(Long id) throws AccountNotFoundException {
        Account account = accountDao.find(id);
        if (account == null) {
            throw new AccountNotFoundException("id=" + id.toString());
        }
        return account;
    }

    private Account checkAccountNumberExists(String accountNumber) throws AccountNotFoundException {
        Account account = accountDao.findAccountByNumber(accountNumber);
        if (account == null) {
            throw new AccountNotFoundException("accountNumber=" + accountNumber);
        }
        return account;
    }
}
