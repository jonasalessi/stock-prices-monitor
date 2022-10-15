package org.stock.company.infra.http;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.stock.company.application.dto.CompanyInput;
import org.stock.company.application.usecase.RegisterCompany;
import reactor.core.publisher.Mono;

@RestController
public class CompanyController {
    private final RegisterCompany useCase;

    public CompanyController(RegisterCompany useCase) {
        this.useCase = useCase;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.CREATED)
    Mono<Void> doSome() {
        return useCase.execute(new CompanyInput("ABS1", "Some company"));
    }
}
