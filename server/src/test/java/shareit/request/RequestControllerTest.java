package shareit.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import shareit.user.User;
import shareit.user.UserRepository;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RequestController.class)
public class RequestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RequestService requestService;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createRequest_shouldReturnRequest() throws Exception {
        userRepository.save(new User(null, "User1", "user1@example.com"));
        ItemRequest inputDto = new ItemRequest(null, 1L, "Need item", null);
        ItemRequest outputDto = new ItemRequest(1L, 1L, "Need item", LocalDateTime.now());

        Mockito.when(requestService.addRequest(Mockito.any(), Mockito.any()))
                .thenReturn(outputDto);

        mockMvc.perform(post("/requests")
                        .header("X-Sharer-User-Id", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.requestId").value(1L))
                .andExpect(jsonPath("$.description").value("Need item"));
    }
}