package ru.yandex.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository repository;

    public ItemDto addItem(ItemDto dto, Long ownerId) {
        Item item = ItemDtoMapper.mapToModel(dto, ownerId);
        return repository.addItem(item);
    }


    public ItemDto updateItem(ItemDto dto, Long itemId, Long userId) {
        Item item = repository.getItem(itemId);
        if (item.getOwnerId() != userId) {
            return null;
        }
        item.setDescription(dto.getDescription());
        item.setName(dto.getName());
        item.setAvailable(dto.getAvailable());
        return ItemDtoMapper.mapToDto(item);
    }

    public List<ItemDto> getUserItems(Long userId) {
        return repository.getUserItems(userId);
    }

    public List<ItemDto> getItemsForRent(String text) {
        return repository.getItemsForRent(text);
    }
}
