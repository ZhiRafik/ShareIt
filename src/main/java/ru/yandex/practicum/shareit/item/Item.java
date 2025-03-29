package ru.yandex.practicum.shareit.item;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Item {
    Long itemId;
    Long ownerId;
    @NotBlank @NotNull
    String name;
    @NotBlank @NotNull
    String description;
    @NotNull
    Boolean available;
    Long timesUsed;
}
