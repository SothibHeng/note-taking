package com.note.infrastructure.exceptions;

import com.note.models.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException e) {
        HttpResponse notFoundResponse = new HttpResponse(404, e.getMessage());
        return new ResponseEntity<>(notFoundResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiErrorResponse> handleBadRequestException(BadRequestException e) {
        ApiErrorResponse errorResponse = new ApiErrorResponse(400, e.getMessage(), e.getApiErrors());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<Object> handleInternalServerErrorException(InternalServerErrorException e) {
        HttpResponse internalServerErrorResponse = new HttpResponse(500, e.getMessage());
        return new ResponseEntity<>(internalServerErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private record HttpResponse(int status, String message) { }
}
