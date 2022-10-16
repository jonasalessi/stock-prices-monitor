package org.stock.company.application.port.out;

import org.stock.company.domain.entity.Company;
import reactor.core.publisher.Mono;

import java.util.List;

public interface CompanyRepository {
    Mono<Void> save(Company company);

    Mono<Boolean> existsTicker(List<String> tickers);
}
