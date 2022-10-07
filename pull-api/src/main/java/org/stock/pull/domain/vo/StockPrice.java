package org.stock.pull.domain.vo;

import java.math.BigDecimal;

public record StockPrice(String ticker, BigDecimal price) {}