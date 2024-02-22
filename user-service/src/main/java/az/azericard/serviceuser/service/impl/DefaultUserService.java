package az.azericard.serviceuser.service.impl;

import az.azericard.serviceuser.domain.entity.User;
import az.azericard.serviceuser.repository.UserRepository;
import az.azericard.serviceuser.service.UserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;

    public DefaultUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserByUsername(String username) {
        Objects.requireNonNull(username, "username must not be null!");

        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User '" + username + "' not found!"));
    }

    @Override
    @Transactional
    public void deleteUser(String username) {
        Objects.requireNonNull(username, "username must not be null!");

        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with " + username + " not found!"));

        userRepository.delete(user);
    }
}
