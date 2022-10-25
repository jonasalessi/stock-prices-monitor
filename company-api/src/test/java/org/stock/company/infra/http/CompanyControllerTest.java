package org.stock.company.infra.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.stock.AbstractIT;
import org.stock.company.application.port.in.RegisterCompanyCommand;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.fail;

@Tag("integration")
class CompanyControllerTest extends AbstractIT {

    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private DatabaseClient dbClient;
    @Autowired
    private ObjectMapper mapper;

    @Test
    void shouldCreateANewCompany_and_ListItByName() {
        var command = createRegisterCompanyCommand();

        webTestClient.post()
                .uri("/companies")
                .body(Mono.just(command), RegisterCompanyCommand.class)
                .exchange()
                .expectStatus()
                .isCreated();

        webTestClient.get()
                .uri("/companies?query=JON")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(1)
                .jsonPath("$[0].name").isEqualTo("My Company 2022")
                .jsonPath("$[0].ticker").isEqualTo("JONA3");
    }

    @Test
    void shouldSaveCommandEvent_WhenCompanyIsCreated() {
        var command = createRegisterCompanyCommand();
        webTestClient.post()
                .uri("/companies")
                .body(Mono.just(command), RegisterCompanyCommand.class)
                .exchange()
                .expectStatus()
                .isCreated();

        var result = dbClient.sql("select payload from register_company_outbox")
                .fetch()
                .all()
                .map(m -> convertPayloadToRegisterCompanyCommand(m.get("PAYLOAD").toString()))
                .blockLast();

        assertThat(result, notNullValue());
        assertThat(result, equalTo(command));

    }

    private RegisterCompanyCommand convertPayloadToRegisterCompanyCommand(String payload) {
        try {
            return mapper.readValue(payload, RegisterCompanyCommand.class);
        } catch (Exception e) {
            fail(e);
            return null;
        }
    }

    private RegisterCompanyCommand createRegisterCompanyCommand() {
        return new RegisterCompanyCommand(List.of("ABCD3", "JONA3"), "My Company 2022");
    }
}