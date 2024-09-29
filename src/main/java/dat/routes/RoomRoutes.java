package dat.routes;

import dat.controllers.HotelAndRoomController;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class RoomRoutes {

    HotelAndRoomController hotelAndRoomController = new HotelAndRoomController();

    public EndpointGroup getRoutes(){
        return () -> {
            get("/", hotelAndRoomController::getRooms);
            get("/{id}", hotelAndRoomController::getById);
            post("/create", hotelAndRoomController::createRoom);
            put("/{id}", hotelAndRoomController::update);
            delete("/{id}", hotelAndRoomController::delete);
        };
    }
}
