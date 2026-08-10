package bg.lostlanguagelab.searchservice.exception;

public class SearchRecordNotFoundException extends RuntimeException {
    public SearchRecordNotFoundException(String message) {
        super(message);
    }
}

