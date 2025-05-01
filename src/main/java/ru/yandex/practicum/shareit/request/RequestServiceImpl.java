package ru.yandex.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.shareit.exception.NotFoundException;
import ru.yandex.practicum.shareit.item.Answer;
import ru.yandex.practicum.shareit.item.Item;
import ru.yandex.practicum.shareit.item.ItemRepository;
import ru.yandex.practicum.shareit.user.User;
import ru.yandex.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    public ItemRequest addRequest(String description, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User is not found"));
        ItemRequest request = ItemRequest.builder()
                .description(description)
                .userId(userId)
                .created(LocalDateTime.now())
                .build();
        return requestRepository.save(request);
    }

    public List<ItemRequestWithAnswersDto> getUserRequestsWithAnswers(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User is not found"));
        List<ItemRequest> requests = requestRepository.findAllByUserIdOrderByCreatedDesc(userId);
        List<ItemRequestWithAnswersDto> result = new ArrayList<>();

        for (ItemRequest itemRequest : requests) {
            List<Item> items = itemRepository.findAllByRequestId(itemRequest.getRequestId());
            List<Answer> answers = items.stream()
                    .map(item -> Answer.builder()
                            .ownerId(item.getOwner().getId())
                            .itemId(item.getId())
                            .name(item.getName())
                            .build())
                    .toList();

            result.add(ItemRequestWithAnswersDto.builder()
                            .requestId(itemRequest.getRequestId())
                            .description(itemRequest.getDescription())
                            .created(itemRequest.getCreated())
                            .answers(answers)
                            .build());
        }
        return result;
    }

    public List<ItemRequest> getAllRequests(Long userId) {
        User user = userRepository.findById(userId)
                .orElse(null);
        List<ItemRequest> requests;
        if (user != null) {
            return requestRepository.findAllByUserIdNotOrderByCreatedDesc(userId);
        } else {
            return requestRepository.findAll();
        }
    }

    public ItemRequestWithAnswersDto getRequestWithAnswers(Long requestId) {
        ItemRequest itemRequest = requestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("Request not found"));
        List<Item> items = itemRepository.findAllByRequestId(requestId);

        List<Answer> answers = items.stream()
                .map(item -> Answer.builder()
                        .ownerId(item.getOwner().getId())
                        .itemId(item.getId())
                        .name(item.getName())
                        .build())
                .toList();

        ItemRequestWithAnswersDto answersDto = ItemRequestWithAnswersDto.builder()
                .requestId(requestId)
                .description(itemRequest.getDescription())
                .created(itemRequest.getCreated())
                .answers(answers)
                .build();

        return answersDto;
    }
}
































