package org.stock.company.application.usecase;

import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import org.stock.company.application.dto.CompanyTickerSearchDto;
import reactor.core.publisher.Flux;

public interface SearchCompanyTickerQuery {
    Flux<CompanyTickerSearchDto> execute(String query);
}

@Component
class SearchCompanyTickerQueryImpl implements SearchCompanyTickerQuery {
    private final DatabaseClient client;

    public SearchCompanyTickerQueryImpl(DatabaseClient client) {
        this.client = client;
    }

    @Override
    public Flux<CompanyTickerSearchDto> execute(String name) {
        return client.sql("""
                        select c.name as company, t.name as ticker
                            from company c join ticker t on t.company_id = c.id
                           where c.name like :name or t.name like :name
                        """)
                .bind("name", "%" + name.toUpperCase() + "%")
                .map(r -> new CompanyTickerSearchDto(r.get("company", String.class), r.get("ticker", String.class)))
                .all();
    }
}