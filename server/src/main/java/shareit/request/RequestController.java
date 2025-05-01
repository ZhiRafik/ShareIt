package shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/requests")
@RequiredArgsConstructor
public class RequestController {
    private final RequestService service;
    private final String xUserId = "X-Sharer-User-Id";

    @PostMapping
    public ItemRequest addRequest(String description,
                                  @RequestHeader(name = xUserId) Long userId) {
        return service.addRequest(description, userId);
    }

    @GetMapping
    public List<ItemRequestWithAnswersDto> getUserRequestsWithAnswers(@RequestHeader(name = xUserId) Long userId) {
        return service.getUserRequestsWithAnswers(userId);
    }

    @GetMapping("/all")
    public List<ItemRequest> getAllRequests(@RequestHeader(name = xUserId, required = false) Long userId) {
        return service.getAllRequests(userId);
    }

    @GetMapping("/{requestId}")
    public ItemRequestWithAnswersDto getRequestWithAnswers(Long requestId) {
        return service.getRequestWithAnswers(requestId);
    }
}
