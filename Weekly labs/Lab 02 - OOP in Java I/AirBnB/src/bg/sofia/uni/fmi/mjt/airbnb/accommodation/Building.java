package bg.sofia.uni.fmi.mjt.airbnb.accommodation;

import bg.sofia.uni.fmi.mjt.airbnb.accommodation.location.Location;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public abstract class Building implements Bookable{
    private String Id;
    private Location location;
    private boolean booked;
    private double pricePerNight;
    private int numberOfNights;

    public Building(Location location, double pricePerNight) {
        this.location = location;
        this.pricePerNight = pricePerNight;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    @Override
    public String getId() {
        return this.Id;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public Location getLocation() {
        return this.location;
    }

    @Override
    public boolean isBooked() {
        return this.booked;
    }

    @Override
    public boolean book(LocalDateTime checkIn, LocalDateTime checkOut) {
        if(this.booked) return false;
        if(checkIn == null || checkOut == null) return false;
        if(checkIn.isBefore(LocalDateTime.now()) || checkOut.isBefore(LocalDateTime.now())) return false;
        if(checkIn.equals(checkOut) || checkIn.isAfter(checkOut)) return false;
        this.booked = true;
        this.numberOfNights = (int) ChronoUnit.DAYS.between(checkIn, checkOut);
        return true;
    }

    @Override
    public double getTotalPriceOfStay() {
        if (!this.booked) return 0.0;
        return this.pricePerNight * this.numberOfNights;
    }

    public void setPricePerNight(double priceOfNight) {
        this.pricePerNight = priceOfNight;
    }

    @Override
    public double getPricePerNight() {
        return this.pricePerNight;
    }

}
