package shareit.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemClient itemClient;
    private static final String xUserId = "X-Sharer-User-Id";

    @PostMapping
    public ResponseEntity<Object> addItem(@Valid @RequestBody ItemDto dto,
                                          @RequestHeader(name = xUserId) Long ownerId) {
        return itemClient.addItem(dto, ownerId);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> addComment(@Valid @RequestBody Comment comment,
                              @PathVariable Long itemId,
                              @RequestHeader(name = xUserId) Long userId) {
        return itemClient.addComment(comment, itemId, userId);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> updateItem(@Valid @RequestBody ItemDto dto,
                              @PathVariable Long itemId,
                              @RequestHeader(name = xUserId) Long userId) {
        return itemClient.updateItem(dto, itemId, userId);
    }

    @GetMapping
    public ResponseEntity<Object> getUserItems(@RequestHeader(name = xUserId) Long userId) {
        return itemClient.getUserItems(userId);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Object> getItem(@PathVariable Long itemId) {
        return itemClient.getItem(itemId);
    }

    @GetMapping("/search") // только доступные для аренды
    public ResponseEntity<Object> getItemsForRent(@RequestParam String text) {
        return itemClient.getItemsForRent(text);
    }
}
