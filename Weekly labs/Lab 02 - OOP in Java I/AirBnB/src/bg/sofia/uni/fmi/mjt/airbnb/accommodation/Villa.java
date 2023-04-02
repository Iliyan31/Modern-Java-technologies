package bg.sofia.uni.fmi.mjt.airbnb.accommodation;

import bg.sofia.uni.fmi.mjt.airbnb.accommodation.location.Location;

public class Villa extends Building {
    private static int countVillas;

    public Villa(Location location, double pricePerNight) {
        super(location, pricePerNight);
        this.setId("VIL-" + (countVillas++));
    }

}
