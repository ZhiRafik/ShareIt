package ru.yandex.practicum.shareit.user;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    private HashMap<Long, User> users = new HashMap<>();
    private long id;

    public Optional<User> addUser(User user) {
        for (User savedUser : users.values()) {
            if (savedUser.getEmail().equals(user.getEmail())) {
                return Optional.empty();
            }
        }
        user.setUserId(newId());
        users.put(user.getUserId(), user);
        return Optional.of(user);
    }

    public List<User> getUsers() {
        return List.copyOf(users.values());
    }

    public Optional<User> getUser(Long id) {
        return Optional.of(users.get(id));
    }

    public Optional<User> updateUser(User user, Long userId) {
        if (!users.containsKey(userId)) {
            return Optional.empty();
        }
        user.setUserId(userId);
        users.put(userId, user);
        return Optional.of(user);
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
