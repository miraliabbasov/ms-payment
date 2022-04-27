package com.example.mspayment.controller;

import com.example.mspayment.model.dto.ExceptionDto;
import com.example.mspayment.model.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ErrorController{

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionDto handle (NotFoundException exception){

        return new ExceptionDto(exception.getCode(), exception.getMessage());

    }
}
