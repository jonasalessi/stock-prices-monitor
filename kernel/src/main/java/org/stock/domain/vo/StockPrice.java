package org.stock.domain.vo;

import java.math.BigDecimal;

public record StockPrice(String ticker, BigDecimal price) {}
