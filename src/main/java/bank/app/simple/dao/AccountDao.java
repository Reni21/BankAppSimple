package bank.app.simple.dao;

import bank.app.simple.entity.Account;

import java.util.List;

public interface AccountDao extends GenericDao<Account, Long> {
    Account findAccountByNumber(String accountNumber);
    List<Account> findAllUserAccounts(String personalNumber);
}
