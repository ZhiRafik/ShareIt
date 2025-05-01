package shareit.item;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import shareit.booking.Booking;
import shareit.user.User;

import java.util.List;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemDtoWithBookingsAndComments {
    Long id;
    String name;
    Long ownerId;
    String description;
    Boolean available;
    Long timesUsed;
    List<Comment> comments;
    Booking lastBooking;
    Booking nextBooking;
}
