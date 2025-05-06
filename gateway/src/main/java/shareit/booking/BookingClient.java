package shareit.booking;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;

import shareit.client.BaseClient;

@Service
public class BookingClient extends BaseClient {
    private static final String API_PREFIX = "/bookings";

    @Autowired
    public BookingClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }

    // POST /bookings
    public ResponseEntity<Object> addBooking(BookingRequestDto requestDto, Long userId) {
        return post("", userId, requestDto);
    }

    // PATCH /bookings/{bookingId}?approved=true/false
    public ResponseEntity<Object> confirmStatus(Long bookingId, Boolean approved, Long userId) {
        Map<String, Object> parameters = Map.of("approved", approved);
        return patch("/" + bookingId + "?approved={approved}", userId, parameters, null);
    }

    // GET /bookings/{bookingId}
    public ResponseEntity<Object> getInformation(Long bookingId, Long userId) {
        return get("/" + bookingId, userId);
    }

    // GET /bookings?state=... (для пользователя, который бронировал)
    public ResponseEntity<Object> getUserBookings(Long userId, String state) {
        Map<String, Object> parameters = Map.of("state", state != null ? state : "ALL");
        return get("?state={state}", userId, parameters);
    }

    // GET /bookings/owner?state=... (для владельца вещей)
    public ResponseEntity<Object> getItemsBookings(Long userId, String state) {
        Map<String, Object> parameters = Map.of("state", state != null ? state : "ALL");
        return get("/owner?state={state}", userId, parameters);
    }
}
