package shareit.booking;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingClient bookingClient;
    private final String xUserId = "X-Sharer-User-Id";

    @PostMapping
    public ResponseEntity<Object> addBooking(@Valid @RequestBody BookingRequestDto bookingRequest,
                              @RequestHeader (name = xUserId) Long userId) {
        return bookingClient.addBooking(bookingRequest, userId);
    }

    @PatchMapping("/{bookingId}")
    public ResponseEntity<Object> confirmStatus(@PathVariable Long bookingId,
                                  @RequestParam Boolean approved,
                                  @RequestHeader (name = xUserId) Long userId) {
        return bookingClient.confirmStatus(bookingId, approved, userId);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<Object> getInformation(@PathVariable Long bookingId,
                                  @RequestHeader (name = xUserId) Long userId) {
        return bookingClient.getInformation(bookingId, userId);
    }

    @GetMapping
    public ResponseEntity<Object> getInformation(@RequestParam(required = false) String state,
                                                       @RequestHeader (name = xUserId) Long userId) {
        return bookingClient.getUserBookings(userId, state);
    }

    @GetMapping("/owner")
    public ResponseEntity<Object> getItemsBookings(@RequestParam(required = false) String state,
                                        @RequestHeader (name = xUserId) Long userId) {
        return bookingClient.getItemsBookings(userId, state);
    }
}
