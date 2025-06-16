package org.example.prjstockprice;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.Map;

public interface AlphaVantageService {
    @GET("query?function=GLOBAL_QUOTE")
    Single<Map<String, Map<String, String>>> getQuote(
            @Query("symbol") String symbol,
            @Query("apikey") String apiKey
    );
}