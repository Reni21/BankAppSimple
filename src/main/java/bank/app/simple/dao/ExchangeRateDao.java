package bank.app.simple.dao;

import bank.app.simple.entity.Currency;
import bank.app.simple.entity.ExchangeRate;

public interface ExchangeRateDao extends GenericDao <ExchangeRate, Long> {
    boolean isCurrencyExist(Currency currency);
    ExchangeRate findRateByCurrency(Currency currency);
}
