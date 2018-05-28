package com.f1soft.bachaat.controller;

import com.kat.bachaat.errormessage.DataBindingErrorMessage;
import com.kat.bachaat.errormessage.ExceptionMessage;
import com.kat.bachaat.exception.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ExceptionMessage> unAuthorizedException(final UserAlreadyExistsException ex, final HttpServletRequest request) {
        ExceptionMessage error = new ExceptionMessage();
        error.setMessage(ex.getMessage());
        error.setCallerUrl(request.getRequestURI());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<DataBindingErrorMessage> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        exception.printStackTrace();
        DataBindingErrorMessage dataBindingErrorMessage = dataBindingErrorMessagesConverter(exception.getBindingResult());
        return new ResponseEntity<>(dataBindingErrorMessage, HttpStatus.BAD_REQUEST);
    }

    public DataBindingErrorMessage dataBindingErrorMessagesConverter(BindingResult bindingResult) {
        DataBindingErrorMessage dataBindingErrorMessage = new DataBindingErrorMessage();
        dataBindingErrorMessage.setErrorMessage("Invalid request parameter");
        dataBindingErrorMessage.setCode(HttpStatus.BAD_REQUEST.value());
        List<DataBindingErrorMessage.Error> errors = new ArrayList<>();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            DataBindingErrorMessage.Error error = dataBindingErrorMessage.new Error();
            error.setErrorMessage(fieldError.getDefaultMessage());
            error.setRejectedValue(fieldError.getRejectedValue());
            error.setFieldName(fieldError.getField());
            errors.add(error);
        }
        dataBindingErrorMessage.setErrors(errors);
        return dataBindingErrorMessage;
    }
}