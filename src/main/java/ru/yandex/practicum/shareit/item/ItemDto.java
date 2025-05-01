package ru.yandex.practicum.shareit.item;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.shareit.user.User;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemDto {
    Long id;
    @NotBlank
    String name;
    User owner;
    @NotBlank
    String description;
    Boolean available;
    Long timesUsed;
    @Positive
    Long requestId;
}
