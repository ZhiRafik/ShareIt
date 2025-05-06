package shareit.booking;

import jakarta.persistence.Enumerated;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import shareit.item.Item;
import shareit.user.User;

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
    @ManyToOne @JoinColumn(name = "item_id")
    Item item;
    @ManyToOne @JoinColumn(name = "booker_id")
    User booker;
    LocalDateTime start;
    @Column(name = "\"end\"")
    LocalDateTime end;
    @Enumerated(EnumType.STRING)
    Status status;
}