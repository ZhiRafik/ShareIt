package shareit.booking;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class BookingRequestDtoJsonTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void serializeAndDeserialize_shouldMatchOriginalObject() throws JsonProcessingException {
        LocalDateTime start = LocalDateTime.of(2025, 5, 1, 12, 0);
        LocalDateTime end = LocalDateTime.of(2025, 5, 2, 12, 0);

        BookingRequestDto dto = new BookingRequestDto();
        dto.setItemId(1L);
        dto.setStart(start);
        dto.setEnd(end);

        String json = objectMapper.writeValueAsString(dto);
        BookingRequestDto result = objectMapper.readValue(json, BookingRequestDto.class);

        assertThat(result).isEqualTo(dto);
    }
}