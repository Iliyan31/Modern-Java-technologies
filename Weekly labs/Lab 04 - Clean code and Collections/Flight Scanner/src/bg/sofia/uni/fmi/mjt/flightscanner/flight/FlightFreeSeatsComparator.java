package bg.sofia.uni.fmi.mjt.flightscanner.flight;

import java.util.Comparator;

public class FlightFreeSeatsComparator implements Comparator<Flight> {

    @Override
    public int compare(Flight firstFlight, Flight secondFlight) {
        return Integer.compare(secondFlight.getFreeSeatsCount(), firstFlight.getFreeSeatsCount());
    }
}