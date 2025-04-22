package ru.yandex.practicum.shareit.booking;

import java.util.List;

public interface BookingService {
    Booking addBooking(BookingRequestDto bookingRequest, Long userId);

    Booking confirmStatus(Long bookingId, Boolean status, Long userId);

    Booking getInformation(Long bookingId, Long userId);

    List<Booking> getUserBookings(Long userId, String status);

    List<Booking> getItemsBookings(Long userId, String status);
}
