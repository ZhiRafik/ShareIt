package shareit.user;

import jakarta.validation.constraints.*;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor // чтобы Hibernate мог создать объект
@AllArgsConstructor // чтобы Builder работы при NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "users")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Email @NotBlank @NotNull @Column(length = 512, nullable = false)
    String email;
    @NotBlank @NotNull @Column(unique = true)
    String name;
}
