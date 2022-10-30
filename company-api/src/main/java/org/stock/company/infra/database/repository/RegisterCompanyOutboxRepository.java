package org.stock.company.infra.database.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import org.stock.company.application.port.in.RegisterCompanyCommand;
import org.stock.company.domain.entity.Company;
import org.stock.company.infra.exception.UnexpectedException;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Repository
class RegisterCompanyOutboxRepository {
    private final DatabaseClient client;
    private final ObjectMapper mapper;

    public RegisterCompanyOutboxRepository(DatabaseClient client, ObjectMapper mapper) {
        this.client = client;
        this.mapper = mapper;
    }

    public Mono<Void> saveEvent(Company company) {
        var payload = createPayload(company);
        return client.sql("insert into register_company_outbox (payload, created_at) values ($1, $2)")
                .bind("$1", payload)
                .bind("$2", Instant.now())
                .then();
    }

    private String createPayload(Company company) {
        try {
            return mapper.writeValueAsString(new RegisterCompanyCommand(company.getTickers(), company.getName()));
        } catch (Exception ex) {
            throw new UnexpectedException(ex);
        }
    }
}
