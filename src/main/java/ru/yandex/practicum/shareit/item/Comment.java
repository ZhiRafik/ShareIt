package ru.yandex.practicum.shareit.item;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.shareit.user.User;

import java.time.LocalDateTime;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String text;
    @NotNull @Column(nullable = false) @ManyToOne @JoinColumn(name = "item_id")
    Item item;
    @NotNull @Column(nullable = false) @ManyToOne @JoinColumn(name = "user_id")
    User author;
    LocalDateTime created;
}
