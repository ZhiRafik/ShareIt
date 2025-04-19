package ru.yandex.practicum.shareit.booking;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingRequestDto {
    @NotNull
    Long itemId;
    @NotNull
    LocalDateTime start;
    @NotNull
    LocalDateTime end;
}
