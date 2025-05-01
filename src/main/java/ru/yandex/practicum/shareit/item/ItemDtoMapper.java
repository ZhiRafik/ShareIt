package ru.yandex.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.shareit.booking.Booking;
import ru.yandex.practicum.shareit.exception.BadRequestException;
import ru.yandex.practicum.shareit.user.User;

import java.util.List;

@RequiredArgsConstructor
public class ItemDtoMapper {

    public static Item mapToModel(ItemDto dto, User owner) {
        Item item = Item.builder()
                .id(dto.getId())
                .owner(owner)
                .name(dto.getName())
                .description(dto.getDescription())
                .available(dto.getAvailable())
                .timesUsed(dto.getTimesUsed())
                .requestId(dto.getRequestId())
                .build();
        if (item.getAvailable() == null ||
                item.getDescription() == null ||
                item.getName() == null ||
                item.getName().isBlank() ||
                item.getDescription().isBlank()) {
            throw new BadRequestException("Fields available, description and name cannot be empty");
        }
        return item;
    }

    public static ItemDto mapToDto(Item item) {
        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .timesUsed(item.getTimesUsed())
                .requestId(item.getRequestId())
                .build();
    }

    public static ItemDtoWithBookings mapToDtoWithBookings(Item item, Booking last, Booking next) {
        return ItemDtoWithBookings.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .timesUsed(item.getTimesUsed())
                .lastBooking(last)
                .nextBooking(next)
                .build();
    }

    public static ItemDtoWithBookingsAndComments mapToDtoWithBookingsAndComments(ItemDtoWithBookings item,
                                                                                 List<Comment> comments) {
        return ItemDtoWithBookingsAndComments.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .timesUsed(item.getTimesUsed())
                .comments(comments)
                .build();
    }

    public static ItemDtoWithBookingsAndComments mapToDtoWithBookingsAndComments(Item item, List<Comment> comments,
                                                                                 Booking last, Booking next) {
        return ItemDtoWithBookingsAndComments.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .timesUsed(item.getTimesUsed())
                .comments(comments)
                .nextBooking(next)
                .lastBooking(last)
                .build();
    }

    public static ItemDtoWithComments mapToDtoWithComments(Item item, List<Comment> comments) {
        return ItemDtoWithComments.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .timesUsed(item.getTimesUsed())
                .comments(comments)
                .build();
    }
}
