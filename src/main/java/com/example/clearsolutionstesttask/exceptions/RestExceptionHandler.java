package com.example.clearsolutionstesttask.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class RestExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public @ResponseBody String handleValidationExceptions(MethodArgumentNotValidException ex) {
    return ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
  }

}

