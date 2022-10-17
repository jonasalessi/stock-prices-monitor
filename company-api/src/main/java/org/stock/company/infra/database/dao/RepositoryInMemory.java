package org.stock.company.infra.database.dao;

import org.springframework.stereotype.Component;
import org.stock.company.application.port.out.CompanyRepository;
import org.stock.company.domain.entity.Company;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Temporary in memory
 */
@Component
public class RepositoryInMemory implements CompanyRepository {
    private final List<Company> inMemoryData = new ArrayList<>();
    private final HashSet<String> savedTickers = new HashSet<>();

    public List<Company> getInMemoryData() {
        return inMemoryData;
    }

    @Override
    public Mono<Void> save(Company company) {
        synchronized (this) {
            inMemoryData.add(company);
            savedTickers.addAll(company.getTickers());
        }
        return Mono.empty();
    }

    @Override
    public Mono<Boolean> existsTicker(List<String> tickers) {
        synchronized (this) {
            if (tickers.stream().anyMatch(ticker -> savedTickers.contains(ticker))) {
                return Mono.just(Boolean.TRUE);
            }
        }
        return Mono.just(Boolean.FALSE);
    }
}
