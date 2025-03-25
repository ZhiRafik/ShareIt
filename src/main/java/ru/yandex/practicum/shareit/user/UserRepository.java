package ru.yandex.practicum.shareit.user;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    private HashMap<Long, User> users = new HashMap<>();
    private long id;

    public User addUser(User user) {
        user.setUserId(newId());
        users.put(user.getUserId(), user);
        return user;
    }

    public List<User> getUsers() {
        return List.copyOf(users.values());
    }

    public Optional<User> getUser(Long id) {
        return Optional.of(users.get(id));
    }

    public Optional<User> updateUser(User user) {
        users.put(user.getUserId(), user);
        return Optional.of(users.get(user.getUserId()));
    }

    public Optional<User> deleteUser(Long id) {
        Optional<User> deletedUser = Optional.of(users.get(id));
        users.remove(id);
        return deletedUser;
    }

    public Long newId() {
        return ++id;
    }
}
