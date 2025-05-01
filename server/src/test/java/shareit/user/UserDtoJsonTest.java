package shareit.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class UserDtoJsonTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void serializeAndDeserialize_shouldMatchOriginalObject() throws JsonProcessingException {
        UserDto dto = new UserDto("alice@example.com", "Alice", 1L);
        String json = objectMapper.writeValueAsString(dto);
        UserDto result = objectMapper.readValue(json, UserDto.class);

        assertThat(result).isEqualTo(dto);
    }
}