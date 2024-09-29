package dat.entities;

import dat.dtos.RoomDTO;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int hotelId; //TODO: make this shii and acntual onetomany relationship with teh entities
    private int roomNumber;
    private int price;

    public Room(RoomDTO roomDTO) {
        this.hotelId = roomDTO.getHotelId();
        this.roomNumber = roomDTO.getRoomNumber();
        this.price = roomDTO.getPrice();
    }
}
