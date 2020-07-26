package com.cs.etrading.csbooking.books.controller.advice;

import com.cs.etrading.csbooking.books.exception.BookClosedException;
import com.cs.etrading.csbooking.books.exception.BookNameExistsException;
import com.cs.etrading.csbooking.books.exception.BookNotFoundException;
import com.cs.etrading.csbooking.common.response.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BookControllerAdviceTest {
    private BookControllerAdvice bookControllerAdvice;
    private HttpServletRequest httpServletRequest;
    private ObjectMapper mapper = new ObjectMapper();


    @BeforeEach
    public  void init() {
        bookControllerAdvice = new BookControllerAdvice();
        httpServletRequest= mock(HttpServletRequest.class);
    }

    @Test
    void ShouldBeAbleToHandelEntityExistsException() throws JsonProcessingException {
        when(httpServletRequest.getMethod()).thenReturn("POST") ;
        ResponseEntity responseEntity = bookControllerAdvice.handelEntityExistsException(new BookNameExistsException("ABC"), httpServletRequest);
        Response response = buildResponse(responseEntity);
        assert (response.getErrorMessage()).equalsIgnoreCase("Error executing POST on null - Book with NAME=ABC exists");

    }

    @Test
    void ShouldBeAbleToHandelBookNotFoundException() throws JsonProcessingException {
        when(httpServletRequest.getMethod()).thenReturn("GET") ;
        ResponseEntity responseEntity = bookControllerAdvice.handelBookNotFoundException(new BookNotFoundException(1L), httpServletRequest);
        Response response = buildResponse(responseEntity);
        assert (response.getErrorMessage()).equalsIgnoreCase("Error executing GET on null - Book with ID=1 Not Found");
    }

    @Test
    void ShouldBeAbleToHandelBookClosedException() throws JsonProcessingException {
        when(httpServletRequest.getMethod()).thenReturn("PUT") ;
        ResponseEntity responseEntity = bookControllerAdvice.handelBookClosedException(new BookClosedException(2L), httpServletRequest);
        Response response = buildResponse(responseEntity);
        assert (response.getErrorMessage())
                .equalsIgnoreCase("Error executing PUT on null - Book with ID=2 is closed");
    }

    private Response buildResponse(ResponseEntity responseEntity) throws JsonProcessingException {
        String json = mapper.writeValueAsString(responseEntity.getBody());
        TypeReference ref = new TypeReference<Response>() {};
        return (Response) mapper.readValue(json, ref);
    }
}
