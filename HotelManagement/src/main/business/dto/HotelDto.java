package ro.fortech.academy.business.dto;

public class HotelDto implements Comparable<HotelDto>  {
    private String name;
    private int id;

    public HotelDto(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(HotelDto o) {
        return this.name.compareTo(o.name);
    }
}
