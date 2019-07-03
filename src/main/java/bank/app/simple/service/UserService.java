package bank.app.simple.service;

import bank.app.simple.entity.User;
import bank.app.simple.exception.PersonalNumberNotUniqueExecption;
import bank.app.simple.exception.UserNotFoundException;

import java.io.IOException;
import java.util.List;

public interface UserService {
     void addUser(User user) throws PersonalNumberNotUniqueExecption;

    User findUserById(Long id);

    double totalUserBalansInUAH(String personalNumber) throws UserNotFoundException, IOException;

    List<User> findAllUsers();

    void updateUser(User user);

    void deleteUser(Long id);
}
