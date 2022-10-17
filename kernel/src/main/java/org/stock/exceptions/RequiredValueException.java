package org.stock.exceptions;

public class RequiredValueException extends RuntimeException{

    public RequiredValueException(String message) {
        super(message);
    }
}
