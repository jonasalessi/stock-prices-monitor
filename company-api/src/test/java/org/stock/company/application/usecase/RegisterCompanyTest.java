package org.stock.company.application.usecase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.stock.company.application.port.in.RegisterCompanyCommand;
import org.stock.company.application.usecase.impl.RegisterCompanyImpl;
import org.stock.company.domain.exception.CompanyTickerAlreadyExists;
import org.stock.company.infra.database.FakeRepositoryInMemory;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class RegisterCompanyTest {

    private RegisterCompany useCase;
    private FakeRepositoryInMemory fakeRepositoryInMemory;

    @BeforeEach
    public void setup() {
        fakeRepositoryInMemory = new FakeRepositoryInMemory();
        useCase = new RegisterCompanyImpl(fakeRepositoryInMemory);
    }

    @Test
    void shouldReturnException_WhenCompanyTickerAlreadyExists() {
        var createdCommand = createRegisterCompanyCommand();
        useCase.execute(createdCommand).block();
        var result = useCase.execute(createdCommand);
        StepVerifier.create(result)
                .verifyError(CompanyTickerAlreadyExists.class);
        assertThat(fakeRepositoryInMemory.getInMemoryData().size(), equalTo(1));
    }

    @Test
    void shouldSaveNewCompany_WhenCompanyTickerNotExists() {
        Mono<Void> result = useCase.execute(createRegisterCompanyCommand());
        StepVerifier.create(result).verifyComplete();
        var tickers = fakeRepositoryInMemory.getInMemoryData().get(0).getTickers();
        assertThat(fakeRepositoryInMemory.getInMemoryData().size(), equalTo(1));
        assertThat(tickers, equalTo(List.of("ANIM3")));
    }

    private RegisterCompanyCommand createRegisterCompanyCommand() {
        return new RegisterCompanyCommand(List.of("ANIM3"), "My name");
    }
}