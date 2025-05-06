package shareit.item;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class ItemDtoJsonTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void serializeAndDeserialize_shouldMatchOriginalItemDto() throws JsonProcessingException {
        ItemDto dto = ItemDto.builder()
                .id(1L)
                .name("Drill")
                .description("Cordless drill")
                .available(true)
                .ownerId(2L)
                .requestId(3L)
                .timesUsed(7L)
                .build();

        String json = objectMapper.writeValueAsString(dto);
        ItemDto result = objectMapper.readValue(json, ItemDto.class);

        assertThat(result).isEqualTo(dto);
    }
}