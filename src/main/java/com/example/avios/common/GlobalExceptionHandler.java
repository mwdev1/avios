package com.example.avios.common;

import com.example.avios.domain.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {

   @ResponseStatus(BAD_REQUEST)
   @ExceptionHandler(IllegalArgumentException.class)
   public String onIllegalArgumentException(IllegalArgumentException ex) {
      log.error(ex.getMessage());
      return ex.getMessage();
   }

   @ResponseStatus(NOT_FOUND)
   @ExceptionHandler(ResourceNotFoundException.class)
   public String onResourceNotFoundException(ResourceNotFoundException ex) {
      log.error(ex.getMessage());
      return ex.getMessage();
   }

   // TODO handle and standardise others

}