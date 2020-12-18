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
import spotify.api.enums.QueryType;
import spotify.exceptions.HttpRequestFailedException;
import spotify.exceptions.SpotifyActionFailedException;
import spotify.models.search.SearchQueryResult;
import spotify.retrofit.services.SearchService;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SearchApiRetrofitTest extends AbstractApiRetrofitTest {
    private final String fakeQuery = "peepeepoopoo";
    private final List<QueryType> listOfFakeQueryTypes = Arrays.asList(QueryType.ALBUM, QueryType.ARTIST);
    private final String fakeQueryTypes = listOfFakeQueryTypes.stream()
            .map(QueryType::toString)
            .collect(Collectors.joining(","));
    private SearchApiRetrofit sut;
    @Mock
    private SearchService mockedSearchService;
    @Mock
    private Call<SearchQueryResult> mockedSearchQueryResultCall;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        sut = new SearchApiRetrofit(fakeAccessToken, mockedSearchService);

        when(mockedSearchService.searchItem(fakeAccessTokenWithBearer, fakeQuery, fakeQueryTypes, fakeOptionalParameters)).thenReturn(mockedSearchQueryResultCall);

        when(mockedSearchQueryResultCall.request()).thenReturn(new Request.Builder().url(fakeUrl).build());
    }


    @Test
    void searchItemUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedSearchQueryResultCall.execute()).thenReturn(Response.success(new SearchQueryResult()));

        sut.searchItem(fakeQuery, listOfFakeQueryTypes, null);

        verify(mockedSearchService).searchItem(fakeAccessTokenWithBearer, fakeQuery, fakeQueryTypes, fakeOptionalParameters);
    }

    @Test
    void searchItemExecutesHttpCall() throws IOException {
        when(mockedSearchQueryResultCall.execute()).thenReturn(Response.success(new SearchQueryResult()));

        sut.searchItem(fakeQuery, listOfFakeQueryTypes, fakeOptionalParameters);

        verify(mockedSearchQueryResultCall).execute();
    }

    @Test
    void searchItemThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedSearchQueryResultCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.searchItem(fakeQuery, listOfFakeQueryTypes, fakeOptionalParameters));
    }

    @Test
    void searchItemThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedSearchQueryResultCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.searchItem(fakeQuery, listOfFakeQueryTypes, fakeOptionalParameters));
    }

    @Test
    void searchItemReturnsSearchQueryResultWhenSuccessful() throws IOException {
        when(mockedSearchQueryResultCall.execute()).thenReturn(Response.success(new SearchQueryResult()));

        Assertions.assertNotNull(sut.searchItem(fakeQuery, listOfFakeQueryTypes, fakeOptionalParameters));
    }
}
