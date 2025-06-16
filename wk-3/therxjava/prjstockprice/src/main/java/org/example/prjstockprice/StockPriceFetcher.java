package org.example.prjstockprice;

import io.reactivex.rxjava3.core.Observable;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class StockPriceFetcher {
    private static final String API_KEY = "VY6NKGJUNDZVMBRW";

    public static Observable<String> fetch(String symbol) {
        AlphaVantageService service = ApiClient.getService();

        return Observable.interval(0, 60, TimeUnit.SECONDS)
                .flatMapSingle(tick ->
                        service.getQuote(symbol, API_KEY)
                                .map(resp -> {
                                    Map<String, String> quote = resp.get("Global Quote");
                                    return quote != null ? quote.get("05. price") : "0";
                                })
                );
    }
}