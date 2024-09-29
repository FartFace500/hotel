package dat.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dat.entities.Hotel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class HotelDTO {
    private Integer id;
    private String name;
    private String address;
    private List<RoomDTO> rooms;

    public HotelDTO(Hotel hotel){
        this.id = hotel.getId();
        this.name = hotel.getName();
        this.address = hotel.getAddress();
        this.rooms = RoomDTO.toDTOList(hotel.getRooms());
    }

    public HotelDTO(String name, String address, List<RoomDTO> rooms){
        this.name = name;
        this.address = address;
        this.rooms = rooms;
    }

    public static List<HotelDTO> toDTOList(List<Hotel> hotels){
        return hotels.stream().map(HotelDTO::new).toList();
    }
}
