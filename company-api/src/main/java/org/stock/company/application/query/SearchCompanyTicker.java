package org.stock.company.application.query;

import org.stock.company.application.dto.CompanyTickerSearchDto;
import reactor.core.publisher.Flux;

public interface SearchCompanyTicker {
    Flux<CompanyTickerSearchDto> execute(String query);
}

