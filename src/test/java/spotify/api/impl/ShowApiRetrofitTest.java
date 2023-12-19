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
import spotify.models.episodes.EpisodeSimplified;
import spotify.models.paging.Paging;
import spotify.models.shows.ShowFull;
import spotify.models.shows.ShowSimplifiedCollection;
import spotify.retrofit.services.ShowService;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ShowApiRetrofitTest extends AbstractApiRetrofitTest {
    private final String fakeShowId = "69";
    private final List<String> listOfFakeShowIds = Arrays.asList(fakeShowId, "420");
    private final String fakeShowIds = String.join(",", listOfFakeShowIds);
    private ShowApiRetrofit sut;
    @Mock
    private ShowService mockedShowService;
    @Mock
    private Call<ShowFull> mockedShowFullCall;
    @Mock
    private Call<Paging<EpisodeSimplified>> mockedPagingEpisodeSimplifiedCall;
    @Mock
    private Call<ShowSimplifiedCollection> mockedShowSimplifiedCollectionCall;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        sut = new ShowApiRetrofit(fakeAccessToken, mockedShowService);

        when(mockedShowService.getShow(fakeAccessTokenWithBearer, fakeShowId, fakeOptionalParameters)).thenReturn(mockedShowFullCall);
        when(mockedShowService.getShows(fakeAccessTokenWithBearer, fakeShowIds, fakeOptionalParameters)).thenReturn(mockedShowSimplifiedCollectionCall);
        when(mockedShowService.getShowEpisodes(fakeAccessTokenWithBearer, fakeShowId, fakeOptionalParameters)).thenReturn(mockedPagingEpisodeSimplifiedCall);

        when(mockedShowFullCall.request()).thenReturn(new Request.Builder().url(fakeUrl).build());
        when(mockedPagingEpisodeSimplifiedCall.request()).thenReturn(new Request.Builder().url(fakeUrl).build());
        when(mockedShowSimplifiedCollectionCall.request()).thenReturn(new Request.Builder().url(fakeUrl).build());
    }

    @Test
    void getShowUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedShowFullCall.execute()).thenReturn(Response.success(new ShowFull()));

        sut.getShow(fakeShowId, null);

        verify(mockedShowService).getShow(fakeAccessTokenWithBearer, fakeShowId, fakeOptionalParameters);
    }

    @Test
    void getShowExecutesHttpCall() throws IOException {
        when(mockedShowFullCall.execute()).thenReturn(Response.success(new ShowFull()));

        sut.getShow(fakeShowId, fakeOptionalParameters);

        verify(mockedShowFullCall).execute();
    }

    @Test
    void getShowThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedShowFullCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.getShow(fakeShowId, fakeOptionalParameters));
    }

    @Test
    void getShowThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedShowFullCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.getShow(fakeShowId, fakeOptionalParameters));
    }

    @Test
    void getShowReturnsShowFullWhenSuccessful() throws IOException {
        when(mockedShowFullCall.execute()).thenReturn(Response.success(new ShowFull()));

        Assertions.assertNotNull(sut.getShow(fakeShowId, fakeOptionalParameters));
    }

    @Test
    void getShowEpisodesUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedPagingEpisodeSimplifiedCall.execute()).thenReturn(Response.success(new Paging<>()));

        sut.getShowEpisodes(fakeShowId, null);

        verify(mockedShowService).getShowEpisodes(fakeAccessTokenWithBearer, fakeShowId, fakeOptionalParameters);
    }

    @Test
    void getShowEpisodesExecutesHttpCall() throws IOException {
        when(mockedPagingEpisodeSimplifiedCall.execute()).thenReturn(Response.success(new Paging<>()));

        sut.getShowEpisodes(fakeShowId, fakeOptionalParameters);

        verify(mockedPagingEpisodeSimplifiedCall).execute();
    }

    @Test
    void getShowEpisodesThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedPagingEpisodeSimplifiedCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.getShowEpisodes(fakeShowId, fakeOptionalParameters));
    }

    @Test
    void getShowEpisodesThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedPagingEpisodeSimplifiedCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.getShowEpisodes(fakeShowId, fakeOptionalParameters));
    }

    @Test
    void getShowEpisodesReturnsEpisodeSimplifiedPagingWhenSuccessful() throws IOException {
        when(mockedPagingEpisodeSimplifiedCall.execute()).thenReturn(Response.success(new Paging<>()));

        Assertions.assertNotNull(sut.getShowEpisodes(fakeShowId, fakeOptionalParameters));
    }


    @Test
    void getShowsUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedShowSimplifiedCollectionCall.execute()).thenReturn(Response.success(new ShowSimplifiedCollection()));

        sut.getShows(listOfFakeShowIds, null);

        verify(mockedShowService).getShows(fakeAccessTokenWithBearer, fakeShowIds, fakeOptionalParameters);
    }

    @Test
    void getShowsExecutesHttpCall() throws IOException {
        when(mockedShowSimplifiedCollectionCall.execute()).thenReturn(Response.success(new ShowSimplifiedCollection()));

        sut.getShows(listOfFakeShowIds, fakeOptionalParameters);

        verify(mockedShowSimplifiedCollectionCall).execute();
    }

    @Test
    void getShowsThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedShowSimplifiedCollectionCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.getShows(listOfFakeShowIds, fakeOptionalParameters));
    }

    @Test
    void getShowsThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedShowSimplifiedCollectionCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.getShows(listOfFakeShowIds, fakeOptionalParameters));
    }

    @Test
    void getShowsReturnsShowSimplifiedCollectionWhenSuccessful() throws IOException {
        when(mockedShowSimplifiedCollectionCall.execute()).thenReturn(Response.success(new ShowSimplifiedCollection()));

        Assertions.assertNotNull(sut.getShows(listOfFakeShowIds, fakeOptionalParameters));
    }

    @Test
    void getShowsUnwrappedUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedShowSimplifiedCollectionCall.execute()).thenReturn(Response.success(new ShowSimplifiedCollection()));

        sut.getShowsUnwrapped(listOfFakeShowIds, null);

        verify(mockedShowService).getShows(fakeAccessTokenWithBearer, fakeShowIds, fakeOptionalParameters);
    }

    @Test
    void getShowsUnwrappedExecutesHttpCall() throws IOException {
        when(mockedShowSimplifiedCollectionCall.execute()).thenReturn(Response.success(new ShowSimplifiedCollection()));

        sut.getShowsUnwrapped(listOfFakeShowIds, fakeOptionalParameters);

        verify(mockedShowSimplifiedCollectionCall).execute();
    }

    @Test
    void getShowsUnwrappedThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedShowSimplifiedCollectionCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.getShowsUnwrapped(listOfFakeShowIds, fakeOptionalParameters));
    }

    @Test
    void getShowsUnwrappedThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedShowSimplifiedCollectionCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.getShowsUnwrapped(listOfFakeShowIds, fakeOptionalParameters));
    }

    @Test
    void getShowsUnwrappedReturnsShowSimplifiedCollectionWhenSuccessful() throws IOException {
        ShowSimplifiedCollection showSimplifiedCollection = new ShowSimplifiedCollection();
        showSimplifiedCollection.setShows(Collections.emptyList());
        when(mockedShowSimplifiedCollectionCall.execute()).thenReturn(Response.success(showSimplifiedCollection));

        Assertions.assertNotNull(sut.getShowsUnwrapped(listOfFakeShowIds, fakeOptionalParameters));
    }

    @Test
    void getShowsUnwrappedThrowsSpotifyActionFailedExceptionWhenEmptyResponseBody() throws IOException {
        when(mockedShowSimplifiedCollectionCall.execute())
                .thenReturn(
                        Response.success(null)
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.getShowsUnwrapped(listOfFakeShowIds, fakeOptionalParameters));
    }
}
