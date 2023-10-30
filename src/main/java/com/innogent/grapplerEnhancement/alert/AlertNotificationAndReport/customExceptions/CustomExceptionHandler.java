package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.customExceptions;

import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.payloads.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<?>> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String message = "Invalid input. Please provide a valid value.";
        return new ResponseEntity<>(new ApiResponse<>(null, message,false),HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<ApiResponse<?>> handleMethodArgumentTypeMismatch1(ResourceAccessException ex) {
        String message = "Invalid input. Please provide a valid value in URL.";
        return new ResponseEntity<>(new ApiResponse<>(null, message,false),HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleEntityNotFoundException(EntityNotFoundException ex) {
        String message = "requsted data not found.";
        return new ResponseEntity<>(new ApiResponse<>(null, message,false),HttpStatus.NOT_FOUND);

    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleResourceNotFoundException(ResourceNotFoundException ex){
        return new ResponseEntity<>(new ApiResponse<>(null, ex.getMessage(),false),HttpStatus.NOT_FOUND);


    }

    @ExceptionHandler(ResourceAlreadyExistException.class)
    public ResponseEntity<ApiResponse<?>> handleResourceAlreadyExistException(ResourceAlreadyExistException ex){
        return new ResponseEntity<>(new ApiResponse<>(null, ex.getMessage(),false),HttpStatus.CONFLICT);

    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<?>> handleDataIntegrityViolationException(DataIntegrityViolationException ex){
        return new ResponseEntity<>(new ApiResponse<>(null, ex.getMessage(),false),HttpStatus.BAD_REQUEST);
    }
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, Object> objectBody = new LinkedHashMap<>();
        Map<String ,String> errors=new LinkedHashMap();
        // Get all errors
        ex.getBindingResult()
                .getAllErrors()
                .forEach((error) ->{
                    String fieldName=((FieldError)error).getField();
                    String message= error.getDefaultMessage();
                    errors.put(fieldName,message);
                });

        objectBody.put("response",null);
        objectBody.put("Errors",errors);
//        objectBody.put("Success",false);
//        objectBody.put("Current Timestamp", new Date());

//        return new ResponseEntity<>(objectBody, status);
        return new ResponseEntity<>(new ApiResponse<>(objectBody, ex.getMessage(),false),status);

    }

}
