package ru.yandex.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.shareit.exception.BadRequestException;
import ru.yandex.practicum.shareit.exception.ConflictException;
import ru.yandex.practicum.shareit.exception.NotFoundException;
import ru.yandex.practicum.shareit.item.Item;
import ru.yandex.practicum.shareit.item.ItemRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final ItemRepository itemRepository;

    public Booking addBooking(Booking booking) {
        if (bookingRepository.findById(booking.getBookingId()).isPresent()) {
            throw new ConflictException("Booking already created");
        }
        Item item = itemRepository.findById(booking.getItem().getItemId())
                .orElseThrow(() -> new NotFoundException("Item for booking is not found"));
        List<Booking> bookingsForItem = bookingRepository
                .findByItemIdAndStartBetween(item.getItemId(), booking.getStartTime(), booking.getEndTime());
        LocalDateTime start = booking.getStartTime();
        LocalDateTime end = booking.getEndTime();
        boolean booked = bookingsForItem.stream()
                .noneMatch(b ->
                        !(b.getStartTime().isAfter(start) && b.getStartTime().isBefore(end))
                        && !(b.getEndTime().isAfter(start) && b.getEndTime().isBefore(end))
                );
        if (booked) {
            throw new ConflictException("Item is already booked at this time");
        } else {
            booking.setStatus(Status.WAITING);
            return bookingRepository.save(booking);
        }
    }

    public Booking confirmStatus(Long bookingId, Boolean status, Long userId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Booking with id " + bookingId + " not found"));
        Item item = itemRepository.findById(booking.getItem().getItemId())
                .orElseThrow(() -> new NotFoundException("Item with id" + booking.getItem().getItemId() + " not found"));
        if (item.getOwner().getUserId() != userId) {
            throw new BadRequestException("Only owner can confirm the booking of the item");
        }
        if (status) {
            booking.setStatus(Status.CONFIRMED);
        } else {
            booking.setStatus(Status.REJECTED);
        }
        bookingRepository.save(booking);
        return booking;
    }

    public Booking getInformation(Long bookingId, Long userId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Booking with id " + bookingId + " not found"));
        Item item = itemRepository.findById(booking.getItem().getItemId())
                .orElseThrow(() -> new NotFoundException("Item with id" + booking.getItem().getItemId() + " not found"));
        if (booking.getUserId() != userId && item.getOwner().getUserId() != userId) {
            throw new BadRequestException("Only owner or booker can view info about the booking");
        } else {
            return booking;
        }
    }

    public List<Booking> getUserBookings(Long userId, String status) {
        if (status == null || status.isEmpty() ||status.isBlank()) {
            return bookingRepository.findByUserIdOrderByStartTimeDesc(userId);
        } else {
            return bookingRepository.findByUserIdAndStatusOrderByStartTimeDesc(userId, status);
        }
    }

    public List<Booking> getItemsBookings(Long userId, String status) {  // Получение списка бронирований для всех// вещей текущего пользователя
        List<Booking> bookings = null;
        if (status == null || status.isEmpty() ||status.isBlank()) {
            List<Item> items = itemRepository.findAllByUserId(userId);
            if (items.isEmpty()) {
                throw new NotFoundException("User doesn't have any items");
            }
            for (Item i : items) {
                List<Booking> bookingsForItem = bookingRepository.findAllByItemId(i.getItemId());
                bookings.addAll(bookingsForItem);
            }
            return bookings;
        } else {
            List<Item> items = itemRepository.findAllByUserId(userId);
            if (items.isEmpty()) {
                throw new NotFoundException("User doesn't have any items");
            }
            for (Item i : items) {
                List<Booking> bookingsForItem = bookingRepository.findAllByUserIdAndStatus(i.getItemId(), status);
                bookings.addAll(bookingsForItem);
            }
            return bookings;
        }

    }
}
