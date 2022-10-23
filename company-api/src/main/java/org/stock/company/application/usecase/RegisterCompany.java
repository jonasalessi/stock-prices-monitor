package org.stock.company.application.usecase;

import org.stock.company.application.port.in.RegisterCompanyCommand;
import reactor.core.publisher.Mono;

public interface RegisterCompany {
    Mono<Void> execute(RegisterCompanyCommand input);
}

