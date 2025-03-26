package ru.yandex.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.shareit.user.UserService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository repository;
    private final UserService userService;

    public Optional<ItemDto> addItem(ItemDto dto, Long ownerId) {
        if (userService.getUser(ownerId).isEmpty()) {
            return Optional.empty();
        }
        Item item = ItemDtoMapper.mapToModel(dto, ownerId);
        repository.addItem(item);
        return Optional.of(dto);
    }


    public Optional<ItemDto> updateItem(ItemDto dto, Long itemId, Long userId) {
        Item item = repository.getItem(itemId);
        if (item.getOwnerId() != userId) {
            return Optional.empty();
        }
        if (dto.getDescription() != null) {
            item.setDescription(dto.getDescription());
        }
        if (dto.getName() != null) {
            item.setName(dto.getName());
        }
        if (dto.getAvailable() != null) {
            item.setAvailable(dto.getAvailable());
        }
        return Optional.of(ItemDtoMapper.mapToDto(item));
    }

    public List<ItemDto> getUserItems(Long userId) {
        return repository.getUserItems(userId);
    }

    public List<ItemDto> getItemsForRent(String text) {
        return repository.getItemsForRent(text);
    }
}
