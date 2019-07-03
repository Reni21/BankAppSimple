package bank.app.simple.serviceimpl;

import bank.app.simple.dao.ExchangeRateDao;
import bank.app.simple.entity.Currency;
import bank.app.simple.entity.ExchangeRate;
import bank.app.simple.service.ExchangeRateService;

import java.util.List;

public class ExchangeRateServiceImpl  implements ExchangeRateService {
    private final ExchangeRateDao rateDao;

    public ExchangeRateServiceImpl(ExchangeRateDao rateDao) {
        this.rateDao = rateDao;
    }

    @Override
    public void addRate(ExchangeRate rate) {
        if(rateDao.isCurrencyExist(rate.getCurrency())){
            throw new IllegalArgumentException("This type of currency already exist");
        } else {
            Long id = rateDao.create(rate);
            rate.setId(id);
        }
    }

    @Override
    public ExchangeRate findRateById(Long id) {
        return rateDao.find(id);
    }

    @Override
    public ExchangeRate findRateByCurrency(Currency currency) {
        return rateDao.findRateByCurrency(currency);
    }

    @Override
    public List<ExchangeRate> findAllRates() {
        return rateDao.findAll();
    }

    @Override
    public void updateRate(ExchangeRate rate) {
        rateDao.update(rate);
    }

    @Override
    public void deleteRate(Long id) {
        ExchangeRate rate = findRateById(id);
        rateDao.delete(rate, id);
    }
}
