package bank.app.simple.service;

import bank.app.simple.entity.User;
import bank.app.simple.exception.PersonalNumberNotUniqueExecption;

import java.util.List;

public interface UserService {
    public void addUser(User user) throws PersonalNumberNotUniqueExecption;

    public User findUserById(Long id);

    public List<User> findAllUsers();

    public void updateUser(User user);

    public void deleteUser(Long id);
}
