package ru.yandex.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.shareit.booking.Booking;
import ru.yandex.practicum.shareit.exception.BadRequestException;
import ru.yandex.practicum.shareit.user.UserRepository;

import java.util.List;

@RequiredArgsConstructor
public class ItemDtoMapper {

    public static Item mapToModel(ItemDto dto, Long ownerId) {
        Item item = Item.builder()
                .itemId(dto.getId())
                .owner(dto.getOwner())
                .name(dto.getName())
                .description(dto.getDescription())
                .available(dto.getAvailable())
                .timesUsed(dto.getTimesUsed())
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
                .id(item.getItemId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .timesUsed(item.getTimesUsed())
                .build();
    }

    public static ItemDtoWithBookings mapToDtoWithBookings(Item item, Booking prev, Booking next) {
        return ItemDtoWithBookings.builder()
                .id(item.getItemId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .timesUsed(item.getTimesUsed())
                .prevBooking(prev)
                .nextBooking(next)
                .build();
    }

    public static ItemDtoWithBookingsAndComments mapToDtoWithBookingsAndComments(ItemDtoWithBookings item,
                                                                                 List<Comment> comments) {
        return ItemDtoWithBookingsAndComments.builder()
                .comments(comments)
                .build();
    }

    public static ItemDtoWithComments mapToDtoWithComments(Item item, List<Comment> comments) {
        return ItemDtoWithComments.builder()
                .comments(comments)
                .build();
    }
}
