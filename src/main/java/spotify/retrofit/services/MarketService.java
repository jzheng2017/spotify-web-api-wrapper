package spotify.retrofit.services;

import retrofit2.Call;
import retrofit2.http.*;
import spotify.models.markets.MarketFull;

/**
 * Retrofit service interface for Spotify Market API endpoints.
 * Provides a method to retrieve available markets where Spotify is available.
 */
public interface MarketService {

    /**
     * Retrieves the list of markets where Spotify is available.
     *
     * @param accessToken the OAuth access token for authorization
     * @return a Call object containing the list of available market codes
     */
    @GET("markets")
    Call<MarketFull> getMarkets(@Header("Authorization") String accessToken);

}