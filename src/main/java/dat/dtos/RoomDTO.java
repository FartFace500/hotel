package dat.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import dat.entities.Room;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoomDTO {
    @JsonProperty("id")
    private int id;
    private int hotelId;
    @JsonProperty("roomNumber")
    private int roomNumber;
    @JsonProperty("price")
    private int price;

    public RoomDTO(Room room) {
        this.id = room.getId();
        this.hotelId = room.getHotel().getId();
        this.roomNumber = room.getRoomNumber();
        this.price = room.getPrice();
    }

    public static List<RoomDTO> toDTOList(List<Room> rooms){
        return rooms.stream().map(RoomDTO::new).toList();
    }
}
