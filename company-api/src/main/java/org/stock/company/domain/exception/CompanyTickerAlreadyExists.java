package org.stock.company.domain.exception;

import static java.lang.String.format;

public class CompanyTickerAlreadyExists extends DomainException {

    public CompanyTickerAlreadyExists(String ticker) {
        super(format("Ticker %s already exists!", ticker));
    }
}
