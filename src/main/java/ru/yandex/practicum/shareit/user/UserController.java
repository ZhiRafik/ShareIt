package ru.yandex.practicum.shareit.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.shareit.Exception.BadRequestException;
import ru.yandex.practicum.shareit.Exception.NotFoundException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService service;

    @PostMapping
    public UserDto addUser(@Valid @RequestBody User user) {
        return service.addUser(user)
                .orElseThrow(() -> new BadRequestException("User with such an email already exists"));
    }

    @GetMapping
    public List<UserDto> getUsers() {
        return service.getUsers();
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable Long id) {
        return service.getUser(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    @PatchMapping
    public UserDto updateUser(@RequestBody UserDto dto,
                              @RequestHeader(name = "X-Sharer-User-Id") Long userId) {
        return service.updateUser(dto, userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    @DeleteMapping("/{id}")
    public UserDto deleteUser(@PathVariable Long id) {
        return service.deleteUser(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }
}
