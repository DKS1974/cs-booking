package com.cs.etrading.csbooking.books.controller.advice;

import com.cs.etrading.csbooking.books.exception.BookClosedException;
import com.cs.etrading.csbooking.books.exception.BookNameExistsException;
import com.cs.etrading.csbooking.books.exception.BookNotFoundException;
import com.cs.etrading.csbooking.common.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class BookControllerAdvice  {
    @ExceptionHandler(BookNameExistsException.class)
    public ResponseEntity handelEntityExistsException(BookNameExistsException exception, HttpServletRequest request) {
        String errorMessage = "Error executing %s on %s - %s" ;
        Response response = buildResponse(request, errorMessage, exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response) ;
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity handelBookNotFoundException(BookNotFoundException exception, HttpServletRequest request) {
        String errorMessage = "Error executing %s on %s - %s" ;
        Response response = buildResponse(request, errorMessage, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response) ;
    }

    @ExceptionHandler(BookClosedException.class)
    public ResponseEntity handelBookClosedException(BookClosedException exception, HttpServletRequest request) {
        String errorMessage = "Error executing %s on %s - %s" ;
        Response response = buildResponse(request, errorMessage, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response) ;
    }


    private Response buildResponse(HttpServletRequest request, String errorMessage, String message) {
        return Response.builder()
                .errorMessage(
                        String.format(errorMessage, request.getMethod(), request.getRequestURI(), message))
                .build();
    }

}
