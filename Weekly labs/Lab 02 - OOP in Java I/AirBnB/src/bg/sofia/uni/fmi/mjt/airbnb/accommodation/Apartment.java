package bg.sofia.uni.fmi.mjt.airbnb.accommodation;

import bg.sofia.uni.fmi.mjt.airbnb.accommodation.location.Location;

public class Apartment extends Building {
    private static int countApartments;

    public Apartment(Location location, double pricePerNight) {
        super(location,pricePerNight);
        this.setId("APA-" + (countApartments++));
    }

}
