package shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/requests")
@RequiredArgsConstructor
public class RequestController {
    private final RequestClient requestClient;
    private static final String xUserId = "X-Sharer-User-Id";

    @PostMapping
    public ResponseEntity<Object> addRequest(String description,
                                             @RequestHeader(name = xUserId) Long userId) {
        return requestClient.addRequest(description, userId);
    }

    @GetMapping
    public ResponseEntity<Object> getUserRequestsWithAnswers(@RequestHeader(name = xUserId) Long userId) {
        return requestClient.getUserRequestsWithAnswers(userId);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllRequests(@RequestHeader(name = xUserId, required = false) Long userId) {
        return requestClient.getAllRequests(userId);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> getRequestWithAnswers(Long requestId) {
        return requestClient.getRequestWithAnswers(requestId);
    }
}
