package ru.gribnoff.springshop.controllers;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.gribnoff.springshop.exceptions.NotFoundException;

@SuppressWarnings("unused")
@Slf4j
@ControllerAdvice
public class ShopExceptionHandler {

    //TODO оформить страницу ошибки 500

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public String handleProductNotFoundException(final NotFoundException ex) {
        log.error("Product not found thrown", ex);
        return "error";
    }

}