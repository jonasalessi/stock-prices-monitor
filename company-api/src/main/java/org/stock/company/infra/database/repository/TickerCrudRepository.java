package org.stock.company.infra.database.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import org.stock.company.infra.database.entity.TickerEntity;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public interface TickerCrudRepository extends ReactiveCrudRepository<TickerEntity, Long> {
    Mono<Boolean> existsByNameIn(List<String> tickers);
}
