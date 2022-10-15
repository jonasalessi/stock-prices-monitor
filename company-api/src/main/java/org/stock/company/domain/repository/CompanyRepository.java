package org.stock.company.domain.repository;

import org.stock.company.domain.entity.Company;
import reactor.core.publisher.Mono;

public interface CompanyRepository {
    Mono<Void> save(Company company);

    Mono<Boolean> existsTicker(String ticker);
}
