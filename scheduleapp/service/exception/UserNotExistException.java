package service.exception;

public class UserNotExistException extends Exception {
    public UserNotExistException() {
        super("User doesn't exist!");
    }
}
