package org.stock.company.infra.http;

import org.springframework.beans.factory.parsing.Problem;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.server.EntityResponse;
import org.stock.company.infra.exception.UnexpectedException;
import org.stock.exceptions.DomainException;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class RestErrorHandler {

    @ExceptionHandler(DomainException.class)
    public Mono<EntityResponse<String>> domainException(DomainException exception) {
        return createBadRequest(exception.getMessage());
    }

    @ExceptionHandler(UnexpectedException.class)
    public Mono<EntityResponse<String>> unexpectedException(UnexpectedException exception) {
        return createBadRequest("""
                An unexpected error occurred, sorry for the inconvenience. Our support will investigate it,
                please feel free to contact us to know the status using the reference number %s
                """.formatted(exception.getId()));
    }

    private <T> Mono<EntityResponse<String>> createBadRequest(String message) {
        return EntityResponse.fromObject(message)
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.TEXT_PLAIN)
                .build();
    }
}
