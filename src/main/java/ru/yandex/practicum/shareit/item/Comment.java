package ru.yandex.practicum.shareit.item;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.shareit.user.User;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor // чтобы Hibernate мог создать объект
@AllArgsConstructor // чтобы Builder работы при NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String text;
    @NotNull @ManyToOne @JoinColumn(name = "item_id")
    Item item;
    @NotNull @ManyToOne @JoinColumn(name = "user_id")
    User author;
    LocalDateTime created;
}
