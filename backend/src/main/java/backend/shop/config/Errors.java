package backend.shop.config;

import backend.shop.exceptions.ErrorResponse;
import backend.shop.exceptions.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice()
public class Errors{

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<?> userAlreadyExitstsExceptionHandler(UserAlreadyExistsException ex, WebRequest req){
        ErrorResponse err = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT.value())
                .error(HttpStatus.FORBIDDEN.getReasonPhrase())
                .message(ex.getMessage())
                .path(req.getDescription(false).replace("uri=", ""))
                .build();
        return new ResponseEntity<>(err, HttpStatus.CONFLICT);
    }
}