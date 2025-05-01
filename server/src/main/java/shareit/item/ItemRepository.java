package shareit.item;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByDescriptionContainingIgnoreCase(String text);

    List<Item> findAllByOwnerId(Long userId);

    List<Item> findAllByRequestId(Long requestId);
}
