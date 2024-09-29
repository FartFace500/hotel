package dat.daos;

import dat.dtos.HotelDTO;
import dat.entities.Hotel;
import dat.entities.Room;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;

public class HotelDAO implements IDAO<HotelDTO> {

    private static HotelDAO instance;
    private static EntityManagerFactory emf;

    private HotelDAO() {
        // private constructor
    }

    public static HotelDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            instance = new HotelDAO();
            emf = _emf;
        }
        return instance;
    }
    @Override
    public List<HotelDTO> getAll(){
        List<Hotel> hotelList = new ArrayList<>();
        try(EntityManager em = emf.createEntityManager()){
            TypedQuery<Hotel> query = em.createQuery("SELECT p FROM Hotel p", Hotel.class);
            return HotelDTO.toDTOList(query.getResultList());
        }
    }
    @Override
    public HotelDTO create(HotelDTO hotelDTO) {
        Hotel hotel = new Hotel(hotelDTO);
        try (EntityManager em = emf.createEntityManager()){
          em.getTransaction().begin();
          em.persist(hotel);
          em.getTransaction().commit();
        }
        return new HotelDTO(hotel);
    }

    public List<HotelDTO> createFromList(HotelDTO[] hotelDTOS) {
        List<HotelDTO> hotelDTOList = new ArrayList<>();
        for (int index = 0; index < hotelDTOS.length ; index++) {
            HotelDTO newHotelDTO = create(hotelDTOS[index]);
            hotelDTOList.add(newHotelDTO);
        }
        return hotelDTOList;
    }
    @Override
    public HotelDTO getById(int id){
        try (EntityManager em = emf.createEntityManager()){
            Hotel hotel = em.find(Hotel.class, id);
            if (hotel != null){
                return new HotelDTO(hotel);
            }
            return null;
        }
    }
    @Override
    public void delete(int id){
        try (EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            Hotel hotel = em.find(Hotel.class, id);
            if (hotel != null){
                em.remove(hotel);
            }
            em.getTransaction().commit();
        }
    }
    @Override
    public HotelDTO update(int id, HotelDTO hotelDTO){
        try (EntityManager em = emf.createEntityManager()) {
            Hotel hotel = em.find(Hotel.class, id);
            if (hotel != null) {
                em.getTransaction().begin();
                hotel.setName(hotelDTO.getName());
                hotel.setAddress(hotelDTO.getAddress());
                hotel.setRooms(hotelDTO.getRooms().stream().map(roomDTO -> new Room(roomDTO)).toList());
                em.getTransaction().commit();
            };
            return new HotelDTO(hotel);
        }
    }
}
