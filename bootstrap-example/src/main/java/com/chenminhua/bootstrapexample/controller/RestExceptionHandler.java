package com.chenminhua.bootstrapexample.controller;

import com.chenminhua.bootstrapexample.exception.FooException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({FooException.class})
    protected ResponseEntity<Object> handleFooException(Exception ex, WebRequest req) {
        System.out.println("got it");
        return handleExceptionInternal(ex, "foo foo\n", new HttpHeaders(), HttpStatus.NOT_FOUND, req);
    }

}
