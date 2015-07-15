package server.exception;

public class ShiftTimingNotExistException extends Exception {
    public ShiftTimingNotExistException() {
        super("Shift timing doesn't exist!");
    }
}
