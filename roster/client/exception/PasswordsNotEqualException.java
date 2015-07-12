package client.exception;

public class PasswordsNotEqualException extends Exception {
    public PasswordsNotEqualException() {
        super( "Passwords don't match!");
    }
}
