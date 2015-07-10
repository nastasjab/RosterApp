package front.exception;


public class OperationAdminException extends Exception {
    public OperationAdminException() {
        super("Admin access required for this operation!");
    }
}
