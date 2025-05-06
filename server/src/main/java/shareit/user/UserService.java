package shareit.user;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<UserDto> saveUser(UserDto dto);

    List<UserDto> getUsers();

    Optional<UserDto> getUser(Long id);

    Optional<UserDto> updateUser(UserDto dto, Long userId);

    Optional<UserDto> deleteUser(Long id);
}
