package org.stock.utils;

import org.stock.exceptions.RequiredValueException;

public class Objects {

    public static <T> T nonNullOrEmpty(T value, String message) {
        if (value == null || "".equals(value)) throw new RequiredValueException(message);
        return value;
    }
}
