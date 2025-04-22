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

        List<Booking> itemBookings = bookingRepository.findAllByItemIdAndBooker_Id(itemId, userId);
        for (Booking b : itemBookings) {
            if (b.getEnd().isBefore(LocalDateTime.now())) {
                comment.setItem(item.get());
                comment.setAuthor(author.get());
                commentRepository.save(comment);
                return Optional.of(comment);
            }
        }

        return Optional.empty();
    }

    public Optional<ItemDtoWithBookingsAndComments> getItem(Long itemId) {
        Optional<Item> foundItem = itemRepository.findById(itemId);
        if (foundItem.isEmpty()) {
            return Optional.empty();
        }
        List<Comment> comments = commentRepository.findAllByItemId(itemId);
        List<Booking> itemBookings = bookingRepository.findAllByItemId(itemId);

        if (itemBookings.isEmpty()) {
            return Optional.of(
                    ItemDtoMapper.mapToDtoWithBookingsAndComments(
                            foundItem.get(), comments, null, null));
        }
        Booking prevBooking = itemBookings.get(0);
        Booking nextBooking = itemBookings.get(itemBookings.size() - 1);
        return Optional.of(
                ItemDtoMapper.mapToDtoWithBookingsAndComments(
                        foundItem.get(), comments, prevBooking, nextBooking));
    }

    public List<ItemDtoWithBookingsAndComments> getUserItems(Long userId) {
        List<Item> items = itemRepository.findAllByOwnerId(userId);
        List<ItemDtoWithBookings> itemsWithBookings = new ArrayList<>();

        for (Item i : items) {
            List<Booking> itemBookings = bookingRepository.findAllByItemId(i.getId());
            if (itemBookings.isEmpty()) {
                itemsWithBookings.add(ItemDtoMapper.mapToDtoWithBookings(i, null, null));
                continue;
            }
            Booking prev = itemBookings.get(0); // по умолчанию самая первая была до
            Booking next = itemBookings.get(itemBookings.size() - 1); // а самая последняя после
            for (Booking b : itemBookings) {
                if (b.getEnd().isBefore(LocalDateTime.now()) && // если бронь уже завершена
                        b.getEnd().isAfter(prev.getEnd())) { // и завершена после текущей последней
                    prev = b;
                } else if (b.getStart().isAfter(LocalDateTime.now()) && // если бронь ещё не началась
                        b.getStart().isBefore(next.getStart())) { // и начнется раньше текущей следущей
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
        if (text == null || text.isBlank()) {
            return List.of(); // или выбросить BadRequestException, но по тестам надо вернуть
        }
        List<Item> items = itemRepository.findByDescriptionContainingIgnoreCase(text);
        return items.stream()
                .filter(Item::getAvailable)
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
