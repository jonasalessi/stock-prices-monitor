package org.stock.company.application.port.in;

import java.util.List;

public record RegisterCompanyCommand(List<String> tickers, String name) {
}
