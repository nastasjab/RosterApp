package server.exception;

public class ShiftTimingExistException extends Exception {
    public ShiftTimingExistException() {
        super("Shift timing exists!");
    }
}
