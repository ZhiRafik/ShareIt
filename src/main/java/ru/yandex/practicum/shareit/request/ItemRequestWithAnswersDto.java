package ru.yandex.practicum.shareit.request;

import lombok.*;
import ru.yandex.practicum.shareit.item.Answer;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemRequestWithAnswersDto {
    Long requestId;
    String description;
    LocalDateTime created;
    List<Answer> answers;
}