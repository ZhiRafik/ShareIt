package shareit.request;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestRepository extends JpaRepository<ItemRequest, Long> {

    List<ItemRequest> findAllByUserIdNotOrderByCreatedDesc(Long userId);

    List<ItemRequest> findAllByUserIdOrderByCreatedDesc(Long userId);
}
