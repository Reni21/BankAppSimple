package bank.app.simple.serviceimpl;

import bank.app.simple.dao.UserDao;
import bank.app.simple.entity.User;
import bank.app.simple.exception.PersonalNumberNotUniqueExecption;
import bank.app.simple.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
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
}
