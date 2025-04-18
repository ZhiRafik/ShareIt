package ru.yandex.practicum.shareit.booking;

import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.shareit.item.Item;

import java.time.LocalDateTime;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "bookings")
public class Booking {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long bookingId;
    @NotNull @Column(nullable = false) @ManyToOne @JoinColumn(name = "item_id")
    Item item;
    Long userId;
    LocalDateTime startTime;
    LocalDateTime endTime;
    @Enumerated(EnumType.STRING)
    Status status;
}