package org.stock.company.infra.database.dao;

import org.springframework.stereotype.Component;
import org.stock.company.application.port.out.CompanyRepository;
import org.stock.company.domain.entity.Company;
import org.stock.company.infra.database.entity.CompanyEntity;
import org.stock.company.infra.database.entity.TickerEntity;
import org.stock.company.infra.database.repository.CompanyCrudRepository;
import org.stock.company.infra.database.repository.TickerCrudRepository;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class CompanyTickerDao implements CompanyRepository {

    private final CompanyCrudRepository companyRepository;
    private final TickerCrudRepository tickerRepository;

    public CompanyTickerDao(CompanyCrudRepository companyRepository, TickerCrudRepository tickerRepository) {
        this.companyRepository = companyRepository;
        this.tickerRepository = tickerRepository;
    }

    @Override
    public Mono<Void> save(Company company) {
        var ce = new CompanyEntity(null, company.getName());
        return companyRepository.save(ce)
                .flatMapMany(companyEntity -> tickerRepository.saveAll(createTickersFrom(company, companyEntity.getId())))
                .then();
    }

    private List<TickerEntity> createTickersFrom(Company company, Long companyEntityId) {
        return company.getTickers().stream().map(it -> new TickerEntity(null, it, companyEntityId)).toList();
    }

    @Override
    public Mono<Boolean> existsTicker(List<String> tickers) {
        return tickerRepository.existsByNameIn(tickers);
    }
}
