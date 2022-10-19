package org.stock.company.infra.database.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import org.stock.company.infra.database.entity.CompanyEntity;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public interface CompanyCrudRepository extends ReactiveCrudRepository<CompanyEntity, Long> {

    Mono<Boolean> existsByNameIn(List<String> tickers);
}
