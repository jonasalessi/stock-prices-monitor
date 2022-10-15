package org.stock.pull.infra;

import io.quarkus.scheduler.Scheduled;
import org.stock.pull.application.usecase.PullStockPrice;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class Schedule {
    private static final List<String> TICKERS = List.of("PETR4", "PETR3");
    private final PullStockPrice pullStockPrice;

    public Schedule(PullStockPrice pullStockPrice) {
        this.pullStockPrice = pullStockPrice;
    }

    @Scheduled(every = "{pulling.time}")
    void generateRandomStockPrices() {
        TICKERS.forEach(pullStockPrice::execute);
    }
}
