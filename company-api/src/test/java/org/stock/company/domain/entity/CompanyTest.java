package org.stock.company.domain.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.stock.company.domain.exception.CompanyTickerInvalidException;
import org.stock.exceptions.RequiredValueException;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CompanyTest {

    @Test
    void shouldThrowsRequiredValueException_WhenValuesAreNullOrEmpty() {
        var tickerIsNull = assertThrows(RequiredValueException.class, () -> {
            new Company(null, "Name");
        });
        var nameIsNull = assertThrows(RequiredValueException.class, () -> {
            new Company("Ticker", null);
        });
        var tickerIsEmpty = assertThrows(RequiredValueException.class, () -> {
            new Company("", "Name");
        });
        var nameIsEmpty = assertThrows(RequiredValueException.class, () -> {
            new Company("ABC3", "");
        });
        assertEquals("Ticker is required", tickerIsEmpty.getMessage());
        assertEquals("Name is required", nameIsEmpty.getMessage());
        assertEquals("Ticker is required", tickerIsNull.getMessage());
        assertEquals("Name is required", nameIsNull.getMessage());
    }

    @ParameterizedTest
    @MethodSource("invalidTickers")
    void shouldNotCreateCompany_WhenTickerIsInvalid(String ticker) {
        var exception = assertThrows(CompanyTickerInvalidException.class, () -> {
            new Company(ticker, "Invalid company");
        });
        assertEquals("Company ticker " + ticker + " invalid!", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"ABC3", "ABC4"})
    void shouldCreateCompany_WhenTickerIsValid(String ticker) {
        var company = new Company(ticker, "Valid company");
        assertEquals(ticker, company.getTicker());
    }

    private static Stream<String> invalidTickers() {
        return Stream.of("ABCA3", "1BC3", "A1C3", "AB13", "ABCD", "ABCDE", "AB", "A", "ABC");
    }

}