package bank.app.simple.service;

import bank.app.simple.entity.Currency;
import bank.app.simple.entity.ExchangeRate;

import java.util.List;

public interface ExchangeRateService {
    void addRate(ExchangeRate rate);

    ExchangeRate findRateById(Long id);

    ExchangeRate findRateByCurrency(Currency currency);

    List<ExchangeRate> findAllRates();

    void updateRate(ExchangeRate rate);

    void deleteRate(Long id);

    double convertMoney(Currency curFrom, Currency curTo, double sum);
}
