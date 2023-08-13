package Mobilise.bookapi.utils.handlers.Exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Map<String, Object>> handleConflictException(ConflictException ex) {
        String error = ex.getMessage();

        return new ResponseEntity<>(getErrorMap(error, HttpStatus.CONFLICT), new HttpHeaders(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleCustomNotFoundException(NotFoundException ex) {
        String error = ex.getMessage();

        return new ResponseEntity<>(getErrorMap(error, HttpStatus.NOT_FOUND), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidUuidException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidUuidException(InvalidUuidException ex) {
        String error = "Invalid Uuid provided";

        return new ResponseEntity<>(getErrorMap(error, HttpStatus.BAD_REQUEST), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Map<String, Object>> handleCustomException(CustomException ex) {
        String error = ex.getMessage();
        HttpStatus status = ex.getErrorCode();

        return new ResponseEntity<>(getErrorMap(error, status), new HttpHeaders(), ex.getErrorCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());

        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    private Map<String, Object> getErrorsMap(List<String> errors) {
        Map<String, Object> errorResponse = new HashMap<>();

        errorResponse.put("errors", errors);
        errorResponse.put("status", 422);
        errorResponse.put("data", null);

        return errorResponse;
    }

    private Map<String, Object> getErrorMap(String message, HttpStatus status) {
        Map<String, Object> errorResponse = new HashMap<>();

        errorResponse.put("error", message);
        errorResponse.put("status", status);
        errorResponse.put("data", null);

        return errorResponse;
    }
}