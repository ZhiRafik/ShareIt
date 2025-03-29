package ru.yandex.practicum.shareit.item;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public class ItemRepository {
    private final List<Item> items = new ArrayList<>();
    private long id;

    public ItemDto addItem(Item item) {
        items.add(item);
        item.setItemId(newId());
        return ItemDtoMapper.mapToDto(item);
    }

    public Optional<Item> getItem(Long itemId) {
        for (Item item : items) {
            if (item.getItemId() == itemId) {
                return Optional.of(item);
            }
        }
        return Optional.empty();
    }

    public List<ItemDto> getUserItems(Long userId) {
        return items.stream()
                    .filter(item -> item.getOwnerId() == userId)
                    .map(ItemDtoMapper::mapToDto)
                    .toList();
    }

    public List<ItemDto> getItemsForRent(String text) {
        if (text.isBlank()) {
            return List.of();
        }
        List<ItemDto> itemsByDescriptions = items.stream()
                .filter(item -> item.getDescription().toLowerCase().contains(text.toLowerCase()))
                .filter(Item::getAvailable)
                .map(ItemDtoMapper::mapToDto)
                .toList();
        List<ItemDto> itemsByName = items.stream()
                .filter(item -> item.getName().toLowerCase().contains(text.toLowerCase()))
                .filter(Item::getAvailable)
                .map(ItemDtoMapper::mapToDto)
                .toList();
        return Stream.concat(itemsByDescriptions.stream(), itemsByName.stream())
                                    .toList();
    }

    private Long newId() {
        return ++id;
    }
}
