package ru.yandex.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findAllByItemId(Long itemId);

    List<Booking> findAllByItemIdAndUserId(Long itemId, Long userId);

    List<Booking> findByItemIdAndStartBetween(Long itemId, LocalDateTime start, LocalDateTime end);

    List<Booking> findByUserIdOrderByStartTimeDesc(Long userId);

    List<Booking> findByUserIdAndStatusOrderByStartTimeDesc(Long userId, String status);
    List<Booking> findAllByUserIdAndStatus(Long userId, String status);
}
