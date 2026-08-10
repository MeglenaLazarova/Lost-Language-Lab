package bg.lostlanguagelab.searchservice.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

public class GlobalExceptionHandlerTest {

    @Test
    void testHandleRuntimeException() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();

        RuntimeException ex = new RuntimeException("Error occurred");

        ResponseEntity<String> response = handler.handleRuntime(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error occurred", response.getBody());
    }

    @Test
    void testHandleSearchRecordNotFoundException() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();

        SearchRecordNotFoundException ex =
                new SearchRecordNotFoundException("Record not found");

        ResponseEntity<String> response = handler.handleSearchRecordNotFound(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Record not found", response.getBody());
    }
}

