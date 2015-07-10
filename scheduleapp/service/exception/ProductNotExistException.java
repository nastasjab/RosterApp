package service.exception;

public class ProductNotExistException extends Exception {
    public ProductNotExistException() {
        super("Product not exist!");
    }
}
