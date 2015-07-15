package server.exception;

public class ShiftPatternExistException extends Exception {
    public ShiftPatternExistException() {
        super("Shift pattern exists!");
    }
}
