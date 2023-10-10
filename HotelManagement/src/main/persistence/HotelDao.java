package ro.fortech.academy.persistence;

import ro.fortech.academy.business.dto.HotelDto;
import ro.fortech.academy.business.entities.Hotel;

import java.util.List;

public interface HotelDao {

    List<Hotel> getAllHotels();
    List<HotelDto> getAllHotelsName();
    void updateHotelPhoneDao(int hotelId, String phoneNumber);
    List<Hotel> getAllHotelsServicesDao();
    List<Hotel> searchHotelServicesDao(String hotelServices);
}
