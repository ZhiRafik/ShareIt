package ru.yandex.practicum.shareit.booking;

import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.shareit.item.Item;
import ru.yandex.practicum.shareit.user.User;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor // чтобы Hibernate мог создать объект
@AllArgsConstructor // чтобы Builder работы при NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "bookings")
public class Booking {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NotNull @ManyToOne @JoinColumn(name = "item_id")
    Item item;
    @NotNull @ManyToOne @JoinColumn(name = "booker_id")
    User booker;
    LocalDateTime start;
    @Column(name = "\"end\"")
    LocalDateTime end;
    @Enumerated(EnumType.STRING)
    Status status;
}