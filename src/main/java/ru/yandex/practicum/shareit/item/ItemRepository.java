package ru.yandex.practicum.shareit.item;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByUserId(Long userId);

    List<Item> findByDescriptionContainingIgnoreCase(String text);

    List<Item> findAllByUserId(Long userId);
}
