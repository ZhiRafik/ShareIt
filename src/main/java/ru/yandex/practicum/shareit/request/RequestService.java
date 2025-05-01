package ru.yandex.practicum.shareit.request;

import java.util.List;

public interface RequestService {
    ItemRequest addRequest(String description, Long userId);
    List<ItemRequest> getAllRequests(Long userId);
    List<ItemRequestWithAnswersDto> getUserRequestsWithAnswers(Long userId);
    ItemRequestWithAnswersDto getRequestWithAnswers(Long requestId);
}
