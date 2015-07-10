package service.exception;

public class CategoryNotExistException extends Exception {
    public CategoryNotExistException() {
        super("Category doesn't exist!");
    }
}
