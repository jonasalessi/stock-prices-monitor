package org.stock.company.domain.exception;

import static java.lang.String.format;

public class CompanyTickerInvalidException extends DomainException {

    public CompanyTickerInvalidException(String ticker) {
        super(format("Company ticker %s invalid!", ticker));
    }
}
