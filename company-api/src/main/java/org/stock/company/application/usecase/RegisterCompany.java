package org.stock.company.application.usecase;

import org.springframework.stereotype.Component;
import org.stock.company.application.dto.CompanyInput;
import org.stock.company.domain.entity.Company;
import org.stock.company.domain.exception.CompanyTickerAlreadyExists;
import org.stock.company.domain.repository.CompanyRepository;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

@Component
public class RegisterCompany {
    private static final Logger LOG = Logger.getLogger("RegisterCompany");

    private final CompanyRepository repository;

    public RegisterCompany(CompanyRepository repository) {
        this.repository = repository;
    }

    public Mono<Void> execute(CompanyInput input) {
        return repository.existsTicker(input.ticker())
                .filter(found -> found == Boolean.FALSE)
                .switchIfEmpty(Mono.error(new CompanyTickerAlreadyExists(input.ticker())))
                .flatMap(nothing -> saveCompany(input));
    }

    private Mono<Void> saveCompany(CompanyInput input) {
        LOG.info("Creating company");
        var company = new Company(input.ticker(), input.name());
        return repository.save(company);
    }
}
