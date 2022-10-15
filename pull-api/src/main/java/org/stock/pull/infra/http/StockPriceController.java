package org.stock.pull.infra.http;

import io.smallrye.mutiny.Multi;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.jboss.resteasy.reactive.RestStreamElementType;
import org.stock.pull.domain.vo.StockPrice;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

@Path("/stock")
public class StockPriceController {

    private final Multi<StockPrice> prices;

    public StockPriceController(@Channel("stock-prices-in") Multi<StockPrice> prices) {
        this.prices = prices;
    }

    @GET
    @Path("prices")
    @RestStreamElementType(MediaType.APPLICATION_JSON)
    public Multi<StockPrice> getPrices() {
        return prices;
    }
}