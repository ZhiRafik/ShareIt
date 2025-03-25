package ru.yandex.practicum.shareit.item;

public class ItemDtoMapper {

    public Item mapToModel(ItemDto dto) {
        Item item = Item.builder()
                .itemId(dto.getItemId())
                .name(dto.getName())
                .description(dto.getDescription())
                .available(dto.getAvailable())
                .timesUsed(dto.getTimesUsed())
                .build();
        return item;
    }

    public ItemDto mapToDto(Item item) {
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
