package ru.yandex.practicum.shareit.item;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.shareit.user.User;

import java.util.List;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemDtoWithComments {
    Long id;
    String name;
    User owner;
    String description;
    Boolean available;
    Long timesUsed;
    List<Comment> comments;
}
