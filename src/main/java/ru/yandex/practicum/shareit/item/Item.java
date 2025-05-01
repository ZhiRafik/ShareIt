package ru.yandex.practicum.shareit.item;

import lombok.*;
import ru.yandex.practicum.shareit.user.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor // чтобы Hibernate мог создать объект
@AllArgsConstructor // чтобы Builder работы при NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "items")
public class Item {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NotNull @ManyToOne @JoinColumn(name = "owner_id")
    User owner;
    @NotBlank @Column(nullable = false)
    String name;
    @NotBlank @Column(nullable = false)
    String description;
    @NotNull
    Boolean available;
    Long timesUsed;
    @Column(name = "request_id")
    Long requestId;
}
