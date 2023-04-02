package bg.sofia.uni.fmi.mjt.flightscanner;

import bg.sofia.uni.fmi.mjt.flightscanner.airport.Airport;
import bg.sofia.uni.fmi.mjt.flightscanner.exception.FlightCapacityExceededException;
import bg.sofia.uni.fmi.mjt.flightscanner.exception.InvalidFlightException;
import bg.sofia.uni.fmi.mjt.flightscanner.flight.Flight;
import bg.sofia.uni.fmi.mjt.flightscanner.passenger.Passenger;

import java.util.Collection;

public class FlightSystemValidator {
    protected void validateFlightId(String flightId) {
        if (flightId == null || flightId.isEmpty() || flightId.isBlank()) {
            throw new IllegalArgumentException("The flight ID must not be null, empty, or blank!");
        }
    }

    protected void validateFromAirport(Airport from) {
        if (from == null) {
            throw new IllegalArgumentException("The Airport (from) cannot be null!");
        }
    }

    protected void validateToAirport(Airport to) {
        if (to == null) {
            throw new IllegalArgumentException("The Airport (to) cannot be null!");
        }
    }

    protected void validateTotalCapacityOfFlight(int totalCapacity) {
        if (totalCapacity < 0) {
            throw new IllegalArgumentException("Total capacity of regular flight cannot be less than 0!");
        }
    }

    protected void validateEqualityOfFromAndToFlightException(Airport from, Airport to) {
        if (from.equals(to)) {
            throw new InvalidFlightException("The From and To destinations are equal!");
        }
    }

    protected void validateEqualityOfFromAndToIllegalArgsException(Airport from, Airport to) {
        if (from.equals(to)) {
            throw new IllegalArgumentException("The From and To destinations are equal!");
        }
    }

    protected void validatePassengersCollection(Collection<Passenger> passengers) {
        if (passengers == null) {
            throw new IllegalArgumentException("passengers cannot be null!");
        }
    }

    protected void validateAddPassengersCapacity(Collection<Passenger> regularFlightPassengers,
                                                 Collection<Passenger> passengers, int totalCapacity)
        throws FlightCapacityExceededException {
        if (regularFlightPassengers.size() + passengers.size() > totalCapacity) {
            throw new FlightCapacityExceededException("There are not enough free seats to fill all passengers!");
        }
    }

    void validateFlight(Flight flight) {
        if (flight == null) {
            throw new IllegalArgumentException("The flight cannot be null!");
        }
    }

    void validateFlights(Collection<Flight> flights) {
        if (flights == null) {
            throw new IllegalArgumentException("The collection of flights cannot be null!");
        }
    }

    boolean validateAirportFromFlightsForNull(Collection<Flight> flights) {
        return flights == null;
    }
}
