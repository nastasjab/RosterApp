package service.exception;


public class AdminAccessRequiredException extends Exception {
    public AdminAccessRequiredException() {
        super("Admin access rights required!");
    }
}
