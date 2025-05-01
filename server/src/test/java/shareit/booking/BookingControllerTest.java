package shareit.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import shareit.item.Item;
import shareit.user.User;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookingController.class)
public class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingService bookingService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createBooking_shouldReturnBookingWithNestedItemAndUser() throws Exception {
        // Создаём входной объект BookingRequestDto с корректными полями
        BookingRequestDto input = new BookingRequestDto();
        input.setItemId(10L);
        input.setStart(LocalDateTime.now().plusDays(1));
        input.setEnd(LocalDateTime.now().plusDays(2));

        // Создаём объекты, используемые для формирования ответа
        User booker = new User(1L, "Booker", "booker@example.com");
        User owner = new User(2L, "Owner", "owner@example.com");
        Item item = new Item(10L, owner, "Item1", "d1",
                        true, 0L, null);

        // Формируем ответ сервиса: Booking с вложенными объектами
        Booking output = Booking.builder()
                .id(100L)
                .start(input.getStart())
                .end(input.getEnd())
                .item(item)
                .booker(booker)
                .status(Status.WAITING)
                .build();

        Mockito.when(bookingService.addBooking(Mockito.any(), Mockito.any()))
                .thenReturn(output);

        mockMvc.perform(post("/bookings")
                        .header("X-Sharer-User-Id", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(100L))
                .andExpect(jsonPath("$.item.id").value(10L))
                .andExpect(jsonPath("$.booker.id").value(1L))
                .andExpect(jsonPath("$.status").value("WAITING"));
    }
}