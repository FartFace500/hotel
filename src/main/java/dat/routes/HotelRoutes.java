package dat.routes;

import dat.controllers.HotelAndRoomController;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;


public class HotelRoutes {

    HotelAndRoomController hotelAndRoomController = new HotelAndRoomController();

    public EndpointGroup getRoutes(){
        return () -> {
            get("/", hotelAndRoomController::getHotels);
            get("/{id}",hotelAndRoomController::getById);
            get("/{id}/rooms",hotelAndRoomController::getHotelRooms);
            post("/", hotelAndRoomController::createHotels);
            post("/create", hotelAndRoomController::createHotel);
            put("/{id}",hotelAndRoomController::update);
            delete("/{id}",hotelAndRoomController::delete);
        };
    }

}
