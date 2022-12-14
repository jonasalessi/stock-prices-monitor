package org.stock.pull.application.usecase;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.stock.domain.vo.StockPrice;

import javax.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

/**
 * For now, it will only generate random values
 */
@ApplicationScoped
public class PullStockPrice {
    private static final Random RANDOM = new Random();
    private final Emitter<StockPrice> emitter;

    public PullStockPrice(@Channel("stock-prices-out") Emitter<StockPrice> emitter) {
        this.emitter = emitter;
    }

    public void execute(String ticker) {
        var price = BigDecimal.valueOf(RANDOM.nextDouble(10)).setScale(2, RoundingMode.HALF_UP);
        emitter.send(new StockPrice(ticker, price));
    }
}
