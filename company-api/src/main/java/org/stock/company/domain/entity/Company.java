package org.stock.company.domain.entity;

import org.stock.company.domain.exception.CompanyTickerInvalidException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.stock.utils.Objects.nonNullOrEmpty;

public final class Company {
    private final List<String> tickers;
    private final String name;

    public Company(String name) {
        this.name = nonNullOrEmpty(name, "Name is required");
        this.tickers = new ArrayList<>();
    }
    public Company addTicker(String ticker) {
        nonNullOrEmpty(ticker, "Ticker is required");
        var tickerFormatted = ticker.toUpperCase();
        validateTickerFormat(tickerFormatted);
        this.tickers.add(tickerFormatted);
        return this;
    }

    private void validateTickerFormat(String tickerFormatted) {
        if (!tickerFormatted.matches("[A-Z]{4}\\d{1,2}")) throw new CompanyTickerInvalidException(tickerFormatted);
    }

    public String getName() {
        return name;
    }

    public List<String> getTickers() {
        return Collections.unmodifiableList(tickers);
    }
}
