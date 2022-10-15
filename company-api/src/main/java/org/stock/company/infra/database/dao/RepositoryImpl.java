package org.stock.company.infra.database.dao;

import org.springframework.stereotype.Component;
import org.stock.company.domain.entity.Company;
import org.stock.company.domain.repository.CompanyRepository;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * Temporary in memory
 */
@Component
public class RepositoryImpl implements CompanyRepository {
    private final List<Company> inMemoryData = new ArrayList<>();

    public List<Company> getInMemoryData() {
        return inMemoryData;
    }

    @Override
    public Mono<Void> save(Company company) {
        inMemoryData.add(company);
        return Mono.empty();
    }

    @Override
    public Mono<Boolean> existsTicker(String ticker) {
        if (inMemoryData.stream().anyMatch(it -> it.getTicker().equals(ticker))) {
            return Mono.just(Boolean.TRUE);
        }
        return Mono.just(Boolean.FALSE);
    }
}
