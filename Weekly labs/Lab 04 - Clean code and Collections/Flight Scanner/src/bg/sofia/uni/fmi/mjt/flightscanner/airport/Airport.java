package bg.sofia.uni.fmi.mjt.flightscanner.airport;

public record Airport(String ID) {
    public Airport {
        if (ID == null || ID.isEmpty() || ID.isBlank()) {
            throw new IllegalArgumentException("The ID must not be null, empty or blank!");
        }
    }
}
