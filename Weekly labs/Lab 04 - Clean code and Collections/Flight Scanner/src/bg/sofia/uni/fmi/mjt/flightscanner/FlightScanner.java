package bg.sofia.uni.fmi.mjt.flightscanner;

import bg.sofia.uni.fmi.mjt.flightscanner.airport.Airport;
import bg.sofia.uni.fmi.mjt.flightscanner.flight.Flight;
import bg.sofia.uni.fmi.mjt.flightscanner.flight.FlightByNameComparator;
import bg.sofia.uni.fmi.mjt.flightscanner.flight.FlightFreeSeatsComparator;
import bg.sofia.uni.fmi.mjt.flightscanner.flight.RegularFlight;

import java.util.*;

public class FlightScanner extends FlightSystemValidator implements FlightScannerAPI {

    private final Set<Flight> registeredFlights;
    private final Map<Airport, Set<Flight>> airportsMap;

    public FlightScanner() {
        this.registeredFlights = new HashSet<>();
        this.airportsMap = new HashMap<>();
    }

    @Override
    public void add(Flight flight) {
        validateFlight(flight);

        addFlightToAirport(flight);

        registeredFlights.add(flight);
    }

    @Override
    public void addAll(Collection<Flight> flights) {
        validateFlights(flights);

        addCollectionOfFlightsToAirport(flights);

        registeredFlights.addAll(flights);
    }

    /**
     * Returns a list of flights from start to destination airports with minimum number of transfers.
     * If there are several such flights with equal minimum number of transfers, returns any of them.
     * If the destination is not reachable with any sequence of flights from the start airport, returns an empty list.
     */
    @Override
    public List<Flight> searchFlights(Airport from, Airport to) {
        validateFromAirport(from);
        validateToAirport(to);
        validateEqualityOfFromAndToIllegalArgsException(from, to);

        Queue<Airport> airportsQueue = new ArrayDeque<>();
        Set<Airport> visitedAirports = new HashSet<>();
        Map<Airport, Flight> parent = new HashMap<>();

        airportsQueue.add(from);
        visitedAirports.add(from);
        parent.put(from, null);

        while (!airportsQueue.isEmpty()) {
            Airport air = airportsQueue.remove();

            if (airportsMap.get(air) == null) {
                continue;
            }

            for (Flight flight : airportsMap.get(air)) {
                Airport nextAirport = flight.getTo();
                if (!visitedAirports.contains(nextAirport)) {
                    visitedAirports.add(nextAirport);
                    airportsQueue.add(nextAirport);
                    parent.put(nextAirport, flight);
                }
            }
        }

        if (!visitedAirports.contains(to)) {
            return new LinkedList<>();
        }

        List<Flight> flightList = new LinkedList<>();

        for (Flight flight = parent.get(to); flight != null; flight = parent.get(flight.getFrom())) {
            flightList.add(flight);
        }

        return flightList;
    }

    @Override
    public List<Flight> getFlightsSortedByFreeSeats(Airport from) {
        validateFromAirport(from);

        List<Flight> sortedBySeatsFlights = new LinkedList<>();

        if (validateAirportFromFlightsForNull(airportsMap.get(from))) {
            return List.copyOf(sortedBySeatsFlights);
        }

        sortedBySeatsFlights.addAll(airportsMap.get(from));
        sortedBySeatsFlights.sort(new FlightFreeSeatsComparator());

        return List.copyOf(sortedBySeatsFlights);
    }


    @Override
    public List<Flight> getFlightsSortedByDestination(Airport from) {
        validateFromAirport(from);

        List<Flight> sortedByDestinationFlights = new LinkedList<>();

        if (validateAirportFromFlightsForNull(airportsMap.get(from))) {
            return List.copyOf(sortedByDestinationFlights);
        }

        sortedByDestinationFlights.addAll(airportsMap.get(from));
        sortedByDestinationFlights.sort(new FlightByNameComparator());

        return List.copyOf(sortedByDestinationFlights);
    }

    private void addCollectionOfFlightsToAirport(Collection<Flight> flights) {
        for (Flight flight : flights) {
            if (flight == null) {
                continue;
            }
            addFlightToAirport(flight);
        }
    }

    private void addFlightToAirport(Flight flight) {
        if (!airportsMap.containsKey(flight.getFrom())) {
            airportsMap.put(flight.getFrom(), new HashSet<>());
        }

        airportsMap.get(flight.getFrom()).add(flight);
    }


    public static void main(String... args) {
        final int maxCapacity = 100;

        Airport vie = new Airport("vie");
        Airport sn = new Airport("sn");
        Airport sof = new Airport("sof");
        Airport lhr = new Airport("lhr");

        RegularFlight regularFlight1 = new RegularFlight("AA05", vie, sn, maxCapacity);
        RegularFlight regularFlight2 = new RegularFlight("BA07", sn, lhr, maxCapacity);
        RegularFlight regularFlight3 = new RegularFlight("WIZ15", sn, sof, maxCapacity);

        FlightScanner flightScanner = new FlightScanner();
        flightScanner.add(regularFlight1);
        flightScanner.add(regularFlight2);
        flightScanner.add(regularFlight3);

        flightScanner.searchFlights(vie, sof);
    }
}
