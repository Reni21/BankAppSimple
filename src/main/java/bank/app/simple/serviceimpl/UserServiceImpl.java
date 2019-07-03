package bank.app.simple.serviceimpl;
import bank.app.simple.dao.ExchangeRateDao;
import bank.app.simple.dao.UserDao;
import bank.app.simple.entity.Account;
import bank.app.simple.entity.Currency;
import bank.app.simple.entity.User;
import bank.app.simple.exception.PersonalNumberNotUniqueExecption;
import bank.app.simple.exception.UserNotFoundException;
import bank.app.simple.service.UserService;
import bank.app.simple.util.ExchangeRateUtil;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final ExchangeRateDao rateDao;

    public UserServiceImpl(UserDao userDao, ExchangeRateDao rateDao) {
        this.userDao = userDao;
        this.rateDao = rateDao;
    }

    @Override
    public void addUser(User user) throws PersonalNumberNotUniqueExecption {
        Long id = userDao.create(user);
        if (id == null){
            throw new PersonalNumberNotUniqueExecption(user.getPersonalNumber());
        } else {
            user.setId(id);
        }
    }

    @Override
    public User findUserById(Long id) {
        return userDao.find(id);
    }

    @Override
    public double totalUserBalansInUAH(String personalNumber) throws UserNotFoundException, IOException {
        User user = checkUserNumberExists(personalNumber);
        Set<Account> accounts = user.getAccounts();
        double totalBalans = 0.0;
        if (accounts.isEmpty()){
            return totalBalans;
        }
        for (Account account : accounts) {
            double accBalans = account.getBalance();
            if (account.getCurrency() == Currency.UAH){
                totalBalans += accBalans;
            } else {
                totalBalans += ExchangeRateUtil.convertMoney(rateDao, account.getCurrency(), Currency.UAH, accBalans);
            }
        }
        return totalBalans;
    }

    @Override
    public List<User> findAllUsers() {
        return userDao.findAll();
    }

    @Override
    public void updateUser(User user) {
        userDao.update(user);
    }

    @Override
    public void deleteUser(Long id) {
        User user = findUserById(id);
        userDao.delete(user, id);
    }

    private User checkUserNumberExists(String accountNumber) throws UserNotFoundException {
        User user = userDao.findUserByPersonalNumber(accountNumber);
        if (user == null) {
            throw new UserNotFoundException("accountNumber=" + accountNumber);
        }
        return user;
    }
}
