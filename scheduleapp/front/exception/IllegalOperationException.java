package front.exception;

public class IllegalOperationException extends Exception {
    public IllegalOperationException() {
        super("Invalid operation is selected!");
    }
}
