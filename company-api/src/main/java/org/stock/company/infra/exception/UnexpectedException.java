package org.stock.company.infra.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class UnexpectedException extends RuntimeException{
    private static final Logger LOG = LoggerFactory.getLogger(UnexpectedException.class);

    private final UUID id = UUID.randomUUID();

    public UnexpectedException(Throwable cause) {
        LOG.error("Unexpected error ID[" + id + "]", cause);
    }

    public UUID getId() {
        return id;
    }
}
