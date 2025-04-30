package ru.yandex.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.shareit.exception.ConflictException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    public Optional<UserDto> saveUser(UserDto dto) {
        Optional<User> user = repository.findByEmail(dto.getEmail());
        if (user.isPresent()) {
            return Optional.empty();
        }
        User savedUser = repository.save(UserDtoMapper.mapToModel(dto));
        return Optional.of(UserDtoMapper.mapToDto(savedUser));
    }

    public List<UserDto> getUsers() {
        return repository.findAll().stream()
                .map(UserDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public Optional<UserDto> getUser(Long id) {
        Optional<User> foundUser = repository.findById(id);
        if (foundUser.isEmpty()) {
            return Optional.empty();
        }
        UserDto dto = UserDtoMapper.mapToDto(foundUser.get());
        return Optional.of(dto);
    }

    public Optional<UserDto> updateUser(UserDto dto, Long userId) {
        Optional<User> neededUser = repository.findById(userId);
        if (neededUser.isEmpty()) {
            return Optional.empty();
        }
        User foundUser = neededUser.get();
        if (dto.getId() != null) {
            foundUser.setId(dto.getId());
        }
        if (dto.getName() != null) {
            foundUser.setName(dto.getName());
        }
        if (dto.getEmail() != null) {
            if (repository.findByEmail(dto.getEmail()).isPresent()) {
                throw new ConflictException("User with such an email already exists");
            }
            foundUser.setEmail((dto.getEmail()));
        }
        repository.save(foundUser);
        return Optional.of(UserDtoMapper.mapToDto(foundUser));
    }

    public Optional<UserDto> deleteUser(Long id) {
        Optional<User> user = repository.findById(id);
        if (user.isEmpty()) {
            return Optional.empty();
        }
        User foundUser = user.get();
        repository.deleteById(id);
        return Optional.of(UserDtoMapper.mapToDto(foundUser));
    }
}
