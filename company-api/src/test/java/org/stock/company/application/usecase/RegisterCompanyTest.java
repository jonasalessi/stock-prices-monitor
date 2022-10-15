package org.stock.company.application.usecase;

import org.junit.jupiter.api.Test;
import org.stock.company.application.dto.CompanyInput;
import org.stock.company.domain.exception.CompanyTickerAlreadyExists;
import org.stock.company.infra.database.dao.RepositoryImpl;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RegisterCompanyTest {

    @Test
    void shouldReturnException_WhenCompanyTickerAlreadyExists() {
        var input = new CompanyInput("ABC4", "My name");
        var registerCompany = new RegisterCompany(new RepositoryImpl());
        registerCompany.execute(input).block();
        Mono<Void> result = registerCompany.execute(input);
        StepVerifier.create(result).verifyError(CompanyTickerAlreadyExists.class);
    }

    @Test
    void shouldSaveNewCompany_WhenCompanyTickerNotExists() {
        var input = new CompanyInput("ABC4", "My name");
        var repository = new RepositoryImpl();
        var registerCompany = new RegisterCompany(repository);
        Mono<Void> result = registerCompany.execute(input);
        StepVerifier.create(result).verifyComplete();
        assertEquals("ABC4", repository.getInMemoryData().get(0).getTicker());
    }
}