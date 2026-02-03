package spotify.factories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Factory class for creating configured {@link Retrofit} HTTP client instances.
 * Provides methods to build Retrofit clients with appropriate Gson converters
 * for communicating with the Spotify API.
 */
class RetrofitClientFactory {
    private final static Logger logger = LoggerFactory.getLogger(RetrofitClientFactory.class);

    /**
     * Creates a Retrofit client configured with the standard Gson converter.
     * Uses lowercase underscore field naming policy to match Spotify API conventions.
     *
     * @param baseUrl the base URL for the API endpoints
     * @return a configured Retrofit client instance
     */
    static Retrofit getRetrofitClient(String baseUrl) {
        logger.trace("Building Retrofit HTTP client with base url {}.", baseUrl);
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory
                        .create(GsonFactory
                                .getGsonLowerCaseUnderScorePolicy()))
                .build();
    }

    /**
     * Creates a Retrofit client configured with a custom Gson converter that handles
     * polymorphic deserialization of playable objects (tracks and episodes).
     * Used for endpoints that return mixed content types in playlists or player state.
     *
     * @param baseUrl the base URL for the API endpoints
     * @return a configured Retrofit client instance with the custom deserializer
     */
    static Retrofit getRetrofitClientWithAbstractPlayableObjectDeserializer(final String baseUrl) {
        logger.trace("Building Retrofit HTTP client with base url {}.", baseUrl);
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory
                        .create(GsonFactory
                                .getGsonLowerCaseUnderScorePolicyWithAbstractPlayableObjectDeserializer()))
                .build();
    }
}