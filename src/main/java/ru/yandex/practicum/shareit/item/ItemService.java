package ru.yandex.practicum.shareit.item;

import java.util.List;
import java.util.Optional;

public interface ItemService {
    Optional<ItemDto> addItem(ItemDto dto, Long ownerId);

    Optional<ItemDto> updateItem(ItemDto dto, Long itemId, Long userId);

    Optional<ItemDtoWithComments> getItem(Long itemId);

    Optional<Comment> addComment(Comment comment, Long itemId, Long userId);

    List<ItemDtoWithBookingsAndComments> getUserItems(Long userId);

    List<ItemDto> getItemsForRent(String text);
}
