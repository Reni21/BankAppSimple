package bank.app.simple.daoimpl;

import bank.app.simple.dao.AccountDao;
import bank.app.simple.entity.Account;
import bank.app.simple.util.JpaUtil;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class AccountDaoImpl extends GenericDaoImpl<Account, Long> implements AccountDao {

    public AccountDaoImpl() {
        super(Account.class);
    }

    @Override
    public Account findAccountByNumber(String accountNumber) {
        entM = JpaUtil.createEntityManager();
        Query query = entM
                .createQuery("SELECT a FROM Account a WHERE a.accountNumber = :accountNumber");
        query.setParameter("accountNumber", accountNumber);
        try {
            Account account = (Account) query.getSingleResult();
            return account;
        } catch (NoResultException ex) {
            return null;
        } finally {
            entM.close();
        }
    }

    @Override
    public List<Account> findAllUserAccounts(String personalNumber) {
        entM = JpaUtil.createEntityManager();

        try {
            TypedQuery<Account> acc = entM.createQuery("SELECT a FROM Account a WHERE a.owner.personalNumber = :personalNumber", Account.class);
            acc.setParameter("personalNumber", personalNumber);
            List<Account> results = acc.getResultList();

            return results;
        } finally {
            entM.close();
        }
    }
}
