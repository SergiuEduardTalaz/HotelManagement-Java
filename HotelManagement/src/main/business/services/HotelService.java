package ro.fortech.academy.business.services;

import ro.fortech.academy.business.dto.HotelDto;
import ro.fortech.academy.business.entities.Hotel;
import ro.fortech.academy.persistence.HotelDao;

import java.util.List;
import java.util.stream.Collectors;

public class HotelService {

    private final HotelDao hotelDao;

    public HotelService(HotelDao hotelDao) {
        this.hotelDao = hotelDao;
    }

    public List<Hotel> getAllHotels() {
        List<Hotel> list = hotelDao.getAllHotels();
        return list.stream().sorted().collect(Collectors.toList());
    }

    public List<HotelDto> getHotelNames() {
        List<HotelDto> list = hotelDao.getAllHotelsName();
        return list.stream().sorted().collect(Collectors.toList());
    }

    public List<Hotel> getHotelServices(){
        List<Hotel> list = hotelDao.getAllHotelsServicesDao();
        return list.stream().sorted().collect(Collectors.toList());
    }

    public void updateHotelPhoneService(int hotelId, String phoneNumber){
        hotelDao.updateHotelPhoneDao(hotelId,phoneNumber);
    }

    public List<Hotel> searchServicesOfHotel(String hotelServices){
        return hotelDao.searchHotelServicesDao(hotelServices);
    }

}

