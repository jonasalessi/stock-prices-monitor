package org.stock.company.infra.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.stock.company.application.port.in.RegisterCompanyCommand;
import org.stock.company.application.usecase.RegisterCompany;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/company")
public class CompanyController {
    private static final Logger LOG = LoggerFactory.getLogger(CompanyController.class);
    private final RegisterCompany registerCompany;

    CompanyController(RegisterCompany registerCompany) {
        this.registerCompany = registerCompany;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Mono<Void> createCompany(RegisterCompanyCommand command) {
        LOG.info("Creating company {}", command);
        return registerCompany.execute(command);
    }
}
