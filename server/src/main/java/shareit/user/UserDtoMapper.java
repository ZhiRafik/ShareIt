package shareit.user;

import shareit.exception.BadRequestException;

public class UserDtoMapper {

    public static User mapToModel(UserDto dto) {
        User user = User.builder()
                .email(dto.getEmail())
                .name(dto.getName())
                .id(dto.getId())
                .build();
        if (user.getName() == null ||
                user.getEmail() == null ||
                user.getName().isBlank() ||
                user.getEmail().isBlank()) {
            throw new BadRequestException("Fields name and email cannot be empty");
        }
        return user;
    }

    public static UserDto mapToDto(User user) {
        UserDto dto = UserDto.builder()
                .email(user.getEmail())
                .name(user.getName())
                .id(user.getId())
                .build();
        return dto;
    }
}
