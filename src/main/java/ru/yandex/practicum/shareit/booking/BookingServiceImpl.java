package ru.yandex.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.shareit.exception.BadRequestException;
import ru.yandex.practicum.shareit.exception.NotFoundException;
import ru.yandex.practicum.shareit.item.Item;
import ru.yandex.practicum.shareit.item.ItemRepository;
import ru.yandex.practicum.shareit.user.User;
import ru.yandex.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    public Booking addBooking(BookingRequestDto bookingRequest, Long userId) {
        User booker = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User is not found"));
        Item item = itemRepository.findById(bookingRequest.getItemId())
                .orElseThrow(() -> new NotFoundException("Item for booking is not found"));
        if (!item.getAvailable()) {
            throw new BadRequestException("Item is not available");
        }
        if (bookingRequest.getEnd().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Booking ende in the past");
        }
        if (bookingRequest.getEnd().isEqual(bookingRequest.getStart())) {
            throw new BadRequestException("Booking starts and ends simultaneously");
        }
        if (bookingRequest.getEnd().isBefore(bookingRequest.getStart())) {
            throw new BadRequestException("Booking ends before it starts");
        }
        Booking booking = Booking.builder()
                .item(item)
                .booker(booker)
                .start(bookingRequest.getStart())
                .end(bookingRequest.getEnd())
                .build();
        List<Booking> bookingsForItem = bookingRepository
                .findByItemIdAndStartBetween(item.getId(), booking.getStart(), booking.getEnd());
        LocalDateTime start = booking.getStart();
        LocalDateTime end = booking.getEnd();
        boolean booked = bookingsForItem.stream()
                .anyMatch(existingBooking ->
                        (start.isBefore(existingBooking.getEnd())
                                && end.isAfter(existingBooking.getStart()))
                );
        if (booked) {
            throw new BadRequestException("Item is already booked at this time");
        } else {
            booking.setStatus(Status.WAITING);
            return bookingRepository.save(booking);
        }
    }

    public Booking confirmStatus(Long bookingId, Boolean status, Long userId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Booking with id " + bookingId + " not found"));
        Item item = itemRepository.findById(booking.getItem().getId())
                .orElseThrow(() -> new NotFoundException("Item with id" + booking.getItem().getId() + " not found"));
        if (!Objects.equals(item.getOwner().getId(), userId)) {
            throw new BadRequestException("Only owner can confirm the booking of the item");
        }
        if (status) {
            booking.setStatus(Status.APPROVED);
        } else {
            booking.setStatus(Status.REJECTED);
        }
        bookingRepository.save(booking);
        return booking;
    }

    public Booking getInformation(Long bookingId, Long userId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Booking with id " + bookingId + " not found"));
        Item item = itemRepository.findById(booking.getItem().getId())
                .orElseThrow(() -> new NotFoundException("Item with id" + booking.getItem().getId() + " not found"));
        if (!Objects.equals(booking.getBooker().getId(), userId)
                && !Objects.equals(item.getOwner().getId(), userId)) {
            throw new BadRequestException("Only owner or booker can view info about the booking");
        } else {
            return booking;
        }
    }

    public List<Booking> getUserBookings(Long userId, String status) {
        if (status == null || status.isBlank()) {
            return bookingRepository.findByBooker_IdOrderByStartDesc(userId);
        } else {
            return bookingRepository.findByBooker_IdAndStatusOrderByStartDesc(userId, status);
        }
    }

    public List<Booking> getItemsBookings(Long userId, String status) {  // Получение списка бронирований для всех// вещей текущего пользователя
        List<Booking> bookings = null;
        if (status == null || status.isBlank()) {
            List<Item> items = itemRepository.findAllByOwnerId(userId);
            if (items.isEmpty()) {
                throw new NotFoundException("User doesn't have any items");
            }
            for (Item i : items) {
                List<Booking> bookingsForItem = bookingRepository.findAllByItemId(i.getId());
                bookings.addAll(bookingsForItem);
            }
            return bookings;
        } else {
            List<Item> items = itemRepository.findAllByOwnerId(userId);
            if (items.isEmpty()) {
                throw new NotFoundException("User doesn't have any items");
            }
            for (Item i : items) {
                List<Booking> bookingsForItem = bookingRepository.findAllByBooker_IdAndStatus(i.getId(), status);
                bookings.addAll(bookingsForItem);
            }
            return bookings;
        }

    }
}
