package org.stock.company.application.usecase.impl;

import org.springframework.stereotype.Component;
import org.stock.company.application.port.in.RegisterCompanyCommand;
import org.stock.company.application.port.out.CompanyRepository;
import org.stock.company.application.usecase.RegisterCompany;
import org.stock.company.domain.entity.Company;
import org.stock.company.domain.exception.CompanyTickerAlreadyExists;
import reactor.core.publisher.Mono;

@Component
public class RegisterCompanyImpl implements RegisterCompany {

    private final CompanyRepository repository;

    public RegisterCompanyImpl(CompanyRepository repository) {
        this.repository = repository;
    }

    public Mono<Void> execute(RegisterCompanyCommand input) {
        return repository.existsTicker(input.tickers())
                .filter(found -> found == Boolean.FALSE)
                .switchIfEmpty(Mono.error(new CompanyTickerAlreadyExists()))
                .flatMap(nothing -> saveCompany(input));
    }

    private Mono<Void> saveCompany(RegisterCompanyCommand input) {
        var company = new Company(input.name());
        input.tickers().forEach(company::addTicker);
        return repository.save(company);
    }
}
