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
import spotify.models.albums.SavedAlbumFull;
import spotify.models.paging.Paging;
import spotify.models.shows.SavedShowSimplified;
import spotify.models.tracks.SavedTrackFull;
import spotify.retrofit.services.LibraryService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LibraryApiRetrofitTest extends AbstractApiRetrofitTest {
    private final List<String> listOfFakeAlbumIds = Arrays.asList("69", "420");
    private final List<String> listOfFakeShowIds = Arrays.asList("80085", "404");
    private final List<String> listOfFakeTrackIds = Arrays.asList("1337", "026");
    private final String fakeAlbumIds = String.join(",", listOfFakeAlbumIds);
    private final String fakeShowIds = String.join(",", listOfFakeShowIds);
    private final String fakeTrackIds = String.join(",", listOfFakeTrackIds);
    private LibraryApiRetrofit sut;
    @Mock
    private LibraryService mockedLibraryService;
    @Mock
    private Call<List<Boolean>> mockedListOfBooleanCall;
    @Mock
    private Call<Paging<SavedAlbumFull>> mockedSavedAlbumFullPagingCall;
    @Mock
    private Call<Paging<SavedShowSimplified>> mockedSavedAShowSimplifiedPagingCall;
    @Mock
    private Call<Paging<SavedTrackFull>> mockedSavedTrackFullPagingCall;
    @Mock
    private Call<Void> mockedVoidCall;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        sut = new LibraryApiRetrofit(fakeAccessToken, mockedLibraryService);

        when(mockedLibraryService.hasSavedAlbums(fakeAccessTokenWithBearer, fakeAlbumIds)).thenReturn(mockedListOfBooleanCall);
        when(mockedLibraryService.hasSavedShows(fakeAccessTokenWithBearer, fakeShowIds)).thenReturn(mockedListOfBooleanCall);
        when(mockedLibraryService.hasSavedTracks(fakeAccessTokenWithBearer, fakeTrackIds)).thenReturn(mockedListOfBooleanCall);
        when(mockedLibraryService.getSavedAlbums(fakeAccessTokenWithBearer, fakeOptionalParameters)).thenReturn(mockedSavedAlbumFullPagingCall);
        when(mockedLibraryService.getSavedShows(fakeAccessTokenWithBearer, fakeOptionalParameters)).thenReturn(mockedSavedAShowSimplifiedPagingCall);
        when(mockedLibraryService.getSavedTracks(fakeAccessTokenWithBearer, fakeOptionalParameters)).thenReturn(mockedSavedTrackFullPagingCall);
        when(mockedLibraryService.saveAlbums(fakeAccessTokenWithBearer, fakeAlbumIds)).thenReturn(mockedVoidCall);
        when(mockedLibraryService.saveShows(fakeAccessTokenWithBearer, fakeShowIds)).thenReturn(mockedVoidCall);
        when(mockedLibraryService.saveTracks(fakeAccessTokenWithBearer, fakeTrackIds)).thenReturn(mockedVoidCall);
        when(mockedLibraryService.deleteAlbums(fakeAccessTokenWithBearer, fakeAlbumIds)).thenReturn(mockedVoidCall);
        when(mockedLibraryService.deleteShows(fakeAccessTokenWithBearer, fakeShowIds, fakeOptionalParameters)).thenReturn(mockedVoidCall);
        when(mockedLibraryService.deleteTracks(fakeAccessTokenWithBearer, fakeTrackIds)).thenReturn(mockedVoidCall);

        when(mockedVoidCall.request()).thenReturn(new Request.Builder().url(fakeUrl).build());
        when(mockedListOfBooleanCall.request()).thenReturn(new Request.Builder().url(fakeUrl).build());
        when(mockedSavedAlbumFullPagingCall.request()).thenReturn(new Request.Builder().url(fakeUrl).build());
        when(mockedSavedAShowSimplifiedPagingCall.request()).thenReturn(new Request.Builder().url(fakeUrl).build());
        when(mockedSavedTrackFullPagingCall.request()).thenReturn(new Request.Builder().url(fakeUrl).build());
    }

    @Test
    void hasSavedAlbumsUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedListOfBooleanCall.execute()).thenReturn(Response.success(new ArrayList<>()));

        sut.hasSavedAlbums(listOfFakeAlbumIds);

        verify(mockedLibraryService).hasSavedAlbums(fakeAccessTokenWithBearer, fakeAlbumIds);
    }

    @Test
    void hasSavedAlbumsExecutesHttpCall() throws IOException {
        when(mockedListOfBooleanCall.execute()).thenReturn(Response.success(new ArrayList<>()));

        sut.hasSavedAlbums(listOfFakeAlbumIds);

        verify(mockedListOfBooleanCall).execute();
    }

    @Test
    void hasSavedAlbumsThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedListOfBooleanCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.hasSavedAlbums(listOfFakeAlbumIds));
    }

    @Test
    void hasSavedAlbumsThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedListOfBooleanCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.hasSavedAlbums(listOfFakeAlbumIds));
    }

    @Test
    void hasSavedAlbumsReturnsAListOfBooleansWhenSuccessful() throws IOException {
        when(mockedListOfBooleanCall.execute()).thenReturn(Response.success(new ArrayList<>()));

        Assertions.assertNotNull(sut.hasSavedAlbums(listOfFakeAlbumIds));
    }

    @Test
    void hasSavedShowsUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedListOfBooleanCall.execute()).thenReturn(Response.success(new ArrayList<>()));

        sut.hasSavedShows(listOfFakeShowIds);

        verify(mockedLibraryService).hasSavedShows(fakeAccessTokenWithBearer, fakeShowIds);
    }

    @Test
    void hasSavedShowsExecutesHttpCall() throws IOException {
        when(mockedListOfBooleanCall.execute()).thenReturn(Response.success(new ArrayList<>()));

        sut.hasSavedShows(listOfFakeShowIds);

        verify(mockedListOfBooleanCall).execute();
    }

    @Test
    void hasSavedShowsThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedListOfBooleanCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.hasSavedShows(listOfFakeShowIds));
    }

    @Test
    void hasSavedShowsThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedListOfBooleanCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.hasSavedShows(listOfFakeShowIds));
    }

    @Test
    void hasSavedShowsReturnsAListOfBooleansWhenSuccessful() throws IOException {
        when(mockedListOfBooleanCall.execute()).thenReturn(Response.success(new ArrayList<>()));

        Assertions.assertNotNull(sut.hasSavedShows(listOfFakeShowIds));
    }

    @Test
    void hasSavedTracksUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedListOfBooleanCall.execute()).thenReturn(Response.success(new ArrayList<>()));

        sut.hasSavedTracks(listOfFakeTrackIds);

        verify(mockedLibraryService).hasSavedTracks(fakeAccessTokenWithBearer, fakeTrackIds);
    }

    @Test
    void hasSavedTracksExecutesHttpCall() throws IOException {
        when(mockedListOfBooleanCall.execute()).thenReturn(Response.success(new ArrayList<>()));

        sut.hasSavedTracks(listOfFakeTrackIds);

        verify(mockedListOfBooleanCall).execute();
    }

    @Test
    void hasSavedTracksThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedListOfBooleanCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.hasSavedTracks(listOfFakeTrackIds));
    }

    @Test
    void hasSavedTracksThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedListOfBooleanCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.hasSavedTracks(listOfFakeTrackIds));
    }

    @Test
    void hasSavedTracksReturnsAListOfBooleansWhenSuccessful() throws IOException {
        when(mockedListOfBooleanCall.execute()).thenReturn(Response.success(new ArrayList<>()));

        Assertions.assertNotNull(sut.hasSavedTracks(listOfFakeTrackIds));
    }

    @Test
    void getSavedAlbumsUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedSavedAlbumFullPagingCall.execute()).thenReturn(Response.success(new Paging<>()));

        sut.getSavedAlbums(fakeOptionalParameters);

        verify(mockedLibraryService).getSavedAlbums(fakeAccessTokenWithBearer, fakeOptionalParameters);
    }

    @Test
    void getSavedAlbumsExecutesHttpCall() throws IOException {
        when(mockedSavedAlbumFullPagingCall.execute()).thenReturn(Response.success(new Paging<>()));

        sut.getSavedAlbums(fakeOptionalParameters);

        verify(mockedSavedAlbumFullPagingCall).execute();
    }

    @Test
    void getSavedAlbumsThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedSavedAlbumFullPagingCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.getSavedAlbums(fakeOptionalParameters));
    }

    @Test
    void getSavedAlbumsThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedSavedAlbumFullPagingCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.getSavedAlbums(fakeOptionalParameters));
    }

    @Test
    void getSavedAlbumsReturnsSavedAlbumFullPagingWhenSuccessful() throws IOException {
        when(mockedSavedAlbumFullPagingCall.execute()).thenReturn(Response.success(new Paging<>()));

        Assertions.assertNotNull(sut.getSavedAlbums(fakeOptionalParameters));
    }

    @Test
    void getSavedShowsUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedSavedAShowSimplifiedPagingCall.execute()).thenReturn(Response.success(new Paging<>()));

        sut.getSavedShows(fakeOptionalParameters);

        verify(mockedLibraryService).getSavedShows(fakeAccessTokenWithBearer, fakeOptionalParameters);
    }

    @Test
    void getSavedShowsExecutesHttpCall() throws IOException {
        when(mockedSavedAShowSimplifiedPagingCall.execute()).thenReturn(Response.success(new Paging<>()));

        sut.getSavedShows(fakeOptionalParameters);

        verify(mockedSavedAShowSimplifiedPagingCall).execute();
    }

    @Test
    void getSavedShowsThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedSavedAShowSimplifiedPagingCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.getSavedShows(fakeOptionalParameters));
    }

    @Test
    void getSavedShowsThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedSavedAShowSimplifiedPagingCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.getSavedShows(fakeOptionalParameters));
    }

    @Test
    void getSavedShowsReturnsSavedShowSimplifiedPagingWhenSuccessful() throws IOException {
        when(mockedSavedAShowSimplifiedPagingCall.execute()).thenReturn(Response.success(new Paging<>()));

        Assertions.assertNotNull(sut.getSavedShows(fakeOptionalParameters));
    }

    @Test
    void getSavedTracksUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedSavedTrackFullPagingCall.execute()).thenReturn(Response.success(new Paging<>()));

        sut.getSavedTracks(fakeOptionalParameters);

        verify(mockedLibraryService).getSavedTracks(fakeAccessTokenWithBearer, fakeOptionalParameters);
    }

    @Test
    void getSavedTracksExecutesHttpCall() throws IOException {
        when(mockedSavedTrackFullPagingCall.execute()).thenReturn(Response.success(new Paging<>()));

        sut.getSavedTracks(fakeOptionalParameters);

        verify(mockedSavedTrackFullPagingCall).execute();
    }

    @Test
    void getSavedTracksThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedSavedTrackFullPagingCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.getSavedTracks(fakeOptionalParameters));
    }

    @Test
    void getSavedTracksThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedSavedTrackFullPagingCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.getSavedTracks(fakeOptionalParameters));
    }

    @Test
    void getSavedTracksReturnsSavedTrackFullPagingWhenSuccessful() throws IOException {
        when(mockedSavedTrackFullPagingCall.execute()).thenReturn(Response.success(new Paging<>()));

        Assertions.assertNotNull(sut.getSavedTracks(fakeOptionalParameters));
    }

    @Test
    void saveAlbumsUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedVoidCall.execute()).thenReturn(Response.success(null));

        sut.saveAlbums(listOfFakeAlbumIds);

        verify(mockedLibraryService).saveAlbums(fakeAccessTokenWithBearer, fakeAlbumIds);
    }

    @Test
    void saveAlbumsExecutesHttpCall() throws IOException {
        when(mockedVoidCall.execute()).thenReturn(Response.success(null));

        sut.saveAlbums(listOfFakeAlbumIds);

        verify(mockedVoidCall).execute();
    }

    @Test
    void saveAlbumsThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedVoidCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.saveAlbums(listOfFakeAlbumIds));
    }

    @Test
    void saveAlbumsThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedVoidCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.saveAlbums(listOfFakeAlbumIds));
    }

    @Test
    void saveShowsUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedVoidCall.execute()).thenReturn(Response.success(null));

        sut.saveShows(listOfFakeShowIds);

        verify(mockedLibraryService).saveShows(fakeAccessTokenWithBearer, fakeShowIds);
    }

    @Test
    void saveShowsExecutesHttpCall() throws IOException {
        when(mockedVoidCall.execute()).thenReturn(Response.success(null));

        sut.saveShows(listOfFakeShowIds);

        verify(mockedVoidCall).execute();
    }

    @Test
    void saveShowsThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedVoidCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.saveShows(listOfFakeShowIds));
    }

    @Test
    void saveShowsThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedVoidCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.saveShows(listOfFakeShowIds));
    }

    @Test
    void saveTracksUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedVoidCall.execute()).thenReturn(Response.success(null));

        sut.saveTracks(listOfFakeTrackIds);

        verify(mockedLibraryService).saveTracks(fakeAccessTokenWithBearer, fakeTrackIds);
    }

    @Test
    void saveTracksExecutesHttpCall() throws IOException {
        when(mockedVoidCall.execute()).thenReturn(Response.success(null));

        sut.saveTracks(listOfFakeTrackIds);

        verify(mockedVoidCall).execute();
    }

    @Test
    void saveTracksThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedVoidCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.saveTracks(listOfFakeTrackIds));
    }

    @Test
    void saveTracksThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedVoidCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.saveTracks(listOfFakeTrackIds));
    }

    @Test
    void deleteAlbumsUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedVoidCall.execute()).thenReturn(Response.success(null));

        sut.deleteAlbums(listOfFakeAlbumIds);

        verify(mockedLibraryService).deleteAlbums(fakeAccessTokenWithBearer, fakeAlbumIds);
    }

    @Test
    void deleteAlbumsExecutesHttpCall() throws IOException {
        when(mockedVoidCall.execute()).thenReturn(Response.success(null));

        sut.deleteAlbums(listOfFakeAlbumIds);

        verify(mockedVoidCall).execute();
    }

    @Test
    void deleteAlbumsThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedVoidCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.deleteAlbums(listOfFakeAlbumIds));
    }

    @Test
    void deleteAlbumsThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedVoidCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.deleteAlbums(listOfFakeAlbumIds));
    }

    @Test
    void deleteShowsUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedVoidCall.execute()).thenReturn(Response.success(null));

        sut.deleteShows(listOfFakeShowIds, fakeOptionalParameters);

        verify(mockedLibraryService).deleteShows(fakeAccessTokenWithBearer, fakeShowIds, fakeOptionalParameters);
    }

    @Test
    void deleteShowsExecutesHttpCall() throws IOException {
        when(mockedVoidCall.execute()).thenReturn(Response.success(null));

        sut.deleteShows(listOfFakeShowIds, fakeOptionalParameters);

        verify(mockedVoidCall).execute();
    }

    @Test
    void deleteShowsThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedVoidCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.deleteShows(listOfFakeShowIds, fakeOptionalParameters));
    }

    @Test
    void deleteShowsThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedVoidCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.deleteShows(listOfFakeShowIds, fakeOptionalParameters));
    }

    @Test
    void deleteTracksUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedVoidCall.execute()).thenReturn(Response.success(null));

        sut.deleteTracks(listOfFakeTrackIds);

        verify(mockedLibraryService).deleteTracks(fakeAccessTokenWithBearer, fakeTrackIds);
    }

    @Test
    void deleteTracksExecutesHttpCall() throws IOException {
        when(mockedVoidCall.execute()).thenReturn(Response.success(null));

        sut.deleteTracks(listOfFakeTrackIds);

        verify(mockedVoidCall).execute();
    }

    @Test
    void deleteTracksThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedVoidCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.deleteTracks(listOfFakeTrackIds));
    }

    @Test
    void deleteTracksThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedVoidCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.deleteTracks(listOfFakeTrackIds));
    }
}
