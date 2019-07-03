package bank.app.simple.daoimpl;

import bank.app.simple.dao.ExchangeRateDao;
import bank.app.simple.entity.Currency;
import bank.app.simple.entity.ExchangeRate;
import bank.app.simple.util.JpaUtil;

import javax.persistence.NoResultException;
import javax.persistence.Query;

public class ExchangeRateDaoImpl extends GenericDaoImpl<ExchangeRate, Long> implements ExchangeRateDao {

    public ExchangeRateDaoImpl() {
        super(ExchangeRate.class);
    }

    @Override
    public boolean isCurrencyExist(Currency currency) {
        entM = JpaUtil.createEntityManager();
        Query query = entM
                .createQuery("SELECT 1 FROM ExchangeRate r WHERE r.currency = :currency");
        query.setParameter("currency", currency);
        try {
            int data = (int) query.getSingleResult();
            return data == 1;
        } catch (NoResultException ex) {
            return false;
        } finally {
            entM.close();
        }
    }

    @Override
    public ExchangeRate findRateByCurrency(Currency currency) {
        entM = JpaUtil.createEntityManager();
        Query query = entM
                .createQuery("SELECT r FROM ExchangeRate r WHERE r.currency = :currency", ExchangeRate.class);
        query.setParameter("currency", currency);
        try {
            return (ExchangeRate) query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        } finally {
            entM.close();
        }
    }
}
