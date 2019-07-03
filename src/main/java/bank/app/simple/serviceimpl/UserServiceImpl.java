package bank.app.simple.serviceimpl;

import bank.app.simple.dao.UserDao;
import bank.app.simple.entity.User;
import bank.app.simple.exception.PersonalNumberNotUniqueExecption;
import bank.app.simple.exception.UserNotFoundException;
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
        if (id == null) {
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
    public void updateUser(User user) throws UserNotFoundException {
        checkUserExists(user.getId());
        userDao.update(user);
    }

    @Override
    public void deleteUser(Long id) throws UserNotFoundException {
        User user = checkUserExists(id);
        userDao.delete(user, id);
    }

    private User checkUserExists(Long id) throws UserNotFoundException {
        User user = userDao.find(id);
        if (user == null) {
            throw new UserNotFoundException("id=" + id);
        }
        return user;
    }
}
