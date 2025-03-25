package ru.yandex.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public User addUser(User user) {
        return repository.addUser(user);
    }

    public List<User> getUsers() {
        return repository.getUsers();
    }

    public Optional<User> getUser(Long id) {
        return repository.getUser(id);
    }

    public Optional<User> updateUser(User user) {
        return repository.updateUser(user);
    }

    public Optional<User> deleteUser(Long id) {
        return repository.deleteUser(id);
    }
}
