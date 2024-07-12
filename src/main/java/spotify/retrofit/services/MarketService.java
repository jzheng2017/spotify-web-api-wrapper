package spotify.retrofit.services;

import retrofit2.Call;
import retrofit2.http.*;
import spotify.models.markets.MarketFull;

public interface MarketService {
    @GET("markets")
    Call<MarketFull> getMarkets(@Header("Authorization") String accessToken);

}
