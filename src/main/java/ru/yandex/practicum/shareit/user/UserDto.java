package ru.yandex.practicum.shareit.user;

import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {
    @NotNull
    @Positive
    Long userId;
    @NotNull
    @Email
    String email;
    String name;
}