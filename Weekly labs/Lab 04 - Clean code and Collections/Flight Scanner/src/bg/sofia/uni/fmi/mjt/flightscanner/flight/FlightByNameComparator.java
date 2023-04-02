package bg.sofia.uni.fmi.mjt.flightscanner.flight;

import java.util.Comparator;

public class FlightByNameComparator implements Comparator<Flight> {

    @Override
    public int compare(Flight firstFlight, Flight secondFlight) {
        return firstFlight.getTo().ID().compareTo(secondFlight.getTo().ID());
    }
}
