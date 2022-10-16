package org.stock.company.domain.exception;

import org.stock.exceptions.DomainException;

public class CompanyTickerAlreadyExists extends DomainException {

    public CompanyTickerAlreadyExists() {
        super("Ticker already exists!");
    }
}
