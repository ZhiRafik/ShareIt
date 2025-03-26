package ru.yandex.practicum.shareit.user;

import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    Long userId;
    @Email @NotNull
    String email;
    @NotNull
    String name;
}
