package ru.yandex.practicum.shareit.user;

public class UserDtoMapper {

    public static User mapToModel(UserDto dto) {
        User user = User.builder()
                .email(dto.getEmail())
                .name(dto.getName())
                .build();
        return user;
    }

    public static UserDto mapToDto(User user) {
        UserDto dto = UserDto.builder()
                .email(user.getEmail())
                .name(user.getName())
                .build();
        return dto;
    }
}
