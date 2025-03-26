package ru.yandex.practicum.shareit.item;

import java.util.List;

public interface ItemService {
    ItemDto addItem(ItemDto dto, Long ownerId);

    ItemDto updateItem(ItemDto dto, Long itemId, Long userId);

    List<ItemDto> getUserItems(Long userId);

    List<ItemDto> getItemsForRent(String text);
}
