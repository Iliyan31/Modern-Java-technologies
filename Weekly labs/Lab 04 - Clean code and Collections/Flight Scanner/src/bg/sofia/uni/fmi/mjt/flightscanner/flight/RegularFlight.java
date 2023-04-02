package bg.sofia.uni.fmi.mjt.flightscanner.flight;

import bg.sofia.uni.fmi.mjt.flightscanner.FlightSystemValidator;
import bg.sofia.uni.fmi.mjt.flightscanner.airport.Airport;
import bg.sofia.uni.fmi.mjt.flightscanner.exception.FlightCapacityExceededException;
import bg.sofia.uni.fmi.mjt.flightscanner.exception.InvalidFlightException;
import bg.sofia.uni.fmi.mjt.flightscanner.passenger.Passenger;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class RegularFlight extends FlightSystemValidator implements Flight, Comparable<RegularFlight> {

    private final String flightId;
    private final Airport startAirport;
    private final Airport endAirport;
    private final int totalCapacity;
    private final Set<Passenger> regularFlightPassengers;

    public RegularFlight(String flightId, Airport from, Airport to, int totalCapacity) {
        validateFlightId(flightId);
        validateFromAirport(from);
        validateToAirport(to);
        validateTotalCapacityOfFlight(totalCapacity);
        validateEqualityOfFromAndToFlightException(from, to);

        this.flightId = flightId;
        this.startAirport = from;
        this.endAirport = to;
        this.totalCapacity = totalCapacity;
        this.regularFlightPassengers = new HashSet<>();
    }

    public static RegularFlight of(String flightId, Airport from, Airport to, int totalCapacity) {
        return new RegularFlight(flightId, from, to, totalCapacity);
    }

    @Override
    public Airport getFrom() {
        return startAirport;
    }

    @Override
    public Airport getTo() {
        return endAirport;
    }

    @Override
    public void addPassenger(Passenger passenger) throws FlightCapacityExceededException {
        if (regularFlightPassengers.size() + 1 <= totalCapacity) {
            regularFlightPassengers.add(passenger);
        } else {
            throw new FlightCapacityExceededException("There are no more free seats!");
        }
    }

    @Override
    public void addPassengers(Collection<Passenger> passengers) throws FlightCapacityExceededException {
        validatePassengersCollection(passengers);
        validateAddPassengersCapacity(regularFlightPassengers, passengers, totalCapacity);

        regularFlightPassengers.addAll(passengers);
    }

    @Override
    public Collection<Passenger> getAllPassengers() {
        return Set.copyOf(regularFlightPassengers);
    }

    @Override
    public int getFreeSeatsCount() {
        return totalCapacity - regularFlightPassengers.size();
    }


    @Override
    public int compareTo(RegularFlight other) {
        return Integer.compare(other.getFreeSeatsCount(), this.getFreeSeatsCount());
    }

    @Override
    public String toString() {
        return "RegularFlight{" +
            "flightId='" + flightId + '\'' +
            ", from=" + startAirport +
            ", to=" + endAirport +
            ", totalCapacity=" + totalCapacity +
            ", passengers=" + regularFlightPassengers +
            '}';
    }

//    public static void main(String... args) {
//        RegularFlight f = RegularFlight.of("123", null, null, 10);
//    }
}
