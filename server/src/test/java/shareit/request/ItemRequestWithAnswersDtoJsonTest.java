package shareit.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class ItemRequestWithAnswersDtoJsonTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void serializeAndDeserialize_shouldMatchOriginalObject() throws JsonProcessingException {
        ItemRequestWithAnswersDto dto = new ItemRequestWithAnswersDto(1L, "Need item", LocalDateTime.now(), Collections.emptyList());
        String json = objectMapper.writeValueAsString(dto);
        ItemRequestWithAnswersDto result = objectMapper.readValue(json, ItemRequestWithAnswersDto.class);

        assertThat(result).isEqualTo(dto);
    }
}