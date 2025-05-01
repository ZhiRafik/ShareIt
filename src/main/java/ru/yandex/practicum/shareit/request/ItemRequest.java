package ru.yandex.practicum.shareit.request;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor // чтобы Hibernate мог создать объект
@AllArgsConstructor // чтобы Builder работы при NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemRequest {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long requestId;
    @Column(name = "user_id", nullable = false)
    Long userId;
    String description;
    LocalDateTime created;
}
