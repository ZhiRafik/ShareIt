package ru.yandex.practicum.shareit.item;

import ru.yandex.practicum.shareit.Exception.BadRequestException;

public class ItemDtoMapper {

    public static Item mapToModel(ItemDto dto, Long ownerId) {
        Item item = Item.builder()
                .itemId(dto.getId())
                .ownerId(ownerId)
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
        ItemDto itemDto = ItemDto.builder()
                .id(item.getItemId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .timesUsed(item.getTimesUsed())
                .build();
        return itemDto;
    }
}
