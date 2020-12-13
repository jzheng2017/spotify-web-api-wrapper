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
import spotify.models.episodes.EpisodeFull;
import spotify.models.episodes.EpisodeFullCollection;
import spotify.retrofit.services.EpisodeService;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EpisodeApiRetrofitTest extends AbstractApiRetrofitTest {
    private final String fakeEpisodeId = "69";
    private final List<String> listOfFakeEpisodeIds = Collections.singletonList(fakeEpisodeId);
    private final String fakeEpisodeIds = String.join(",", listOfFakeEpisodeIds);
    private EpisodeApiRetrofit sut;
    @Mock
    private EpisodeService mockedEpisodeService;
    @Mock
    private Call<EpisodeFull> mockedEpisodeFullCall;
    @Mock
    private Call<EpisodeFullCollection> mockedEpisodeFullCollectionCall;
    @Mock
    private List<String> episodeListWithExceededSize;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        sut = new EpisodeApiRetrofit(fakeAccessToken, mockedEpisodeService);

        when(mockedEpisodeService.getEpisode(fakeAccessTokenWithBearer, fakeEpisodeId, fakeOptionalParameters)).thenReturn(mockedEpisodeFullCall);
        when(mockedEpisodeService.getEpisodes(fakeAccessTokenWithBearer, fakeEpisodeIds, fakeOptionalParameters)).thenReturn(mockedEpisodeFullCollectionCall);

        when(mockedEpisodeFullCall.request()).thenReturn(new Request.Builder().url(fakeUrl).build());
        when(mockedEpisodeFullCollectionCall.request()).thenReturn(new Request.Builder().url(fakeUrl).build());

        when(episodeListWithExceededSize.size()).thenReturn(51);
    }

    @Test
    void getEpisodeUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedEpisodeFullCall.execute()).thenReturn(Response.success(new EpisodeFull()));

        sut.getEpisode(fakeEpisodeId, null);

        verify(mockedEpisodeService).getEpisode(fakeAccessTokenWithBearer, fakeEpisodeId, fakeOptionalParameters);
    }

    @Test
    void getEpisodeExecutesHttpCall() throws IOException {
        when(mockedEpisodeFullCall.execute()).thenReturn(Response.success(new EpisodeFull()));

        sut.getEpisode(fakeEpisodeId, fakeOptionalParameters);
        verify(mockedEpisodeFullCall).execute();
    }

    @Test
    void getEpisodeThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedEpisodeFullCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.getEpisode(fakeEpisodeId, fakeOptionalParameters));
    }

    @Test
    void getEpisodeThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedEpisodeFullCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.getEpisode(fakeEpisodeId, fakeOptionalParameters));
    }

    @Test
    void getEpisodeReturnsEpisodeFullWhenSuccessful() throws IOException {
        when(mockedEpisodeFullCall.execute()).thenReturn(Response.success(new EpisodeFull()));

        Assertions.assertNotNull(sut.getEpisode(fakeEpisodeId, fakeOptionalParameters));
    }

    @Test
    void getEpisodesUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedEpisodeFullCollectionCall.execute()).thenReturn(Response.success(new EpisodeFullCollection()));

        sut.getEpisodes(listOfFakeEpisodeIds, null);

        verify(mockedEpisodeService).getEpisodes(fakeAccessTokenWithBearer, fakeEpisodeIds, fakeOptionalParameters);
    }

    @Test
    void getEpisodesExecutesHttpCall() throws IOException {
        when(mockedEpisodeFullCollectionCall.execute()).thenReturn(Response.success(new EpisodeFullCollection()));

        sut.getEpisodes(listOfFakeEpisodeIds, fakeOptionalParameters);

        verify(mockedEpisodeFullCollectionCall).execute();
    }

    @Test
    void getEpisodesThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedEpisodeFullCollectionCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.getEpisodes(listOfFakeEpisodeIds, fakeOptionalParameters));
    }

    @Test
    void getEpisodesThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedEpisodeFullCollectionCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.getEpisodes(listOfFakeEpisodeIds, fakeOptionalParameters));
    }

    @Test
    void getEpisodesReturnsEpisodeFullCollectionWhenSuccessful() throws IOException {
        when(mockedEpisodeFullCollectionCall.execute()).thenReturn(Response.success(new EpisodeFullCollection()));

        Assertions.assertNotNull(sut.getEpisodes(listOfFakeEpisodeIds, fakeOptionalParameters));
    }

    @Test
    void getEpisodesThrowsIllegalArgumentExceptionWhenListExceedsMaximumAllowedSize() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> sut.getEpisodes(episodeListWithExceededSize, fakeOptionalParameters));
    }
}
