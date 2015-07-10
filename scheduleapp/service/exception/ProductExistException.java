package service.exception;

public class ProductExistException extends Exception {
    public ProductExistException() {
        super("Product already exist!");
    }
}
