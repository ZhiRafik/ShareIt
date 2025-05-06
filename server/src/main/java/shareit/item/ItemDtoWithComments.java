package shareit.item;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemDtoWithComments {
    Long id;
    String name;
    Long ownerId;
    String description;
    Boolean available;
    Long timesUsed;
    List<Comment> comments;
}
