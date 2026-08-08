package bg.lostlanguagelab.exception;

public class UnauthorizedDeleteException extends RuntimeException {
    public UnauthorizedDeleteException(String message) {
        super(message);
    }
}
