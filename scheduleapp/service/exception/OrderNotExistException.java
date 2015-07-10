package service.exception;

public class OrderNotExistException extends Exception {
    public OrderNotExistException() {
        super("Order not exist.");
    }
}
