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
import spotify.api.enums.AlbumType;
import spotify.exceptions.HttpRequestFailedException;
import spotify.exceptions.SpotifyActionFailedException;
import spotify.models.albums.AlbumSimplified;
import spotify.models.artists.ArtistFull;
import spotify.models.artists.ArtistFullCollection;
import spotify.models.paging.Paging;
import spotify.models.tracks.TrackFullCollection;
import spotify.retrofit.services.ArtistService;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ArtistApiRetrofitTest extends AbstractApiRetrofitTest {
    private final String fakeArtistId = "420";
    private final List<String> listOfFakeArtistIds = Arrays.asList(fakeArtistId, "69");
    private final String fakeArtistIds = String.join(",", listOfFakeArtistIds);
    private final List<AlbumType> listOfFakeAlbumType = Arrays.asList(AlbumType.ALBUM, AlbumType.SINGLE);
    private final Map<String, String> fakeOptionalParameterWithAlbumTypes = new HashMap<>() {
        {
            put("include_groups", listOfFakeAlbumType.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(",")));
        }
    };
    private ArtistApiRetrofit sut;
    @Mock
    private ArtistService mockedArtistService;
    @Mock
    private Call<ArtistFull> mockedArtistFullCall;
    @Mock
    private Call<ArtistFullCollection> mockedArtistFullCollectionCall;
    @Mock
    private Call<Paging<AlbumSimplified>> mockedPagingAlbumSimplifiedCall;
    @Mock
    private Call<TrackFullCollection> mockedTrackFullCollectionCall;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        sut = new ArtistApiRetrofit(fakeAccessToken, mockedArtistService);

        when(mockedArtistService.getArtist(fakeAccessTokenWithBearer, fakeArtistId)).thenReturn(mockedArtistFullCall);
        when(mockedArtistService.getArtistAlbums(fakeAccessTokenWithBearer, fakeArtistId, fakeOptionalParameters)).thenReturn(mockedPagingAlbumSimplifiedCall);
        when(mockedArtistService.getArtistTopTracks(fakeAccessTokenWithBearer, fakeArtistId, fakeOptionalParameters)).thenReturn(mockedTrackFullCollectionCall);
        when(mockedArtistService.getRelatedArtists(fakeAccessTokenWithBearer, fakeArtistId)).thenReturn(mockedArtistFullCollectionCall);
        when(mockedArtistService.getArtists(fakeAccessTokenWithBearer, fakeArtistIds)).thenReturn(mockedArtistFullCollectionCall);

        when(mockedArtistFullCall.request()).thenReturn(new Request.Builder().url(fakeUrl).build());
        when(mockedArtistFullCollectionCall.request()).thenReturn(new Request.Builder().url(fakeUrl).build());
        when(mockedPagingAlbumSimplifiedCall.request()).thenReturn(new Request.Builder().url(fakeUrl).build());
        when(mockedTrackFullCollectionCall.request()).thenReturn(new Request.Builder().url(fakeUrl).build());
    }

    @Test
    void getArtistUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedArtistFullCall.execute()).thenReturn(Response.success(new ArtistFull()));

        sut.getArtist(fakeArtistId);

        verify(mockedArtistService).getArtist(fakeAccessTokenWithBearer, fakeArtistId);
    }

    @Test
    void getArtistExecutesHttpCall() throws IOException {
        when(mockedArtistFullCall.execute()).thenReturn(Response.success(new ArtistFull()));

        sut.getArtist(fakeArtistId);
        verify(mockedArtistFullCall).execute();
    }

    @Test
    void getArtistThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedArtistFullCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.getArtist(fakeArtistId));
    }

    @Test
    void getArtistThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedArtistFullCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.getArtist(fakeArtistId));
    }

    @Test
    void getArtistReturnsArtistFullWhenSuccessful() throws IOException {
        when(mockedArtistFullCall.execute()).thenReturn(Response.success(new ArtistFull()));

        Assertions.assertNotNull(sut.getArtist(fakeArtistId));
    }

    @Test
    void getArtistAlbumsUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedArtistService.getArtistAlbums(fakeAccessTokenWithBearer, fakeArtistId, fakeOptionalParameterWithAlbumTypes)).thenReturn(mockedPagingAlbumSimplifiedCall);
        when(mockedPagingAlbumSimplifiedCall.execute()).thenReturn(Response.success(new Paging<>()));

        sut.getArtistAlbums(fakeArtistId, listOfFakeAlbumType, null);

        verify(mockedArtistService).getArtistAlbums(fakeAccessTokenWithBearer, fakeArtistId, fakeOptionalParameterWithAlbumTypes);
    }

    @Test
    void getArtistAlbumsExecutesHttpCall() throws IOException {
        when(mockedPagingAlbumSimplifiedCall.execute()).thenReturn(Response.success(new Paging<>()));

        sut.getArtistAlbums(fakeArtistId, listOfFakeAlbumType, fakeOptionalParameters);
        verify(mockedPagingAlbumSimplifiedCall).execute();
    }

    @Test
    void getArtistAlbumsThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedPagingAlbumSimplifiedCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.getArtistAlbums(fakeArtistId, listOfFakeAlbumType, fakeOptionalParameters));
    }

    @Test
    void getArtistAlbumsThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedPagingAlbumSimplifiedCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.getArtistAlbums(fakeArtistId, listOfFakeAlbumType, fakeOptionalParameters));
    }

    @Test
    void getArtistAlbumsReturnsPagingAlbumSimplifiedWhenSuccessful() throws IOException {
        when(mockedPagingAlbumSimplifiedCall.execute()).thenReturn(Response.success(new Paging<>()));

        Assertions.assertNotNull(sut.getArtistAlbums(fakeArtistId, listOfFakeAlbumType, fakeOptionalParameters));
    }

    @Test
    void getArtistTopTracksUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedTrackFullCollectionCall.execute()).thenReturn(Response.success(new TrackFullCollection()));

        sut.getArtistTopTracks(fakeArtistId, null);

        verify(mockedArtistService).getArtistTopTracks(fakeAccessTokenWithBearer, fakeArtistId, fakeOptionalParameters);
    }


    @Test
    void getArtistTopTracksExecutesHttpCall() throws IOException {
        when(mockedTrackFullCollectionCall.execute()).thenReturn(Response.success(new TrackFullCollection()));

        sut.getArtistTopTracks(fakeArtistId, fakeOptionalParameters);
        verify(mockedTrackFullCollectionCall).execute();
    }

    @Test
    void getArtistTopTracksThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedTrackFullCollectionCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.getArtistTopTracks(fakeArtistId, fakeOptionalParameters));
    }

    @Test
    void getArtistTopTracksThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedTrackFullCollectionCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.getArtistTopTracks(fakeArtistId, fakeOptionalParameters));
    }

    @Test
    void getArtistTopTracksReturnsTrackFullCollectionWhenSuccessful() throws IOException {
        when(mockedTrackFullCollectionCall.execute()).thenReturn(Response.success(new TrackFullCollection()));

        Assertions.assertNotNull(sut.getArtistTopTracks(fakeArtistId, fakeOptionalParameters));
    }

    @Test
    void getRelatedArtistsUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedArtistFullCollectionCall.execute()).thenReturn(Response.success(new ArtistFullCollection()));

        sut.getRelatedArtists(fakeArtistId);

        verify(mockedArtistService).getRelatedArtists(fakeAccessTokenWithBearer, fakeArtistId);
    }

    @Test
    void getRelatedArtistsExecutesHttpCall() throws IOException {
        when(mockedArtistFullCollectionCall.execute()).thenReturn(Response.success(new ArtistFullCollection()));

        sut.getRelatedArtists(fakeArtistId);
        verify(mockedArtistFullCollectionCall).execute();
    }

    @Test
    void getRelatedArtistsThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedArtistFullCollectionCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.getRelatedArtists(fakeArtistId));
    }

    @Test
    void getRelatedArtistsThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedArtistFullCollectionCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.getRelatedArtists(fakeArtistId));
    }

    @Test
    void getRelatedArtistsReturnsArtistFullCollectionWhenSuccessful() throws IOException {
        when(mockedArtistFullCollectionCall.execute()).thenReturn(Response.success(new ArtistFullCollection()));

        Assertions.assertNotNull(sut.getRelatedArtists(fakeArtistId));
    }

    @Test
    void getArtistsUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedArtistFullCollectionCall.execute()).thenReturn(Response.success(new ArtistFullCollection()));

        sut.getArtists(listOfFakeArtistIds);

        verify(mockedArtistService).getArtists(fakeAccessTokenWithBearer, fakeArtistIds);
    }

    @Test
    void getArtistsExecutesHttpCall() throws IOException {
        when(mockedArtistFullCollectionCall.execute()).thenReturn(Response.success(new ArtistFullCollection()));

        sut.getArtists(listOfFakeArtistIds);
        verify(mockedArtistFullCollectionCall).execute();
    }

    @Test
    void getArtistsThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedArtistFullCollectionCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.getArtists(listOfFakeArtistIds));
    }

    @Test
    void getArtistsThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedArtistFullCollectionCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.getArtists(listOfFakeArtistIds));
    }

    @Test
    void getArtistsReturnsArtistFullCollectionWhenSuccessful() throws IOException {
        when(mockedArtistFullCollectionCall.execute()).thenReturn(Response.success(new ArtistFullCollection()));

        Assertions.assertNotNull(sut.getArtists(listOfFakeArtistIds));
    }
}
