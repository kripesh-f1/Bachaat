package com.f1soft.bachaat.controller;

import com.f1soft.bachaat.reponseMessage.ExceptionResponse;
import com.f1soft.bachaat.exception.DataNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ExceptionResponse> userNotFoundException(final DataNotFoundException ex, final HttpServletRequest request) {
        ExceptionResponse error = new ExceptionResponse();
        error.setMessage(ex.getMessage());
        error.setCallerUrl(request.getRequestURI());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
