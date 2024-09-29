package dat.controllers;

import dat.config.HibernateConfig;
import dat.daos.HotelDAO;
import dat.daos.RoomDAO;
import dat.dtos.HotelDTO;
import dat.dtos.RoomDTO;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import jakarta.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;


public class HotelAndRoomController {

    private final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("hotel");
    HotelDAO hotelDAO = HotelDAO.getInstance(emf);
    RoomDAO roomDAO = RoomDAO.getInstance(emf);

    private static final Logger logger = LoggerFactory.getLogger(HotelAndRoomController.class);
    private static final Logger debugLogger = LoggerFactory.getLogger("app");

    public HotelAndRoomController(){
    }

    public void getHotels(Context ctx){
        List<HotelDTO> hotelDTOS = hotelDAO.getAll();
        ctx.status(HttpStatus.OK);
        ctx.json(hotelDTOS);
    }

    public void getHotelRooms(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("id"));
        HotelDTO hotelDTO = hotelDAO.getById(id);
        List<RoomDTO> rooms = hotelDTO.getRooms();
        ctx.status(HttpStatus.OK);
        ctx.json(rooms);
    }

    public void getRooms(Context ctx){
        List<RoomDTO> roomDTOS = roomDAO.getAll();
        ctx.status(HttpStatus.OK);
        ctx.json(roomDTOS);
    }

    public void createHotels(Context ctx){
        // Modtag og konverter en liste af digte (fra json til dto)
        HotelDTO[] hotelDTOS = ctx.bodyAsClass(HotelDTO[].class);
        // Gem alle digtene i databasen (dao) og modtag en liste af de nye digte
        List<HotelDTO> newHotelDTOS = hotelDAO.createFromList(hotelDTOS);
        ctx.status(HttpStatus.CREATED);
        ctx.json(newHotelDTOS);
    }

    public void createHotel(Context ctx){
        debugLogger.debug("ERROR /hotel");
        HotelDTO hotelDTO = ctx.bodyAsClass(HotelDTO.class);
        HotelDTO newHotelDTO = hotelDAO.create(hotelDTO);
        ctx.status(HttpStatus.CREATED);
        ctx.json(newHotelDTO);
    }

    public void createRoom(Context ctx){
        debugLogger.debug("ERROR /room");
        RoomDTO roomDTO = ctx.bodyAsClass(RoomDTO.class);
        RoomDTO newRoomDTO = roomDAO.create(roomDTO);
        ctx.status(HttpStatus.CREATED);
        ctx.json(newRoomDTO);
    }

    public void delete(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("id"));

        RoomDTO fetchedRoom = roomDAO.getById(id);
        if (fetchedRoom != null) {
            roomDAO.delete(id);
            ctx.result("deleted: " + id);
            return;
        } else {
            ctx.result("No room found");
        }
        HotelDTO fetchedHotel = hotelDAO.getById(id);
        if (fetchedHotel != null) {
            hotelDAO.delete(id);
            ctx.result("deleted: " + id);
            return;
        } else {
            ctx.result("No hotel found");
        }
        ctx.status(HttpStatus.NOT_FOUND);
        ctx.result("No valid HotelDTO found in the body");
    }

    public void update(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("id"));

        RoomDTO fetchedRoom = roomDAO.getById(id);
        if (fetchedRoom != null) {
            ctx.json(fetchedRoom);
            fetchedRoom = roomDAO.update(id, fetchedRoom);
            return;
        } else {
            ctx.result("No room found");
        }
        HotelDTO fetchedHotel = hotelDAO.getById(id);
        if (fetchedHotel != null) {
            fetchedHotel = hotelDAO.update(id, fetchedHotel);
            ctx.json(fetchedHotel);
            return;
        } else {
            ctx.result("No hotel found");
        }
        ctx.status(HttpStatus.NOT_FOUND);
        ctx.result("No valid HotelDTO found in the body");
    }

    public void getById(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("id"));

        RoomDTO fetchedRoom = roomDAO.getById(id);
        if (fetchedRoom != null) {
            ctx.json(fetchedRoom);
            return;
        } else {
            ctx.result("No room found");
        }
        HotelDTO fetchedHotel = hotelDAO.getById(id);
        if (fetchedHotel != null) {
            ctx.json(fetchedHotel);
            return;
        } else {
            ctx.result("No hotel found");
        }
        ctx.status(HttpStatus.NOT_FOUND);
        ctx.result("No valid HotelDTO found in the body");
    }


}
