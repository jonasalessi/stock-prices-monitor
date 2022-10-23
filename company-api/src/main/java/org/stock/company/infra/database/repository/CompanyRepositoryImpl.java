package org.stock.company.infra.database.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.stock.company.application.port.out.CompanyRepository;
import org.stock.company.domain.entity.Company;
import org.stock.company.infra.database.entity.CompanyEntity;
import org.stock.company.infra.database.entity.TickerEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Function;

@Repository
public class CompanyRepositoryImpl implements CompanyRepository {

    private final CompanyCrudRepository companyRepository;
    private final TickerCrudRepository tickerRepository;
    private final RegisterCompanyOutboxRepository eventRepository;

    public CompanyRepositoryImpl(CompanyCrudRepository companyRepository, TickerCrudRepository tickerRepository,
                                 RegisterCompanyOutboxRepository eventRepository) {
        this.companyRepository = companyRepository;
        this.tickerRepository = tickerRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    @Transactional
    public Mono<Void> save(Company company) {
        var entity = new CompanyEntity(null, company.getName());
        return companyRepository.save(entity)
                .flatMapMany(saveTickers(company.getTickers()))
                .then()
                .and(eventRepository.saveEvent(company));
    }

    private Function<CompanyEntity, Flux<TickerEntity>> saveTickers(List<String> tickers) {
        return company -> tickerRepository.saveAll(toTickerEntity(tickers, company.getId()));
    }


    private List<TickerEntity> toTickerEntity(List<String> tickers, Long companyEntityId) {
        return tickers.stream().map(it -> new TickerEntity(null, it, companyEntityId)).toList();
    }

    @Override
    public Mono<Boolean> existsTicker(List<String> tickers) {
        return tickerRepository.existsByNameIn(tickers);
    }
}
