package shareit.request;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import shareit.user.User;
import shareit.user.UserRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RequestServiceImplIntegrationTest {

    @Autowired
    private RequestServiceImpl requestService;

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void getUserRequests_shouldReturnRequestsOfUser() {
        User requester = userRepository.save(new User(1L, "req@example.com", "Requester"));
        requestService.addRequest("Need item", 1L);

        List<ItemRequestWithAnswersDto> requests
                = requestService.getUserRequestsWithAnswers(requester.getId());

        assertThat(requests).hasSize(1);
        assertThat(requests.get(0).getDescription()).isEqualTo("Need item");
    }
}