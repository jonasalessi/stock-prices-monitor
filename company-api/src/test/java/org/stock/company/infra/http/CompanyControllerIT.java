package org.stock.company.infra.http;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.stock.AbstractIT;
import org.stock.company.application.port.in.RegisterCompanyCommand;
import reactor.core.publisher.Mono;

import java.util.List;

class CompanyControllerIT extends AbstractIT {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void shouldCreateANewCompany_and_ListItByName() {
        var command = new RegisterCompanyCommand(List.of("ABCD3", "JONA3"), "My Company 2022");

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
}