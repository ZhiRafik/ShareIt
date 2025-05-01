package shareit.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import shareit.exception.BadRequestException;
import shareit.exception.NotFoundException;

import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService service;
    private final String xUserId = "X-Sharer-User-Id";

    @PostMapping
    public ItemDto addItem(@Valid @RequestBody ItemDto dto,
                           @RequestHeader(name = xUserId) Long ownerId) {
        return service.addItem(dto, ownerId)
                .orElseThrow(() -> new NotFoundException("Item not found"));
    }

    @PostMapping("/{itemId}/comment")
    public Comment addComment(@Valid @RequestBody Comment comment,
                              @PathVariable Long itemId,
                              @RequestHeader(name = xUserId) Long userId) {
        return service.addComment(comment, itemId, userId)
                .orElseThrow(() -> new BadRequestException("Item with ended booking not found"));
    }

    @PatchMapping("/{itemId}")
    public ItemDto updateItem(@Valid @RequestBody ItemDto dto,
                              @PathVariable Long itemId,
                              @RequestHeader(name = xUserId) Long userId) {
        return service.updateItem(dto, itemId, userId)
                .orElseThrow(() -> new NotFoundException("Item not found"));
    }

    @GetMapping
    public List<ItemDtoWithBookingsAndComments> getUserItems(@RequestHeader(name = xUserId) Long userId) {
        return service.getUserItems(userId);
    }

    @GetMapping("/{itemId}")
    public ItemDtoWithBookingsAndComments getItem(@PathVariable Long itemId) {
        return service.getItem(itemId)
                .orElseThrow(() -> new NotFoundException("Item not found"));
    }

    @GetMapping("/search") // только доступные для аренды
    public List<ItemDto> getItemsForRent(@RequestParam String text) {
        return service.getItemsForRent(text);
    }
}
