package bank.app.simple;

import bank.app.simple.dao.AccountDao;
import bank.app.simple.dao.ExchangeRateDao;
import bank.app.simple.dao.TransactionDao;
import bank.app.simple.daoimpl.*;
import bank.app.simple.entity.*;
import bank.app.simple.exception.AccountNotFoundException;
import bank.app.simple.exception.BankException;
import bank.app.simple.exception.NotEnoughMoneyException;
import bank.app.simple.serviceimpl.AccountServiceImpl;
import bank.app.simple.serviceimpl.ExchangeRateServiceImpl;
import bank.app.simple.serviceimpl.UserServiceImpl;

import java.io.IOException;

public class Main {
    private static ExchangeRateDao rateDao = new ExchangeRateDaoImpl();
    private static TransactionDao transDao = new TransactionDaoImpl();
    private static UserServiceImpl userService = new UserServiceImpl(new UserDaoImpl(), rateDao);
    private static ExchangeRateServiceImpl rateService = new ExchangeRateServiceImpl(rateDao);
    private static AccountServiceImpl accountService = new AccountServiceImpl(new AccountDaoImpl(), rateDao, transDao);

    public static void main(String[] args) throws BankException, IOException {
        fillTables();
        System.out.println(
                ">-------- Total balance from accounts for user US222 = " +
                        userService.totalUserBalansInUAH("US222") + " UAH");
        try {
            accountService.withdrawFromAccount(250.0, "AC444");
        } catch (NotEnoughMoneyException ex) {
            System.out.println(">-------- Exception: " + ex.getMessage());
        }
        accountService.depositAccount(400.0, "AC444");
        accountService.withdrawFromAccount(250.0, "AC444");

        accountService.transferMoney(1000.0, "AC111", "AC222");
        accountService.transferMoney(10.0, "AC222", "AC444");
    }

    private static void fillTables() throws BankException {
        ExchangeRate rateUsd = new ExchangeRate(Currency.USD, 27.0, 26.5);
        ExchangeRate rateEur = new ExchangeRate(Currency.EUR, 30.5, 29.5);
        rateService.addRate(rateUsd);
        rateService.addRate(rateEur);

        User oleg = new User("Oleg", "US111");
        Account olegMaster = new Account(AccountType.MASTER, "AC111", oleg, Currency.UAH);
        olegMaster.setBalance(5000.0);
        Account olegForegin = new Account(AccountType.FOREGIN_CUR, "AC222", oleg, Currency.USD);
        oleg.addAccount(olegMaster);
        oleg.addAccount(olegForegin);
        userService.addUser(oleg);

        User reni = new User("Reni", "US222");
        userService.addUser(reni);
        Account reniMaster = new Account(AccountType.MASTER, "AC333", reni, Currency.UAH);
        Account reniForegin = new Account(AccountType.FOREGIN_CUR, "AC444", reni, Currency.EUR);
        reniForegin.setBalance(100.0);
        accountService.addAccount(reniMaster);
        accountService.depositAccount(1000.0, "AC333");
        accountService.addAccount(reniForegin);
    }
}