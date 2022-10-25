package org.stock.company.domain.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.stock.company.domain.exception.CompanyTickerInvalidException;
import org.stock.exceptions.RequiredValueException;

import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CompanyTest {

    @Test
    void shouldThrowsRequiredValueException_WhenValuesAreNullOrEmpty() {
        var tickerIsNull = assertThrows(RequiredValueException.class, () -> {
            new Company("Name").addTicker(null);
        });
        var nameIsNull = assertThrows(RequiredValueException.class, () -> {
            new Company(null);
        });
        var tickerIsEmpty = assertThrows(RequiredValueException.class, () -> {
            new Company("Name").addTicker("");
        });
        var nameIsEmpty = assertThrows(RequiredValueException.class, () -> {
            new Company("");
        });
        assertThat(tickerIsEmpty.getMessage(), equalTo("Ticker is required"));
        assertThat(nameIsEmpty.getMessage(), equalTo("Name is required"));
        assertThat(tickerIsNull.getMessage(), equalTo("Ticker is required"));
        assertThat(nameIsNull.getMessage(), equalTo("Name is required"));
    }

    @ParameterizedTest
    @MethodSource("invalidTickers")
    void shouldNotCreateCompany_WhenTickerIsInvalid(String ticker) {
        var exception = assertThrows(CompanyTickerInvalidException.class, () -> {
            new Company("Invalid company").addTicker(ticker);
        });
        assertThat(exception.getMessage(), equalTo("Company ticker " + ticker + " invalid!"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"AFLU5", "ALUP11"})
    void shouldCreateCompany_WhenTickerIsValid(String ticker) {
        var company = new Company("Valid company").addTicker(ticker);
        assertThat(company.getTickers().size(), equalTo(1));
        assertThat(company.getTickers().get(0), equalTo(ticker));
    }

    private static Stream<String> invalidTickers() {
        return Stream.of("ABC3", "1BC3", "A1C3", "AB13", "ABCD", "ABCDE", "AB", "A", "ABC");
    }

}