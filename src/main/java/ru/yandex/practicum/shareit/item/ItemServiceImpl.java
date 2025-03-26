package ru.yandex.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.shareit.Exception.NotFoundException;
import ru.yandex.practicum.shareit.user.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository repository;
    private final UserRepository userRepository;

    public Optional<ItemDto> addItem(ItemDto dto, Long ownerId) {
        if (userRepository.getUser(ownerId).isEmpty()) {
            throw new NotFoundException("Non-existent user");
        }
        Item item = ItemDtoMapper.mapToModel(dto, ownerId);
        repository.addItem(item);
        return Optional.of(ItemDtoMapper.mapToDto(item));
    }

    public Optional<ItemDto> getItem(Long itemId) {
        Optional<Item> foundItem = repository.getItem(itemId);
        if (foundItem.isEmpty()) {
            return Optional.empty();
        }
        ItemDto dto = ItemDtoMapper.mapToDto(foundItem.get());
        return Optional.of(dto);
    }


    public Optional<ItemDto> updateItem(ItemDto dto, Long itemId, Long userId) {
        Optional<Item> foundItem = repository.getItem(itemId);
        if (foundItem.isEmpty()) {
            throw new NotFoundException("Item is not found");
        }
        Item item = foundItem.get();
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
