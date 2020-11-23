package spotify.factories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class RetrofitClientFactory {
    private final static Logger logger = LoggerFactory.getLogger(RetrofitClientFactory.class);

    static Retrofit getRetrofitClient(String baseUrl) {
        logger.trace(String.format("Building Retrofit HTTP client with base url %s.", baseUrl));
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory
                        .create(GsonFactory
                                .getGsonLowerCaseUnderScorePolicy()))
                .build();
    }
}
