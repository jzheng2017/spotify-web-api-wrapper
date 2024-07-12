package spotify.api.impl;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import retrofit2.Call;
import retrofit2.Response;
import spotify.exceptions.HttpRequestFailedException;
import spotify.exceptions.SpotifyActionFailedException;
import spotify.models.markets.MarketFull;
import spotify.retrofit.services.MarketService;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MarketApiRetrofitTest extends AbstractApiRetrofitTest {
    private MarketApiRetrofit sut;
    @Mock
    private MarketService mockedMarketService;
    @Mock
    private Call<MarketFull> mockedMarketFullCall;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        sut = new MarketApiRetrofit(fakeAccessToken, mockedMarketService);

        when(mockedMarketService.getMarkets(anyString())).thenReturn(mockedMarketFullCall);

        when(mockedMarketFullCall.request()).thenReturn(new Request.Builder().url(fakeUrl).build());
    }

    @Test
    void getMarketsUsesCorrectValuesToCreatHttpCall() throws IOException {
        when(mockedMarketFullCall.execute()).thenReturn(Response.success(new MarketFull()));

        sut.getMarkets();
        verify(mockedMarketService).getMarkets(fakeAccessTokenWithBearer);
    }

    @Test
    void getMarketsExecutesHttpCall() throws IOException {
        when(mockedMarketFullCall.execute()).thenReturn(Response.success(new MarketFull()));

        sut.getMarkets();
        verify(mockedMarketFullCall).execute();
    }

    @Test
    void getMarketsThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedMarketFullCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.getMarkets());
    }

    @Test
    void getMarketsThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedMarketFullCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.getMarkets());
    }

    @Test
    void getMarketsReturnsMarketFullWhenSuccessful() throws IOException {
        when(mockedMarketFullCall.execute()).thenReturn(Response.success(new MarketFull()));

        Assertions.assertNotNull(sut.getMarkets());
    }
}
