package shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findAllByItemId(Long itemId);

    List<Booking> findAllByItemIdAndBooker_Id(Long itemId, Long userId);

    List<Booking> findByItemIdAndStartBetween(Long itemId, LocalDateTime start, LocalDateTime end);

    List<Booking> findByBooker_IdOrderByStartDesc(Long userId);

    List<Booking> findByBooker_IdAndStatusOrderByStartDesc(Long userId, String status);

    List<Booking> findAllByBooker_IdAndStatus(Long userId, String status);
}
