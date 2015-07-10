package service.exception;


public class UserExistException extends Exception {
    public UserExistException() {
        super("User already exist!");
    }
}
