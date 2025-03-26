package ru.yandex.practicum.shareit.item;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.ArrayList;

@Repository
public class ItemRepository {
    private final List<Item> items = new ArrayList<>();

    public ItemDto addItem(Item item) {
        items.add(item);
        return ItemDtoMapper.mapToDto(item);
    }

    public Item getItem(Long itemId) {
        for (Item item : items) {
            if (item.getItemId() == itemId) {
                return item;
            }
        }
        return null;
    }

    public List<ItemDto> getUserItems(Long userId) {
        return items.stream()
                    .filter(item -> item.getOwnerId() == userId)
                    .map(ItemDtoMapper::mapToDto)
                    .toList();
    }

    public List<ItemDto> getItemsForRent(String text) {
        return items.stream()
                .filter(item -> item.getDescription().contains(text))
                .filter(item -> item.getAvailable())
                .map(ItemDtoMapper::mapToDto)
                .toList();
    }
}
