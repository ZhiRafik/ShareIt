package ru.yandex.practicum.shareit.booking;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService service;

    @PostMapping
    public Booking addBooking(@Valid @RequestBody BookingRequestDto bookingRequest) {
        return service.addBooking(bookingRequest);
    }

    @PatchMapping("/{bookingId}?approved={approved}")
    public Booking confirmStatus(@PathVariable Long bookingId,
                                  @PathVariable Boolean status,
                                  @RequestHeader (name = "X-Sharer-User-Id") Long userId) {
        return service.confirmStatus(bookingId, status, userId);
    }

    @GetMapping("/{bookingId}")
    public Booking getInformation(@PathVariable Long bookingId,
                                  @RequestHeader (name = "X-Sharer-User-Id") Long userId) {
        return service.getInformation(bookingId, userId);
    }

    @GetMapping("/?state={state}")
    public List<Booking> getInformation(@PathVariable(required = false) String status,
                                        @RequestHeader (name = "X-Sharer-User-Id") Long userId) {
        return service.getUserBookings(userId, status);
    }

    @GetMapping("/owner?state={state}")
    public List<Booking> getItemsBookings(@PathVariable(required = false) String status,
                                        @RequestHeader (name = "X-Sharer-User-Id") Long userId) {
        return service.getItemsBookings(userId, status);
    }
}
