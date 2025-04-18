package ru.yandex.practicum.shareit.item;

import ru.yandex.practicum.shareit.user.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "items")
public class Item {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long itemId;
    @Column(nullable = false) @ManyToOne @JoinColumn(name = "owner_id")
    User owner;
    @NotBlank @Column(nullable = false)
    String name;
    @NotBlank @Column(nullable = false)
    String description;
    @NotNull
    Boolean available;
    Long timesUsed;
}
