package org.stock.company.infra.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.stock.company.application.dto.CompanyTickerSearchDto;
import org.stock.company.application.port.in.RegisterCompanyCommand;
import org.stock.company.application.usecase.RegisterCompany;
import org.stock.company.application.query.SearchCompanyTicker;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private static final Logger LOG = LoggerFactory.getLogger(CompanyController.class);

    private final RegisterCompany registerCompany;
    private final SearchCompanyTicker searchCompany;

    public CompanyController(RegisterCompany registerCompany, SearchCompanyTicker searchCompany) {
        this.registerCompany = registerCompany;
        this.searchCompany = searchCompany;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Mono<Void> createCompany(@RequestBody RegisterCompanyCommand command) {
        LOG.info("Creating company {}", command);
        return registerCompany.execute(command);
    }

    @GetMapping
    Flux<CompanyTickerSearchDto> searchBy(@RequestParam String query) {
        LOG.info("Company+ticker searching by {}", query);
        return searchCompany.execute(query);
    }

}
