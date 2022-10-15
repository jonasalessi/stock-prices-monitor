package org.stock.company.domain.entity;

import org.stock.company.domain.exception.CompanyTickerInvalidException;

import java.util.Objects;

public final class Company {
    private final String ticker;
    private final String name;

    public Company(String ticker, String name) {
        validateInputs(ticker, name);
        var tickerFormatted = ticker.toUpperCase();
        validateTickerFormat(tickerFormatted);
        this.ticker = tickerFormatted;
        this.name = name;
    }

    private static void validateTickerFormat(String tickerFormatted) {
        if (!tickerFormatted.matches("[A-Z]{3}\\d")) throw new CompanyTickerInvalidException(tickerFormatted);
    }

    private static void validateInputs(String ticker, String name) {
        Objects.requireNonNull(ticker, "Ticker is required");
        Objects.requireNonNull(name, "Name is required");
    }

    public String getName() {
        return name;
    }

    public String getTicker() {
        return ticker;
    }
}
