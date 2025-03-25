package ru.yandex.practicum.shareit.request;

import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemRequest {
    @NotNull
    @Positive
    Long requestId;
    @NotNull
    @Positive
    Long userId;
    String description;
}
