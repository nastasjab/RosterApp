package client.exception;

public class InvalidEmailAddressException extends Exception {
    public InvalidEmailAddressException() {
        super("Invalid e-mail address!");
    }
}
