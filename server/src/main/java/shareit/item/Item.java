package shareit.item;

import lombok.*;
import shareit.user.User;
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
    @ManyToOne @JoinColumn(name = "owner_id")
    User owner;
    @Column(nullable = false)
    String name;
    @Column(nullable = false)
    String description;
    Boolean available;
    Long timesUsed;
    @Column(name = "request_id")
    Long requestId;
}
