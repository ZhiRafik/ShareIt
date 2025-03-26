package ru.yandex.practicum.shareit.item;

public class ItemDtoMapper {

    public static Item mapToModel(ItemDto dto, Long ownerId) {
        Item item = Item.builder()
                .itemId(dto.getItemId())
                .ownerId(ownerId)
                .name(dto.getName())
                .description(dto.getDescription())
                .available(dto.getAvailable())
                .timesUsed(dto.getTimesUsed())
                .build();
        return item;
    }

    public static ItemDto mapToDto(Item item) {
        ItemDto itemDto = ItemDto.builder()
                                .itemId(item.getItemId())
                                .name(item.getName())
                                .description(item.getDescription())
                                .available(item.getAvailable())
                                .timesUsed(item.getTimesUsed())
                                .build();
        return itemDto;
    }
}
