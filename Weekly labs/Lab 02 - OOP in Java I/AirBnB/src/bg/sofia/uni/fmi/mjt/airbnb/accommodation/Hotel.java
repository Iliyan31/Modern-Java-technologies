package bg.sofia.uni.fmi.mjt.airbnb.accommodation;

import bg.sofia.uni.fmi.mjt.airbnb.accommodation.location.Location;

public class Hotel extends Building {
    private static int countHotels;

    public Hotel(Location location, double pricePerNight) {
        super(location, pricePerNight);
        this.setId("HOT-" + (countHotels++));
    }

}
