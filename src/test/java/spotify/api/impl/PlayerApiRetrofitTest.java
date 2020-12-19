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
import spotify.api.enums.RepeatType;
import spotify.exceptions.HttpRequestFailedException;
import spotify.exceptions.SpotifyActionFailedException;
import spotify.models.paging.CursorBasedPaging;
import spotify.models.players.CurrentlyPlayingObject;
import spotify.models.players.DeviceCollection;
import spotify.models.players.PlayHistory;
import spotify.models.players.PlayingContext;
import spotify.models.players.requests.ChangePlaybackStateRequestBody;
import spotify.models.players.requests.TransferPlaybackRequestBody;
import spotify.retrofit.services.PlayerService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PlayerApiRetrofitTest extends AbstractApiRetrofitTest {
    private final String fakeUri = "https://jiankai.nl";
    private final int fakeMsPosition = 69;
    private final int fakeVolumePercent = 420;
    private final boolean fakeShuffle = false;
    private final RepeatType fakeRepeatMode = RepeatType.CONTEXT;
    private PlayerApiRetrofit sut;
    @Mock
    private PlayerService mockedPlayerService;
    @Mock
    private Call<DeviceCollection> mockedDeviceCollectionCall;
    @Mock
    private Call<PlayingContext> mockedPlayingContextCall;
    @Mock
    private Call<CursorBasedPaging<PlayHistory>> mockedCursorBasedPagingPlayHistoryCall;
    @Mock
    private Call<CurrentlyPlayingObject> mockedCurrentlyPlayedObjectCall;
    @Mock
    private Call<Void> mockedVoidCall;
    private ChangePlaybackStateRequestBody changePlaybackStateRequestBody = new ChangePlaybackStateRequestBody();
    private TransferPlaybackRequestBody transferPlaybackRequestBody;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        sut = new PlayerApiRetrofit(fakeAccessToken, mockedPlayerService);

        transferPlaybackRequestBody = new TransferPlaybackRequestBody();
        ;

        when(mockedPlayerService.getAvailableDevices(fakeAccessTokenWithBearer)).thenReturn(mockedDeviceCollectionCall);
        when(mockedPlayerService.getCurrentPlayingContext(fakeAccessTokenWithBearer, fakeOptionalParameters)).thenReturn(mockedPlayingContextCall);
        when(mockedPlayerService.getRecentlyPlayedTracks(fakeAccessTokenWithBearer, fakeOptionalParameters)).thenReturn(mockedCursorBasedPagingPlayHistoryCall);
        when(mockedPlayerService.getCurrentlyPlayingObject(fakeAccessTokenWithBearer, fakeOptionalParameters)).thenReturn(mockedCurrentlyPlayedObjectCall);
        when(mockedPlayerService.addItemToQueue(fakeAccessTokenWithBearer, fakeUri, fakeOptionalParameters)).thenReturn(mockedVoidCall);
        when(mockedPlayerService.skipToNextTrack(fakeAccessTokenWithBearer, fakeOptionalParameters)).thenReturn(mockedVoidCall);
        when(mockedPlayerService.skipToPreviousTrack(fakeAccessTokenWithBearer, fakeOptionalParameters)).thenReturn(mockedVoidCall);
        when(mockedPlayerService.pausePlayback(fakeAccessTokenWithBearer, fakeOptionalParameters)).thenReturn(mockedVoidCall);
        when(mockedPlayerService.jumpToPositionInCurrentTrack(fakeAccessTokenWithBearer, fakeMsPosition, fakeOptionalParameters)).thenReturn(mockedVoidCall);
        when(mockedPlayerService.setRepeatModePlayback(fakeAccessTokenWithBearer, fakeRepeatMode, fakeOptionalParameters)).thenReturn(mockedVoidCall);
        when(mockedPlayerService.setVolumePlayback(fakeAccessTokenWithBearer, fakeVolumePercent, fakeOptionalParameters)).thenReturn(mockedVoidCall);
        when(mockedPlayerService.changePlaybackState(fakeAccessTokenWithBearer, changePlaybackStateRequestBody)).thenReturn(mockedVoidCall);
        when(mockedPlayerService.shufflePlayback(fakeAccessTokenWithBearer, fakeShuffle, fakeOptionalParameters)).thenReturn(mockedVoidCall);
        when(mockedPlayerService.transferPlayback(fakeAccessTokenWithBearer, transferPlaybackRequestBody)).thenReturn(mockedVoidCall);

        when(mockedDeviceCollectionCall.request()).thenReturn(new Request.Builder().url(fakeUrl).build());
        when(mockedPlayingContextCall.request()).thenReturn(new Request.Builder().url(fakeUrl).build());
        when(mockedCursorBasedPagingPlayHistoryCall.request()).thenReturn(new Request.Builder().url(fakeUrl).build());
        when(mockedCurrentlyPlayedObjectCall.request()).thenReturn(new Request.Builder().url(fakeUrl).build());
        when(mockedVoidCall.request()).thenReturn(new Request.Builder().url(fakeUrl).build());
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

    @Test
    void getRecentlyPlayedTracksContextUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedCursorBasedPagingPlayHistoryCall.execute()).thenReturn(Response.success(new CursorBasedPaging<>()));

        sut.getRecentlyPlayedTracks(null);

        verify(mockedPlayerService).getRecentlyPlayedTracks(fakeAccessTokenWithBearer, fakeOptionalParameters);
    }

    @Test
    void getRecentlyPlayedTracksExecutesHttpCall() throws IOException {
        when(mockedCursorBasedPagingPlayHistoryCall.execute()).thenReturn(Response.success(new CursorBasedPaging<>()));

        sut.getRecentlyPlayedTracks(fakeOptionalParameters);

        verify(mockedCursorBasedPagingPlayHistoryCall).execute();
    }

    @Test
    void getRecentlyPlayedTracksThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedCursorBasedPagingPlayHistoryCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.getRecentlyPlayedTracks(fakeOptionalParameters));
    }

    @Test
    void getRecentlyPlayedTracksThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedCursorBasedPagingPlayHistoryCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.getRecentlyPlayedTracks(fakeOptionalParameters));
    }

    @Test
    void getRecentlyPlayedTracksReturnsPlayingContextWhenSuccessful() throws IOException {
        when(mockedCursorBasedPagingPlayHistoryCall.execute()).thenReturn(Response.success(new CursorBasedPaging<>()));

        Assertions.assertNotNull(sut.getRecentlyPlayedTracks(fakeOptionalParameters));
    }

    @Test
    void getCurrentlyPlayedObjectContextUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedCurrentlyPlayedObjectCall.execute()).thenReturn(Response.success(new CurrentlyPlayingObject()));

        sut.getCurrentlyPlayedObject(null);

        verify(mockedPlayerService).getCurrentlyPlayingObject(fakeAccessTokenWithBearer, fakeOptionalParameters);
    }

    @Test
    void getCurrentlyPlayedObjectExecutesHttpCall() throws IOException {
        when(mockedCurrentlyPlayedObjectCall.execute()).thenReturn(Response.success(new CurrentlyPlayingObject()));

        sut.getCurrentlyPlayedObject(fakeOptionalParameters);

        verify(mockedCurrentlyPlayedObjectCall).execute();
    }

    @Test
    void getCurrentlyPlayedObjectThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedCurrentlyPlayedObjectCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.getCurrentlyPlayedObject(fakeOptionalParameters));
    }

    @Test
    void getCurrentlyPlayedObjectThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedCurrentlyPlayedObjectCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.getCurrentlyPlayedObject(fakeOptionalParameters));
    }

    @Test
    void getCurrentlyPlayedObjectReturnsPlayingContextWhenSuccessful() throws IOException {
        when(mockedCurrentlyPlayedObjectCall.execute()).thenReturn(Response.success(new CurrentlyPlayingObject()));

        Assertions.assertNotNull(sut.getCurrentlyPlayedObject(fakeOptionalParameters));
    }

    @Test
    void addItemToQueueUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedVoidCall.execute()).thenReturn(Response.success(null));

        sut.addItemToQueue(fakeUri, null);

        verify(mockedPlayerService).addItemToQueue(fakeAccessTokenWithBearer, fakeUri, fakeOptionalParameters);
    }

    @Test
    void addItemToQueueExecutesHttpCall() throws IOException {
        when(mockedVoidCall.execute()).thenReturn(Response.success(null));

        sut.addItemToQueue(fakeUri, fakeOptionalParameters);

        verify(mockedVoidCall).execute();
    }

    @Test
    void addItemToQueueThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedVoidCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.addItemToQueue(fakeUri, fakeOptionalParameters));
    }

    @Test
    void addItemToQueueThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedVoidCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.addItemToQueue(fakeUri, fakeOptionalParameters));
    }

    @Test
    void skipToNextTrackUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedVoidCall.execute()).thenReturn(Response.success(null));

        sut.skipToNextTrack(null);

        verify(mockedPlayerService).skipToNextTrack(fakeAccessTokenWithBearer, fakeOptionalParameters);
    }

    @Test
    void skipToNextTrackExecutesHttpCall() throws IOException {
        when(mockedVoidCall.execute()).thenReturn(Response.success(null));

        sut.skipToNextTrack(fakeOptionalParameters);

        verify(mockedVoidCall).execute();
    }

    @Test
    void skipToNextTrackThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedVoidCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.skipToNextTrack(fakeOptionalParameters));
    }

    @Test
    void skipToNextTrackThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedVoidCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.skipToNextTrack(fakeOptionalParameters));
    }

    @Test
    void skipToPreviousTrackUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedVoidCall.execute()).thenReturn(Response.success(null));

        sut.skipToPreviousTrack(null);

        verify(mockedPlayerService).skipToPreviousTrack(fakeAccessTokenWithBearer, fakeOptionalParameters);
    }

    @Test
    void skipToPreviousTrackExecutesHttpCall() throws IOException {
        when(mockedVoidCall.execute()).thenReturn(Response.success(null));

        sut.skipToPreviousTrack(fakeOptionalParameters);

        verify(mockedVoidCall).execute();
    }

    @Test
    void skipToPreviousTrackThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedVoidCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.skipToPreviousTrack(fakeOptionalParameters));
    }

    @Test
    void skipToPreviousTrackThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedVoidCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.skipToPreviousTrack(fakeOptionalParameters));
    }

    @Test
    void pausePlaybackUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedVoidCall.execute()).thenReturn(Response.success(null));

        sut.pausePlayback(null);

        verify(mockedPlayerService).pausePlayback(fakeAccessTokenWithBearer, fakeOptionalParameters);
    }

    @Test
    void pausePlaybackExecutesHttpCall() throws IOException {
        when(mockedVoidCall.execute()).thenReturn(Response.success(null));

        sut.pausePlayback(fakeOptionalParameters);

        verify(mockedVoidCall).execute();
    }

    @Test
    void pausePlaybackThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedVoidCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.pausePlayback(fakeOptionalParameters));
    }

    @Test
    void pausePlaybackThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedVoidCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.pausePlayback(fakeOptionalParameters));
    }

    @Test
    void jumpToPositionInCurrentTrackUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedVoidCall.execute()).thenReturn(Response.success(null));

        sut.jumpToPositionInCurrentTrack(fakeMsPosition, null);

        verify(mockedPlayerService).jumpToPositionInCurrentTrack(fakeAccessTokenWithBearer, fakeMsPosition, fakeOptionalParameters);
    }

    @Test
    void jumpToPositionInCurrentTrackExecutesHttpCall() throws IOException {
        when(mockedVoidCall.execute()).thenReturn(Response.success(null));

        sut.jumpToPositionInCurrentTrack(fakeMsPosition, fakeOptionalParameters);

        verify(mockedVoidCall).execute();
    }

    @Test
    void jumpToPositionInCurrentTrackThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedVoidCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.jumpToPositionInCurrentTrack(fakeMsPosition, fakeOptionalParameters));
    }

    @Test
    void jumpToPositionInCurrentTrackThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedVoidCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.jumpToPositionInCurrentTrack(fakeMsPosition, fakeOptionalParameters));
    }

    @Test
    void jumpToPositionInCurrentTrackThrowsIllegalArgumentExceptionWhenPositionMsIsNegative() throws IOException {
        when(mockedVoidCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(IllegalArgumentException.class, () -> sut.jumpToPositionInCurrentTrack(-1, fakeOptionalParameters));
    }

    @Test
    void setRepeatModePlaybackUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedVoidCall.execute()).thenReturn(Response.success(null));

        sut.setRepeatModePlayback(fakeRepeatMode, null);

        verify(mockedPlayerService).setRepeatModePlayback(fakeAccessTokenWithBearer, fakeRepeatMode, fakeOptionalParameters);
    }

    @Test
    void setRepeatModePlaybackExecutesHttpCall() throws IOException {
        when(mockedVoidCall.execute()).thenReturn(Response.success(null));

        sut.setRepeatModePlayback(fakeRepeatMode, fakeOptionalParameters);

        verify(mockedVoidCall).execute();
    }

    @Test
    void setRepeatModePlaybackThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedVoidCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.setRepeatModePlayback(fakeRepeatMode, fakeOptionalParameters));
    }

    @Test
    void setRepeatModePlaybackThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedVoidCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.setRepeatModePlayback(fakeRepeatMode, fakeOptionalParameters));
    }

    @Test
    void setVolumePlaybackUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedVoidCall.execute()).thenReturn(Response.success(null));

        sut.setVolumePlayback(fakeVolumePercent, null);

        verify(mockedPlayerService).setVolumePlayback(fakeAccessTokenWithBearer, fakeVolumePercent, fakeOptionalParameters);
    }

    @Test
    void setVolumePlaybackExecutesHttpCall() throws IOException {
        when(mockedVoidCall.execute()).thenReturn(Response.success(null));

        sut.setVolumePlayback(fakeVolumePercent, fakeOptionalParameters);

        verify(mockedVoidCall).execute();
    }

    @Test
    void setVolumePlaybackThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedVoidCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.setVolumePlayback(fakeVolumePercent, fakeOptionalParameters));
    }

    @Test
    void setVolumePlaybackThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedVoidCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.setVolumePlayback(fakeVolumePercent, fakeOptionalParameters));
    }

    @Test
    void changePlaybackStateUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedVoidCall.execute()).thenReturn(Response.success(null));

        sut.changePlaybackState(changePlaybackStateRequestBody);

        verify(mockedPlayerService).changePlaybackState(fakeAccessTokenWithBearer, changePlaybackStateRequestBody);
    }

    @Test
    void changePlaybackStateExecutesHttpCall() throws IOException {
        when(mockedVoidCall.execute()).thenReturn(Response.success(null));

        sut.changePlaybackState(changePlaybackStateRequestBody);

        verify(mockedVoidCall).execute();
    }

    @Test
    void changePlaybackStateThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedVoidCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.changePlaybackState(changePlaybackStateRequestBody));
    }

    @Test
    void changePlaybackStateThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedVoidCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.changePlaybackState(changePlaybackStateRequestBody));
    }

    @Test
    void shufflePlaybackUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedVoidCall.execute()).thenReturn(Response.success(null));

        sut.shufflePlayback(fakeShuffle, fakeOptionalParameters);

        verify(mockedPlayerService).shufflePlayback(fakeAccessTokenWithBearer, fakeShuffle, fakeOptionalParameters);
    }

    @Test
    void shufflePlaybackStateExecutesHttpCall() throws IOException {
        when(mockedVoidCall.execute()).thenReturn(Response.success(null));

        sut.shufflePlayback(fakeShuffle, fakeOptionalParameters);

        verify(mockedVoidCall).execute();
    }

    @Test
    void shufflePlaybackThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedVoidCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.shufflePlayback(fakeShuffle, fakeOptionalParameters));
    }

    @Test
    void shufflePlaybackThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedVoidCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.shufflePlayback(fakeShuffle, fakeOptionalParameters));
    }

    @Test
    void transferPlaybackUsesCorrectValuesToCreateHttpCall() throws IOException {
        transferPlaybackRequestBody.setDeviceIds(Arrays.asList("1", "2"));
        when(mockedVoidCall.execute()).thenReturn(Response.success(null));

        sut.transferPlayback(transferPlaybackRequestBody);

        verify(mockedPlayerService).transferPlayback(fakeAccessTokenWithBearer, transferPlaybackRequestBody);
    }

    @Test
    void transferPlaybackExecutesHttpCall() throws IOException {
        transferPlaybackRequestBody.setDeviceIds(Arrays.asList("1", "2"));
        when(mockedVoidCall.execute()).thenReturn(Response.success(null));

        sut.transferPlayback(transferPlaybackRequestBody);

        verify(mockedVoidCall).execute();
    }

    @Test
    void transferPlaybackThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        transferPlaybackRequestBody.setDeviceIds(Arrays.asList("1", "2"));

        when(mockedVoidCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.transferPlayback(transferPlaybackRequestBody));
    }

    @Test
    void transferPlaybackThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        transferPlaybackRequestBody.setDeviceIds(Arrays.asList("1", "2"));

        when(mockedVoidCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.transferPlayback(transferPlaybackRequestBody));
    }

    @Test
    void transferPlaybackThrowsIllegalArgumentExceptionWhenRequestBodyIsNull() throws IOException {
        when(mockedVoidCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(IllegalArgumentException.class, () -> sut.transferPlayback(null));
    }

    @Test
    void transferPlaybackThrowsIllegalArgumentExceptionWhenDeviceIdListIsNull() throws IOException {
        when(mockedVoidCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(IllegalArgumentException.class, () -> sut.transferPlayback(transferPlaybackRequestBody));
    }

    @Test
    void transferPlaybackThrowsIllegalArgumentExceptionWhenDeviceIdListIsEmpty() throws IOException {
        transferPlaybackRequestBody.setDeviceIds(new ArrayList<>());

        when(mockedVoidCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(IllegalArgumentException.class, () -> sut.transferPlayback(transferPlaybackRequestBody));
    }
}
