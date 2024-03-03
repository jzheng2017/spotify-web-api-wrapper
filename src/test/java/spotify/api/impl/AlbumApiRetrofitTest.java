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
import spotify.models.albums.AlbumFull;
import spotify.models.albums.AlbumFullCollection;
import spotify.models.paging.Paging;
import spotify.models.tracks.TrackSimplified;
import spotify.retrofit.services.AlbumService;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AlbumApiRetrofitTest extends AbstractApiRetrofitTest {
    private AlbumApiRetrofit sut;
    @Mock
    private AlbumService mockedAlbumService;
    @Mock
    private Call<AlbumFull> mockedAlbumFullCall;
    @Mock
    private Call<AlbumFullCollection> mockedAlbumFullCollectionCall;
    @Mock
    private Call<Paging<TrackSimplified>> mockedPagingTrackSimplifiedCall;
    @Mock
    private List<String> albumListWithExceededSize;

    private final String fakeAlbumId = "420";
    private final List<String> listOfFakeAlbumIds = Arrays.asList(fakeAlbumId, "69");
    private final String fakeAlbumIds = String.join(",", listOfFakeAlbumIds);

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        sut = new AlbumApiRetrofit(fakeAccessToken, mockedAlbumService);

        when(mockedAlbumService.getAlbum(anyString(), anyString(), any())).thenReturn(mockedAlbumFullCall);
        when(mockedAlbumService.getAlbums(anyString(), anyString(), any())).thenReturn(mockedAlbumFullCollectionCall);
        when(mockedAlbumService.getAlbumTracks(anyString(), anyString(), any())).thenReturn(mockedPagingTrackSimplifiedCall);

        when(mockedAlbumFullCall.request()).thenReturn(new Request.Builder().url(fakeUrl).build());
        when(mockedAlbumFullCollectionCall.request()).thenReturn(new Request.Builder().url(fakeUrl).build());
        when(mockedPagingTrackSimplifiedCall.request()).thenReturn(new Request.Builder().url(fakeUrl).build());

        when(albumListWithExceededSize.size()).thenReturn(21);
    }

    @Test
    void getAlbumUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedAlbumFullCall.execute()).thenReturn(Response.success(new AlbumFull()));

        sut.getAlbum(fakeAlbumId, null);

        verify(mockedAlbumService).getAlbum(fakeAccessTokenWithBearer, fakeAlbumId, fakeOptionalParameters);
    }

    @Test
    void getAlbumExecutesHttpCall() throws IOException {
        when(mockedAlbumFullCall.execute()).thenReturn(Response.success(new AlbumFull()));

        sut.getAlbum(fakeAlbumId, fakeOptionalParameters);
        verify(mockedAlbumFullCall).execute();
    }

    @Test
    void getAlbumThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedAlbumFullCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.getAlbum(fakeAlbumId, fakeOptionalParameters));
    }

    @Test
    void getAlbumThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedAlbumFullCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.getAlbum(fakeAlbumId, fakeOptionalParameters));
    }

    @Test
    void getAlbumReturnsAlbumFullWhenSuccessful() throws IOException {
        when(mockedAlbumFullCall.execute()).thenReturn(Response.success(new AlbumFull()));

        Assertions.assertNotNull(sut.getAlbum(fakeAlbumId, fakeOptionalParameters));
    }

    @Test
    void getAlbumsUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedAlbumFullCollectionCall.execute()).thenReturn(Response.success(new AlbumFullCollection()));

        sut.getAlbums(listOfFakeAlbumIds, null);

        verify(mockedAlbumService).getAlbums(fakeAccessTokenWithBearer, fakeAlbumIds, fakeOptionalParameters);
    }

    @Test
    void getAlbumsExecutesHttpCall() throws IOException {
        when(mockedAlbumFullCollectionCall.execute()).thenReturn(Response.success(new AlbumFullCollection()));

        sut.getAlbums(listOfFakeAlbumIds, fakeOptionalParameters);
        verify(mockedAlbumFullCollectionCall).execute();
    }

    @Test
    void getAlbumsThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedAlbumFullCollectionCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.getAlbums(listOfFakeAlbumIds, fakeOptionalParameters));
    }

    @Test
    void getAlbumsThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedAlbumFullCollectionCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.getAlbums(listOfFakeAlbumIds, fakeOptionalParameters));
    }

    @Test
    void getAlbumsReturnsAlbumFullCollectionWhenSuccessful() throws IOException {
        when(mockedAlbumFullCollectionCall.execute()).thenReturn(Response.success(new AlbumFullCollection()));

        Assertions.assertNotNull(sut.getAlbums(listOfFakeAlbumIds, fakeOptionalParameters));
    }

    @Test
    void getAlbumsThrowsIllegalArgumentExceptionWhenListExceedsMaximumAllowedSize() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> sut.getAlbums(albumListWithExceededSize, fakeOptionalParameters));
    }

    @Test
    void getAlbumTracksUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedPagingTrackSimplifiedCall.execute()).thenReturn(Response.success(new Paging<>()));

        sut.getAlbumTracks(fakeAlbumId, null);

        verify(mockedAlbumService).getAlbumTracks(fakeAccessTokenWithBearer, fakeAlbumId, fakeOptionalParameters);
    }

    @Test
    void getAlbumTracksExecutesHttpCall() throws IOException {
        when(mockedPagingTrackSimplifiedCall.execute()).thenReturn(Response.success(new Paging<>()));

        sut.getAlbumTracks(fakeAlbumId, fakeOptionalParameters);
        verify(mockedPagingTrackSimplifiedCall).execute();
    }

    @Test
    void getAlbumTracksThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedPagingTrackSimplifiedCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.getAlbumTracks(fakeAlbumId, fakeOptionalParameters));
    }

    @Test
    void getAlbumTracksThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedPagingTrackSimplifiedCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.getAlbumTracks(fakeAlbumId, fakeOptionalParameters));
    }

    @Test
    void getAlbumTracksReturnsPagingTrackSimplifiedWhenSuccessful() throws IOException {
        when(mockedPagingTrackSimplifiedCall.execute()).thenReturn(Response.success(new Paging<>()));

        Assertions.assertNotNull(sut.getAlbumTracks(fakeAlbumId, fakeOptionalParameters));
    }
}
