package az.azericard.serviceuser.service;

import az.azericard.serviceuser.domain.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User addUser(User user);

    User getUserByUsername(String username);

    void deleteUser(String username);
}
