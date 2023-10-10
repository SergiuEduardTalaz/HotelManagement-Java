package ro.fortech.academy.presentation.hotel;

import ro.fortech.academy.business.entities.Hotel;
import ro.fortech.academy.business.services.HotelService;

import java.util.List;

public class HotelController {

    private final HotelView view;
    private final HotelModel model;
    private final HotelService service;

    public HotelController(HotelView view, HotelModel model, HotelService service) {
        this.view = view;
        this.model = model;
        this.service = service;
    }

    public void menuItemGetHotelDataPressed() {
        model.setHotelList(service.getAllHotels());
        view.showHotels(model.getHotelList());
    }

    public void updateHotelPhone(int hotelId, String phoneNumber) {
        service.updateHotelPhoneService(hotelId, phoneNumber);
    }

    public List<Hotel> getListOfHotelServices() {
        return service.getHotelServices();
    }

    public void searchHotelServicesController(String hotelServices) {
        List<Hotel> searchedHotel = service.searchServicesOfHotel(hotelServices);
        if (searchedHotel != null && !searchedHotel.isEmpty()) {
            model.setHotelList(searchedHotel);
            view.showHotels(model.getHotelList());
        }
    }
}
