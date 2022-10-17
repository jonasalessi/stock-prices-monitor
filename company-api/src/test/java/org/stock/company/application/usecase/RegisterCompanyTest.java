package org.stock.company.application.usecase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.stock.company.application.port.in.RegisterCompanyCommand;
import org.stock.company.domain.exception.CompanyTickerAlreadyExists;
import org.stock.company.infra.database.dao.RepositoryInMemory;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RegisterCompanyTest {

    private RegisterCompany useCase;
    private RepositoryInMemory repositoryInMemory;

    @BeforeEach
    public void setup() {
        repositoryInMemory = new RepositoryInMemory();
        useCase = new RegisterCompanyImpl(repositoryInMemory);
    }

    @Test
    void shouldReturnException_WhenCompanyTickerAlreadyExists() {
        var createCommand = new RegisterCompanyCommand(List.of("ANIM3"), "My name");
        useCase.execute(createCommand).block();
        Mono<Void> result = useCase.execute(createCommand);
        StepVerifier.create(result).verifyError(CompanyTickerAlreadyExists.class);
    }

    @Test
    void shouldSaveNewCompany_WhenCompanyTickerNotExists() {
        Mono<Void> result = useCase.execute(new RegisterCompanyCommand(List.of("ANIM3"), "My name"));
        StepVerifier.create(result).verifyComplete();
        assertEquals(List.of("ANIM3"), repositoryInMemory.getInMemoryData().get(0).getTickers());
    }
}