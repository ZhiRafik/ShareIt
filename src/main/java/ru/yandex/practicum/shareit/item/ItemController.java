package ru.yandex.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService service;

    @PostMapping
    public ItemDto addItem(@Valid @RequestBody ItemDto dto, @RequestHeader Long ownerId) {
        return service.addItem(dto, ownerId);
    }

    @PatchMapping("/{itemId}")
    public ItemDto updateItem(@Valid @RequestBody ItemDto dto,
                              @PathVariable Long itemId,
                              @RequestHeader Long userId) {
        return service.updateItem(dto, itemId, userId);
    }

    @GetMapping
    public List<ItemDto> getUserItems(@RequestHeader Long userId) {
        return service.getUserItems(userId);
    }

    @GetMapping("/search?text={text}") // только доступные для аренды
    public List<ItemDto> getItemsForRent(@PathVariable String text) {
        return service.getItemsForRent(text);
    }
}
