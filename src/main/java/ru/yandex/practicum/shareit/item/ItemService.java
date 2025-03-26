package ru.yandex.practicum.shareit.item;

import java.util.List;
import java.util.Optional;

public interface ItemService {
    Optional<ItemDto> addItem(ItemDto dto, Long ownerId);

    Optional<ItemDto> updateItem(ItemDto dto, Long itemId, Long userId);

    Optional<ItemDto> getItem(Long itemId);

    List<ItemDto> getUserItems(Long userId);

    List<ItemDto> getItemsForRent(String text);
}
