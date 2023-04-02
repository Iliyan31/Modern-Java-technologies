package bg.sofia.uni.fmi.mjt.airbnb.filter;

import bg.sofia.uni.fmi.mjt.airbnb.accommodation.Bookable;
import bg.sofia.uni.fmi.mjt.airbnb.accommodation.location.Location;
import java.lang.Math;

public class LocationCriterion implements Criterion {
    private final Location currentLocation;
    private final double maxDistance;

    public LocationCriterion(Location currentLocation, double maxDistance) {
        this.currentLocation = currentLocation;
        this.maxDistance = maxDistance;
    }

    @Override
    public boolean check(Bookable bookable) {
        if(bookable == null || bookable.isBooked()) return false;
        if(bookable.getLocation() == null || this.currentLocation == null) return false;

        Location bookableLocation = bookable.getLocation();
        double x = bookableLocation.x() - currentLocation.x();
        double y = bookableLocation.y() - currentLocation.y();
        double distanceBetweenLocations = Math.sqrt(Math.pow(x,2) + Math.pow(y,2));

        return distanceBetweenLocations <= maxDistance;
    }
}
