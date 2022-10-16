package org.stock.company.infra.http;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.stock.company.application.port.in.RegisterCompanyCommand;
import org.stock.company.application.usecase.RegisterCompany;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/company")
public class CompanyController {
    private final RegisterCompany registerCompany;

    CompanyController(RegisterCompany registerCompany) {
        this.registerCompany = registerCompany;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Mono<Void> createCompany(RegisterCompanyCommand command) {
        return registerCompany.execute(command);
    } 
}
