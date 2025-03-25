package ru.yandex.practicum.shareit.user;

public class UserDtoMapper {

    public User mapToModel(UserDto dto) {
        User user = User.builder()
                .userId(dto.getUserId())
                .email(dto.getEmail())
                .name(dto.getName())
                .build();
        return user;
    }

    public UserDto mapToDto(User user) {
        UserDto dto = UserDto.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
        return dto;
    }
}
