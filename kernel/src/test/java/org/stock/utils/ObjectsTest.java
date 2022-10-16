package org.stock.utils;

import org.junit.jupiter.api.Test;
import org.stock.exceptions.RequiredValueException;

import static org.junit.jupiter.api.Assertions.*;
import static org.stock.utils.Objects.nonNullOrEmpty;

class ObjectsTest {

    @Test
    void shouldNotAcceptEmpty() {
        assertThrows(RequiredValueException.class, () -> nonNullOrEmpty("", "Should not be empty"));
    }


    @Test
    void shouldNotAcceptNull() {
        assertThrows(RequiredValueException.class, () -> nonNullOrEmpty(null, "Should not be null"));
    }

    @Test
    void shouldReturnTheValueWhenItIsPresent() {
        var value = nonNullOrEmpty(78, "Should return 78");
        assertEquals(78, value);
    }
}