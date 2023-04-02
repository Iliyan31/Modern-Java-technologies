package bg.sofia.uni.fmi.mjt.smartfridge.exception;

public class FridgeCapacityExceededException extends Throwable {
    public FridgeCapacityExceededException(String message) {
        super(message);
    }
}
