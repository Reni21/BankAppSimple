package bank.app.simple;

import bank.app.simple.dao.ExchangeRateDao;
import bank.app.simple.dao.TransactionDao;
import bank.app.simple.daoimpl.AccountDaoImpl;
import bank.app.simple.daoimpl.ExchangeRateDaoImpl;
import bank.app.simple.daoimpl.TransactionDaoImpl;
import bank.app.simple.daoimpl.UserDaoImpl;
import bank.app.simple.entity.*;
import bank.app.simple.exception.BankException;
import bank.app.simple.exception.NotEnoughMoneyException;
import bank.app.simple.serviceimpl.AccountServiceImpl;
import bank.app.simple.serviceimpl.ExchangeRateServiceImpl;
import bank.app.simple.serviceimpl.UserServiceImpl;
import bank.app.simple.client.ExchangeRateExternalClient;
import bank.app.simple.util.JpaUtil;

public class Main {
    private static ExchangeRateDao rateDao = new ExchangeRateDaoImpl();
    private static TransactionDao transDao = new TransactionDaoImpl();
    private static UserServiceImpl userService = new UserServiceImpl(new UserDaoImpl());
    private static ExchangeRateExternalClient exchangeRateClient = new ExchangeRateExternalClient();
    private static ExchangeRateServiceImpl rateService = new ExchangeRateServiceImpl(rateDao, exchangeRateClient);
    private static AccountServiceImpl accountService = new AccountServiceImpl(new AccountDaoImpl(), transDao, rateService);

    public static void main(String[] args){
        try {
            fillTables();
            System.out.println(
                    ">-------- Total balance from accounts for user US222 = " +
                            accountService.totalUserBalansInUAH("US222") + " UAH");
            try {
                accountService.withdrawFromAccount(250.0, "AC444");
            } catch (NotEnoughMoneyException ex) {
                System.out.println(">-------- Exception: " + ex.getMessage());
            }
            accountService.depositAccount(400.0, "AC444");
            accountService.withdrawFromAccount(250.0, "AC444");

            accountService.transferMoney(1000.0, "AC111", "AC222");
            accountService.transferMoney(10.0, "AC222", "AC444");
        } catch (BankException ex){
            System.out.println(">-------- Exception: " + ex.getMessage());
        } catch (Exception ex){
            ex.printStackTrace();
        } finally {
            JpaUtil.closeEntMngFactory();
        }

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
