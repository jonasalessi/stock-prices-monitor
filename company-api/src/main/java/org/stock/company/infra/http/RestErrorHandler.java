package org.stock.company.infra.http;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.stock.exceptions.DomainException;

@RestControllerAdvice
public class RestErrorHandler {

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<String> domainException(DomainException exception) {
         return ResponseEntity.badRequest().body(exception.getMessage());
    }
}
