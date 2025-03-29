package ru.yandex.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public Optional<UserDto> addUser(User user) {
        Optional<User> addedUser = repository.addUser(user);
        if (addedUser.isEmpty()) {
            return Optional.empty();
        }
        UserDto dto = UserDtoMapper.mapToDto(addedUser.get());
        return Optional.of(dto);
    }

    public List<UserDto> getUsers() {
        return repository.getUsers().stream()
                .map(UserDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public Optional<UserDto> getUser(Long id) {
        Optional<User> foundUser = repository.getUser(id);
        if (foundUser.isEmpty()) {
            return Optional.empty();
        }
        UserDto dto = UserDtoMapper.mapToDto(foundUser.get());
        return Optional.of(dto);
    }

    public Optional<UserDto> updateUser(UserDto dto, Long userId) {
        Optional<User> addedUser = repository.updateUser(dto, userId);
        if (addedUser.isEmpty()) {
            return Optional.empty();
        }
        UserDto dtoToReturn = UserDtoMapper.mapToDto(addedUser.get());
        return Optional.of(dtoToReturn);
    }

    public Optional<UserDto> deleteUser(Long id) {
        Optional<User> deletedUser = repository.deleteUser(id);
        if (deletedUser.isEmpty()) {
            return Optional.empty();
        }
        UserDto dto = UserDtoMapper.mapToDto(deletedUser.get());
        return Optional.of(dto);
    }
}
