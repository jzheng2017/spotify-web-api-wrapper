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
import spotify.models.players.DeviceCollection;
import spotify.models.players.PlayingContext;
import spotify.retrofit.services.PlayerService;

import java.io.IOException;


import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PlayerDeviceAndContextApiRetrofitTest extends AbstractPlayerApiRetrofitTest {
    
    private PlayerApiRetrofit sut;
    @Mock
    private PlayerService mockedPlayerService;
    @Mock
    private Call<DeviceCollection> mockedDeviceCollectionCall;
    @Mock
    private Call<PlayingContext> mockedPlayingContextCall;

   

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        sut = new PlayerApiRetrofit(fakeAccessToken, mockedPlayerService);

        when(mockedPlayerService.getAvailableDevices(fakeAccessTokenWithBearer)).thenReturn(mockedDeviceCollectionCall);
        when(mockedPlayerService.getCurrentPlayingContext(fakeAccessTokenWithBearer, fakeOptionalParameters)).thenReturn(mockedPlayingContextCall);
        when(mockedDeviceCollectionCall.request()).thenReturn(new Request.Builder().url(fakeUrl).build());
        when(mockedPlayingContextCall.request()).thenReturn(new Request.Builder().url(fakeUrl).build());
       
    }

    @Test
    void getAvailableDevicesUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedDeviceCollectionCall.execute()).thenReturn(Response.success(new DeviceCollection()));

        sut.getAvailableDevices();

        verify(mockedPlayerService).getAvailableDevices(fakeAccessTokenWithBearer);
    }

    @Test
    void getAvailableDevicesExecutesHttpCall() throws IOException {
        when(mockedDeviceCollectionCall.execute()).thenReturn(Response.success(new DeviceCollection()));

        sut.getAvailableDevices();

        verify(mockedDeviceCollectionCall).execute();
    }

    @Test
    void getAvailableDevicesThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedDeviceCollectionCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.getAvailableDevices());
    }

    @Test
    void getAvailableDevicesThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedDeviceCollectionCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.getAvailableDevices());
    }

    @Test
    void getAvailableDevicesReturnsDeviceCollectionWhenSuccessful() throws IOException {
        when(mockedDeviceCollectionCall.execute()).thenReturn(Response.success(new DeviceCollection()));

        Assertions.assertNotNull(sut.getAvailableDevices());
    }

    @Test
    void getCurrentPlayingContextUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedPlayingContextCall.execute()).thenReturn(Response.success(new PlayingContext()));

        sut.getCurrentPlayingContext(null);

        verify(mockedPlayerService).getCurrentPlayingContext(fakeAccessTokenWithBearer, fakeOptionalParameters);
    }

    @Test
    void getCurrentPlayingContextExecutesHttpCall() throws IOException {
        when(mockedPlayingContextCall.execute()).thenReturn(Response.success(new PlayingContext()));

        sut.getCurrentPlayingContext(fakeOptionalParameters);

        verify(mockedPlayingContextCall).execute();
    }

    @Test
    void getCurrentPlayingContextThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedPlayingContextCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.getCurrentPlayingContext(fakeOptionalParameters));
    }

    @Test
    void getCurrentPlayingContextThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedPlayingContextCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.getCurrentPlayingContext(fakeOptionalParameters));
    }

    @Test
    void getCurrentPlayingContextReturnsPlayingContextWhenSuccessful() throws IOException {
        when(mockedPlayingContextCall.execute()).thenReturn(Response.success(new PlayingContext()));

        Assertions.assertNotNull(sut.getCurrentPlayingContext(fakeOptionalParameters));
    }


}
