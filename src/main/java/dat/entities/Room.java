package dat.entities;

import dat.dtos.RoomDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel; //TODO: make this shii and actual onetomany relationship with the entities
    private int roomNumber;
    private int price;

    public Room(RoomDTO roomDTO) {
//        this.hotel = roomDTO.getHotelId(); //TODO: figure out if this should be set somewhere else
        this.roomNumber = roomDTO.getRoomNumber();
        this.price = roomDTO.getPrice();
    }
}
