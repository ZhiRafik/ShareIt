package ru.yandex.practicum.shareit.item;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Item {
    @NotNull
    @Positive
    Long itemId;
    Long ownerId;
    String name;
    String description;
    Boolean available;
    Long timesUsed;
}
