package dat.daos;

import dat.dtos.RoomDTO;
import dat.entities.Hotel;
import dat.entities.Room;
import jakarta.persistence.EntityManagerFactory;

import java.util.ArrayList;
import java.util.List;

public class RoomDAO implements IDAO<RoomDTO> {

    private static RoomDAO instance;
    private static EntityManagerFactory emf;

    private RoomDAO() {
        // private constructor
    }

    public static RoomDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            instance = new RoomDAO();
            emf = _emf;
        }
        return instance;
    }

    @Override
    public List<RoomDTO> getAll() {
        List<Room> rooms = new ArrayList<>();
        try(var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("SELECT r FROM Room r", Room.class).getResultList();
        }
        return RoomDTO.toDTOList(rooms);
    }

    @Override
    public RoomDTO getById(int id) {
        try(var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Room room = em.find(Room.class, id);
            if(room != null) {
            return new RoomDTO(room);
            } else {
                return null;
            }
        }
    }

    @Override
    public RoomDTO create(RoomDTO entity) {
        try(var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
            return entity;
        }
    }

    @Override
    public RoomDTO update(int id, RoomDTO entity) {
        try(var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Room room = em.find(Room.class, id);
            if (room != null) {
                room.setHotel(em.find(Hotel.class,entity.getHotelId()));
                room.setRoomNumber(entity.getRoomNumber());
                room.setPrice(entity.getPrice());
                em.getTransaction().commit();
            }
            return new RoomDTO(room);
        }
    }

    @Override
    public void delete(int id) {
        try(var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Room room = em.find(Room.class, id);
            if (room != null) {
            em.remove(room);
            }
            em.getTransaction().commit();
        }
    }
}
