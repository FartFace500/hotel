package dat.entities;

import dat.dtos.HotelDTO;
import dat.dtos.RoomDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="name", length = 100, nullable = false)
    private String name;
    @Column(name="address", length = 1000, nullable = false)
    private String address;
    @Column(name="rooms", nullable = false)
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.PERSIST)
    private List<Room> rooms = new ArrayList<>();

    // make a constructor that takes a PoemDTO object as parameter and initializes the fields.
    public Hotel(HotelDTO hotelDTO){
        this.id = hotelDTO.getId();
        this.name = hotelDTO.getName();
        this.address = hotelDTO.getAddress();
        List<Room> rooms =  hotelDTO.getRooms().stream().map(Room::new).toList();
        this.addRooms(rooms);
    }

    public void addRooms(List<Room> rooms){
        if(this.rooms.isEmpty() && rooms != null){
            for(Room room : rooms){
                this.rooms.add(room);
                room.setHotel(this);
            }
        }
    }


}
