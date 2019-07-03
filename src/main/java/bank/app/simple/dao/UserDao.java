package bank.app.simple.dao;

import bank.app.simple.entity.User;


public interface UserDao extends GenericDao<User, Long> {
    User findUserByPersonalNumber(String personalNumber);
}
