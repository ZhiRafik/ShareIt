package shareit.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserServiceImplIntegrationTest {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void getAllUsers_shouldReturnAllUsers() {
        userRepository.save(new User(null, "user1@example.com", "User1"));
        userRepository.save(new User(null, "user2@example.com", "User2"));

        List<UserDto> users = userService.getUsers();

        assertThat(users).hasSize(2);
        assertThat(users).extracting(UserDto::getEmail)
                .containsExactlyInAnyOrder("user1@example.com", "user2@example.com");
    }
}