package ru.yandex.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.shareit.booking.Booking;
import ru.yandex.practicum.shareit.booking.BookingRepository;
import ru.yandex.practicum.shareit.exception.NotFoundException;
import ru.yandex.practicum.shareit.user.User;
import ru.yandex.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;

    public Optional<ItemDto> addItem(ItemDto dto, Long ownerId) {
        if (userRepository.findById(ownerId).isEmpty()) {
            throw new NotFoundException("Non-existent user");
        }
        Item item = ItemDtoMapper.mapToModel(dto, userRepository.findById(ownerId).get());
        itemRepository.save(item);
        return Optional.of(ItemDtoMapper.mapToDto(item));
    }

    public Optional<Comment> addComment(Comment comment, Long itemId, Long userId) {
        Optional<Item> item = itemRepository.findById(itemId);
        if (item.isEmpty()) {
            return Optional.empty();
        }
        Optional<User> author = userRepository.findById(userId);
        if (author.isEmpty()) {
            throw new NotFoundException("User not found");
        }

        List<Booking> itemBookings = bookingRepository.findAllByItemIdAndUserId(itemId, userId);
        for (Booking b : itemBookings) {
            if (b.getEndTime().isBefore(LocalDateTime.now())) {
                comment.setItem(item.get());
                comment.setAuthor(author.get());
                commentRepository.save(comment);
                return Optional.of(comment);
            }
        }

        return Optional.empty();
    }

    public Optional<ItemDtoWithComments> getItem(Long itemId) {
        Optional<Item> foundItem = itemRepository.findById(itemId);
        if (foundItem.isEmpty()) {
            return Optional.empty();
        }
        List<Comment> comments = commentRepository.findAllByItemId(itemId);

        return Optional.of(ItemDtoMapper.mapToDtoWithComments(foundItem.get(), comments));
    }

    public List<ItemDtoWithBookingsAndComments> getUserItems(Long userId) {
        List<Item> items = itemRepository.findAllByOwnerId(userId);
        List<ItemDtoWithBookings> itemsWithBookings = new ArrayList<>();

        for (Item i : items) {
            List<Booking> itemBookings = bookingRepository.findAllByItemId(i.getId());
            if (itemBookings.isEmpty()) {
                continue;
            }
            Booking prev = itemBookings.get(0); // по умолчанию самая первая была до
            Booking next = itemBookings.get(itemBookings.size() - 1); // а самая последняя после
            for (Booking b : itemBookings) {
                if (b.getEndTime().isBefore(LocalDateTime.now()) && // если бронь уже завершена
                        b.getEndTime().isAfter(prev.getEndTime())) { // и завершена после текущей последней
                    prev = b;
                } else if (b.getStartTime().isAfter(LocalDateTime.now()) && // если бронь ещё не началась
                        b.getStartTime().isBefore(next.getStartTime())) { // и начнется раньше текущей следущей
                    next = b;
                }
            }
            itemsWithBookings.add(ItemDtoMapper.mapToDtoWithBookings(i, prev, next));
        }

        List<ItemDtoWithBookingsAndComments> itemsWithBookingsAndComments = new ArrayList<>();

        for (ItemDtoWithBookings i : itemsWithBookings) {
            List<Comment> comments = commentRepository.findAllByItemId(i.getId());
            itemsWithBookingsAndComments
                    .add(ItemDtoMapper.mapToDtoWithBookingsAndComments(i, comments));
        }

        return itemsWithBookingsAndComments;
    }

    public List<ItemDto> getItemsForRent(String text) {
        List<Item> items = itemRepository.findByDescriptionContainingIgnoreCase(text);
        return items.stream()
                .map(ItemDtoMapper::mapToDto)
                .toList();
    }

    public Optional<ItemDto> updateItem(ItemDto dto, Long itemId, Long userId) {
        Optional<Item> foundItem = itemRepository.findById(itemId);
        if (foundItem.isEmpty()) {
            throw new NotFoundException("Item is not found");
        }
        Item item = foundItem.get();
        if (!Objects.equals(item.getOwner().getId(), userId)) {
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
}
