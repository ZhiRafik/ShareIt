package shareit.booking;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import shareit.item.Item;
import shareit.item.ItemRepository;
import shareit.user.User;
import shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = shareit.ShareItServer.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookingServiceImplIntegrationTest {

    @Autowired
    private BookingServiceImpl bookingService;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    void getAllBookings_shouldReturnAllBookingsForUser() {
        User user = userRepository.save(new User(1L, "booker@example.com", "Booker"));
        User owner = userRepository.save(new User(2L, "owner@example.com", "Owner"));
        Item item = itemRepository.save(new Item(1L, owner, "Item", "Desc",
                                            true, 0L, null));
        bookingRepository.save(new Booking(1L, item, user, LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(2), Status.WAITING));

        List<Booking> bookings = bookingService.getUserBookings(user.getId(), null);

        assertThat(bookings).hasSize(1);
        assertThat(bookings.get(0).getBooker().getId()).isEqualTo(user.getId());
    }
}