package shareit.item;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import shareit.booking.Booking;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemDtoWithBookings {
    Long id;
    String name;
    Long ownerId;
    String description;
    Boolean available;
    Long timesUsed;
    Booking lastBooking;
    Booking nextBooking;
}
