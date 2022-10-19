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
    public Flux<CompanyTickerSearchDto> execute(String query) {
        return client.sql("""
                        select c.name, t.name
                            from company c join ticker t on t.company_id = c.id
                           where c.name like :query or t.name like :query
                        """)
                .bind("query", "%" + query.toUpperCase() + "%")
                .map(row ->
                        new CompanyTickerSearchDto(row.get(0, String.class), row.get(1, String.class))
                ).all();
    }
}