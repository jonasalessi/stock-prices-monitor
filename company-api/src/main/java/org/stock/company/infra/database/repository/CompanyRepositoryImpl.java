package org.stock.company.infra.database.repository;

import org.reactivestreams.Publisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.stock.company.application.port.out.CompanyRepository;
import org.stock.company.domain.entity.Company;
import org.stock.company.infra.database.entity.CompanyEntity;
import org.stock.company.infra.database.entity.TickerEntity;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Function;

@Component
public class CompanyRepositoryImpl implements CompanyRepository {

    private final CompanyCrudRepository companyRepository;
    private final TickerCrudRepository tickerRepository;

    public CompanyRepositoryImpl(CompanyCrudRepository companyRepository, TickerCrudRepository tickerRepository) {
        this.companyRepository = companyRepository;
        this.tickerRepository = tickerRepository;
    }

    @Override
    @Transactional
    public Mono<Void> save(Company company) {
        var entity = new CompanyEntity(null, company.getName());
        return companyRepository.save(entity)
                .flatMapMany(saveTickers(company.getTickers()))
                .then();
    }

    private Function<CompanyEntity, Publisher<TickerEntity>> saveTickers(List<String> tickers) {
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
