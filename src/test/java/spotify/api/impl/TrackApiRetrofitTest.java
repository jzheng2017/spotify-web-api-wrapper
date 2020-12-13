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
import spotify.models.audio.AudioAnalysis;
import spotify.models.audio.AudioFeatures;
import spotify.models.audio.AudioFeaturesCollection;
import spotify.models.tracks.TrackFull;
import spotify.models.tracks.TrackFullCollection;
import spotify.retrofit.services.TrackService;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TrackApiRetrofitTest extends AbstractApiRetrofitTest {
    private final String fakeTrackId = "69";
    private final List<String> listOfFakeTrackIds = Arrays.asList(fakeTrackId, "420");
    private final String fakeTrackIds = String.join(",", listOfFakeTrackIds);
    private TrackApiRetrofit sut;
    @Mock
    private TrackService mockedTrackService;
    @Mock
    private Call<TrackFull> mockedTrackFullCall;
    @Mock
    private Call<TrackFullCollection> mockedTrackFullCollection;
    @Mock
    private Call<AudioFeatures> mockedAudioFeaturesCall;
    @Mock
    private Call<AudioFeaturesCollection> mockedAudioFeaturesCollectionCall;
    @Mock
    private Call<AudioAnalysis> mockedAudioAnalysisCall;
    @Mock
    private List<String> trackListWithExceededSize;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        sut = new TrackApiRetrofit(fakeAccessToken, mockedTrackService);

        when(mockedTrackService.getTrack(fakeAccessTokenWithBearer, fakeTrackId, fakeOptionalParameters)).thenReturn(mockedTrackFullCall);
        when(mockedTrackService.getTracks(fakeAccessTokenWithBearer, fakeTrackIds, fakeOptionalParameters)).thenReturn(mockedTrackFullCollection);
        when(mockedTrackService.getTrackAudioFeatures(fakeAccessTokenWithBearer, fakeTrackId)).thenReturn(mockedAudioFeaturesCall);
        when(mockedTrackService.getTracksAudioFeatures(fakeAccessTokenWithBearer, fakeTrackIds)).thenReturn(mockedAudioFeaturesCollectionCall);
        when(mockedTrackService.getTrackAudioAnalysis(fakeAccessTokenWithBearer, fakeTrackId)).thenReturn(mockedAudioAnalysisCall);

        when(mockedTrackFullCall.request()).thenReturn(new Request.Builder().url(fakeUrl).build());
        when(mockedTrackFullCollection.request()).thenReturn(new Request.Builder().url(fakeUrl).build());
        when(mockedAudioFeaturesCall.request()).thenReturn(new Request.Builder().url(fakeUrl).build());
        when(mockedAudioFeaturesCollectionCall.request()).thenReturn(new Request.Builder().url(fakeUrl).build());
        when(mockedAudioAnalysisCall.request()).thenReturn(new Request.Builder().url(fakeUrl).build());
    }

    @Test
    void getTrackUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedTrackFullCall.execute()).thenReturn(Response.success(new TrackFull()));

        sut.getTrack(fakeTrackId, null);

        verify(mockedTrackService).getTrack(fakeAccessTokenWithBearer, fakeTrackId, fakeOptionalParameters);
    }

    @Test
    void getTrackExecutesHttpCall() throws IOException {
        when(mockedTrackFullCall.execute()).thenReturn(Response.success(new TrackFull()));

        sut.getTrack(fakeTrackId, fakeOptionalParameters);

        verify(mockedTrackFullCall).execute();
    }

    @Test
    void getTrackThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedTrackFullCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.getTrack(fakeTrackId, fakeOptionalParameters));
    }

    @Test
    void getTrackThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedTrackFullCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.getTrack(fakeTrackId, fakeOptionalParameters));
    }

    @Test
    void getTrackReturnsTrackFullWhenSuccessful() throws IOException {
        when(mockedTrackFullCall.execute()).thenReturn(Response.success(new TrackFull()));

        Assertions.assertNotNull(sut.getTrack(fakeTrackId, fakeOptionalParameters));
    }

    @Test
    void getTracksUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedTrackFullCollection.execute()).thenReturn(Response.success(new TrackFullCollection()));

        sut.getTracks(listOfFakeTrackIds, null);

        verify(mockedTrackService).getTracks(fakeAccessTokenWithBearer, fakeTrackIds, fakeOptionalParameters);
    }

    @Test
    void getTracksExecutesHttpCall() throws IOException {
        when(mockedTrackFullCollection.execute()).thenReturn(Response.success(new TrackFullCollection()));

        sut.getTracks(listOfFakeTrackIds, fakeOptionalParameters);

        verify(mockedTrackFullCollection).execute();
    }

    @Test
    void getTracksThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedTrackFullCollection.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.getTracks(listOfFakeTrackIds, fakeOptionalParameters));
    }

    @Test
    void getTracksThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedTrackFullCollection.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.getTracks(listOfFakeTrackIds, fakeOptionalParameters));
    }

    @Test
    void getTracksReturnsTrackFullCollectionWhenSuccessful() throws IOException {
        when(mockedTrackFullCollection.execute()).thenReturn(Response.success(new TrackFullCollection()));

        Assertions.assertNotNull(sut.getTracks(listOfFakeTrackIds, fakeOptionalParameters));
    }

    @Test
    void getTrackAudioFeaturesUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedAudioFeaturesCall.execute()).thenReturn(Response.success(new AudioFeatures()));

        sut.getTrackAudioFeatures(fakeTrackId);

        verify(mockedTrackService).getTrackAudioFeatures(fakeAccessTokenWithBearer, fakeTrackId);
    }

    @Test
    void getTrackAudioFeaturesExecutesHttpCall() throws IOException {
        when(mockedAudioFeaturesCall.execute()).thenReturn(Response.success(new AudioFeatures()));

        sut.getTrackAudioFeatures(fakeTrackId);

        verify(mockedAudioFeaturesCall).execute();
    }

    @Test
    void getTrackAudioFeaturesThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedAudioFeaturesCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.getTrackAudioFeatures(fakeTrackId));
    }

    @Test
    void getTrackAudioFeaturesThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedAudioFeaturesCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.getTrackAudioFeatures(fakeTrackId));
    }

    @Test
    void getTrackAudioFeaturesReturnsAudioFeaturesWhenSuccessful() throws IOException {
        when(mockedAudioFeaturesCall.execute()).thenReturn(Response.success(new AudioFeatures()));

        Assertions.assertNotNull(sut.getTrackAudioFeatures(fakeTrackId));
    }


    @Test
    void getTracksAudioFeaturesUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedAudioFeaturesCollectionCall.execute()).thenReturn(Response.success(new AudioFeaturesCollection()));

        sut.getTracksAudioFeatures(listOfFakeTrackIds);

        verify(mockedTrackService).getTracksAudioFeatures(fakeAccessTokenWithBearer, fakeTrackIds);
    }

    @Test
    void getTracksAudioFeaturesExecutesHttpCall() throws IOException {
        when(mockedAudioFeaturesCollectionCall.execute()).thenReturn(Response.success(new AudioFeaturesCollection()));

        sut.getTracksAudioFeatures(listOfFakeTrackIds);

        verify(mockedAudioFeaturesCollectionCall).execute();
    }

    @Test
    void getTracksAudioFeaturesThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedAudioFeaturesCollectionCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.getTracksAudioFeatures(listOfFakeTrackIds));
    }

    @Test
    void getTracksAudioFeaturesThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedAudioFeaturesCollectionCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.getTracksAudioFeatures(listOfFakeTrackIds));
    }

    @Test
    void getTracksAudioFeaturesThrowsIllegalArgumentExceptionWhenListExceedMaximumAllowedSize() throws IOException {
        when(trackListWithExceededSize.size()).thenReturn(101);
        when(mockedAudioFeaturesCollectionCall.execute()).thenReturn(Response.success(new AudioFeaturesCollection()));

        Assertions.assertThrows(IllegalArgumentException.class, () -> sut.getTracksAudioFeatures(trackListWithExceededSize));
    }

    @Test
    void getTracksAudioFeaturesReturnsAudioFeaturesCollectionWhenSuccessful() throws IOException {
        when(mockedAudioFeaturesCollectionCall.execute()).thenReturn(Response.success(new AudioFeaturesCollection()));

        Assertions.assertNotNull(sut.getTracksAudioFeatures(listOfFakeTrackIds));
    }

    @Test
    void getTrackAudioAnalysisUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedAudioAnalysisCall.execute()).thenReturn(Response.success(new AudioAnalysis()));

        sut.getTrackAudioAnalysis(fakeTrackId);

        verify(mockedTrackService).getTrackAudioAnalysis(fakeAccessTokenWithBearer, fakeTrackId);
    }

    @Test
    void getTrackAudioAnalysisExecutesHttpCall() throws IOException {
        when(mockedAudioAnalysisCall.execute()).thenReturn(Response.success(new AudioAnalysis()));

        sut.getTrackAudioAnalysis(fakeTrackId);

        verify(mockedAudioAnalysisCall).execute();
    }

    @Test
    void getTrackAudioAnalysisThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedAudioAnalysisCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.getTrackAudioAnalysis(fakeTrackId));
    }

    @Test
    void getTrackAudioAnalysisThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedAudioAnalysisCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.getTrackAudioAnalysis(fakeTrackId));
    }

    @Test
    void getTrackAudioAnalysisReturnsAudioFeaturesWhenSuccessful() throws IOException {
        when(mockedAudioAnalysisCall.execute()).thenReturn(Response.success(new AudioAnalysis()));

        Assertions.assertNotNull(sut.getTrackAudioAnalysis(fakeTrackId));
    }

}
