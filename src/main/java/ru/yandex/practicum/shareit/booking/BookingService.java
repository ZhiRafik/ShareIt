package ru.yandex.practicum.shareit.booking;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

public interface BookingService {
    Booking addBooking(BookingRequestDto bookingRequest);

    Booking confirmStatus(Long bookingId, Boolean status, Long userId);

    Booking getInformation(Long bookingId, Long userId);

    List<Booking> getUserBookings(Long userId, String status);

    List<Booking> getItemsBookings(Long userId, String status);
}
