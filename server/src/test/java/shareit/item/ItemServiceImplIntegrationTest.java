package shareit.item;

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
public class ItemServiceImplIntegrationTest {

    @Autowired
    private ItemServiceImpl itemService;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void getUserItems_shouldReturnItemsOfUser() {
        User user = new User(1L, "user1@example.com", "User1");
        user = userRepository.save(user);

        Item item1 = new Item(1L, user, "Item1", "Desc1",
                true, 0L, null);
        Item item2 = new Item(2L, user, "Item2", "Desc2",
                true, 0L, null);
        itemRepository.save(item1);
        itemRepository.save(item2);

        List<ItemDtoWithBookingsAndComments> result = itemService.getUserItems(user.getId());

        assertThat(result).hasSize(2);
        assertThat(result).extracting(ItemDtoWithBookingsAndComments::getName)
                                .containsExactlyInAnyOrder("Item1", "Item2");
    }
}