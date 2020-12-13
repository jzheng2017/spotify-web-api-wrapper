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
import spotify.models.artists.ArtistFull;
import spotify.models.paging.Paging;
import spotify.models.tracks.TrackFull;
import spotify.retrofit.services.PersonalizationService;

import java.io.IOException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PersonalizationApiRetrofitTest extends AbstractApiRetrofitTest {
    private PersonalizationApiRetrofit sut;
    @Mock
    private PersonalizationService mockedPersonalizationService;
    @Mock
    private Call<Paging<ArtistFull>> mockedPagingArtistFullCall;
    @Mock
    private Call<Paging<TrackFull>> mockedPagingTrackFullCall;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        sut = new PersonalizationApiRetrofit(fakeAccessToken, mockedPersonalizationService);

        when(mockedPersonalizationService.getTopArtists(fakeAccessTokenWithBearer, fakeOptionalParameters)).thenReturn(mockedPagingArtistFullCall);
        when(mockedPersonalizationService.getTopTracks(fakeAccessTokenWithBearer, fakeOptionalParameters)).thenReturn(mockedPagingTrackFullCall);

        when(mockedPagingArtistFullCall.request()).thenReturn(new Request.Builder().url(fakeUrl).build());
        when(mockedPagingTrackFullCall.request()).thenReturn(new Request.Builder().url(fakeUrl).build());
    }


    @Test
    void getTopArtistsUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedPagingArtistFullCall.execute()).thenReturn(Response.success(new Paging<>()));

        sut.getTopArtists(null);

        verify(mockedPersonalizationService).getTopArtists(fakeAccessTokenWithBearer, fakeOptionalParameters);
    }

    @Test
    void getTopArtistsExecutesHttpCall() throws IOException {
        when(mockedPagingArtistFullCall.execute()).thenReturn(Response.success(new Paging<>()));

        sut.getTopArtists(fakeOptionalParameters);

        verify(mockedPagingArtistFullCall).execute();
    }

    @Test
    void getTopArtistsThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedPagingArtistFullCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.getTopArtists(fakeOptionalParameters));
    }

    @Test
    void getTopArtistsThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedPagingArtistFullCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.getTopArtists(fakeOptionalParameters));
    }

    @Test
    void getTopArtistsReturnsArtistFullPagingWhenSuccessful() throws IOException {
        when(mockedPagingArtistFullCall.execute()).thenReturn(Response.success(new Paging<>()));

        Assertions.assertNotNull(sut.getTopArtists(fakeOptionalParameters));
    }

    @Test
    void getTopTracksUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedPagingTrackFullCall.execute()).thenReturn(Response.success(new Paging<>()));

        sut.getTopTracks(null);

        verify(mockedPersonalizationService).getTopTracks(fakeAccessTokenWithBearer, fakeOptionalParameters);
    }

    @Test
    void getTopTracksExecutesHttpCall() throws IOException {
        when(mockedPagingTrackFullCall.execute()).thenReturn(Response.success(new Paging<>()));

        sut.getTopTracks(fakeOptionalParameters);

        verify(mockedPagingTrackFullCall).execute();
    }

    @Test
    void getTopTracksThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedPagingTrackFullCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.getTopTracks(fakeOptionalParameters));
    }

    @Test
    void getTopTracksThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedPagingTrackFullCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.getTopTracks(fakeOptionalParameters));
    }

    @Test
    void getTopTracksReturnsTrackFullPagingWhenSuccessful() throws IOException {
        when(mockedPagingTrackFullCall.execute()).thenReturn(Response.success(new Paging<>()));

        Assertions.assertNotNull(sut.getTopTracks(fakeOptionalParameters));
    }
}
