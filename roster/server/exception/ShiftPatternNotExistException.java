package server.exception;

public class ShiftPatternNotExistException extends Exception {
    public ShiftPatternNotExistException() {
        super("Shift pattern doesn't exist!");
    }
}
