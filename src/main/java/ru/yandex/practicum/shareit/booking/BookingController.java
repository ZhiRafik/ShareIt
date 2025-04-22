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
    public Booking addBooking(@Valid @RequestBody BookingRequestDto bookingRequest,
                              @RequestHeader (name = "X-Sharer-User-Id") Long userId) {
        return service.addBooking(bookingRequest, userId);
    }

    @PatchMapping("/{bookingId}")
    public Booking confirmStatus(@PathVariable Long bookingId,
                                  @RequestParam Boolean approved,
                                  @RequestHeader (name = "X-Sharer-User-Id") Long userId) {
        return service.confirmStatus(bookingId, approved, userId);
    }

    @GetMapping("/{bookingId}")
    public Booking getInformation(@PathVariable Long bookingId,
                                  @RequestHeader (name = "X-Sharer-User-Id") Long userId) {
        return service.getInformation(bookingId, userId);
    }

    @GetMapping
    public List<Booking> getInformation(@RequestParam(required = false) String state,
                                        @RequestHeader (name = "X-Sharer-User-Id") Long userId) {
        return service.getUserBookings(userId, state);
    }

    @GetMapping("/owner")
    public List<Booking> getItemsBookings(@RequestParam(required = false) String state,
                                        @RequestHeader (name = "X-Sharer-User-Id") Long userId) {
        return service.getItemsBookings(userId, state);
    }
}
