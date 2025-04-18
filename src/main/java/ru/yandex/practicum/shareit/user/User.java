package ru.yandex.practicum.shareit.user;

import jakarta.validation.constraints.*;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Generated;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "users")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long userId;
    @Email @NotBlank @NotNull @Column(length = 512, nullable = false)
    String email;
    @NotBlank @NotNull @Column(unique = true)
    String name;
}
