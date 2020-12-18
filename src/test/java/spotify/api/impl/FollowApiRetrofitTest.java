package spotify.api.impl;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import retrofit2.Call;
import retrofit2.Response;
import spotify.api.enums.EntityType;
import spotify.exceptions.HttpRequestFailedException;
import spotify.exceptions.SpotifyActionFailedException;
import spotify.models.artists.ArtistFullCursorBasedPagingWrapper;
import spotify.models.paging.CursorBasedPaging;
import spotify.models.playlists.requests.FollowPlaylistRequestBody;
import spotify.retrofit.services.FollowService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FollowApiRetrofitTest extends AbstractApiRetrofitTest {
    private final EntityType fakeEntityType = EntityType.ARTIST;
    private final String fakeEntityId = "69";
    private final String fakePlaylistId = "420";
    private final String fakeUserId = "1337";
    private final List<String> listOfFakeEntityIds = Arrays.asList(fakeEntityId, "9000");
    private final List<String> listOfFakeUserIds = Arrays.asList(fakeUserId, "026");
    private final String fakeEntityIds = String.join(",", listOfFakeEntityIds);
    private final String fakeUserIds = String.join(",", listOfFakeUserIds);
    private final FollowPlaylistRequestBody fakeFollowPlaylistRequestBody = new FollowPlaylistRequestBody(true);
    private FollowApiRetrofit sut;
    @Mock
    private FollowService mockedFollowService;
    @Mock
    private Call<List<Boolean>> mockedListOfBooleanCall;
    @Mock
    private Call<Void> mockedVoidCall;
    @Mock
    private Call<ArtistFullCursorBasedPagingWrapper> mockedArtistFullCursorBasedPagingWrapperCall;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        sut = new FollowApiRetrofit(fakeAccessToken, mockedFollowService);

        when(mockedFollowService.isFollowing(fakeAccessTokenWithBearer, fakeEntityType, fakeEntityIds)).thenReturn(mockedListOfBooleanCall);
        when(mockedFollowService.isFollowingPlaylist(fakeAccessTokenWithBearer, fakePlaylistId, fakeUserIds)).thenReturn(mockedListOfBooleanCall);
        when(mockedFollowService.followEntities(fakeAccessTokenWithBearer, fakeEntityType, fakeEntityIds)).thenReturn(mockedVoidCall);
        when(mockedFollowService.followPlaylist(anyString(), anyString(), any())).thenReturn(mockedVoidCall);
        when(mockedFollowService.getFollowedArtists(fakeAccessTokenWithBearer, fakeEntityType, fakeOptionalParameters)).thenReturn(mockedArtistFullCursorBasedPagingWrapperCall);
        when(mockedFollowService.unfollowEntities(fakeAccessTokenWithBearer, fakeEntityType, fakeEntityIds)).thenReturn(mockedVoidCall);
        when(mockedFollowService.unfollowPlaylist(fakeAccessTokenWithBearer, fakePlaylistId)).thenReturn(mockedVoidCall);

        when(mockedListOfBooleanCall.request()).thenReturn(new Request.Builder().url(fakeUrl).build());
        when(mockedVoidCall.request()).thenReturn(new Request.Builder().url(fakeUrl).build());
        when(mockedArtistFullCursorBasedPagingWrapperCall.request()).thenReturn(new Request.Builder().url(fakeUrl).build());
    }

    @Test
    void isFollowingUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedListOfBooleanCall.execute()).thenReturn(Response.success(new ArrayList<>()));

        sut.isFollowing(fakeEntityType, listOfFakeEntityIds);

        verify(mockedFollowService).isFollowing(fakeAccessTokenWithBearer, fakeEntityType, fakeEntityIds);
    }

    @Test
    void isFollowingExecutesHttpCall() throws IOException {
        when(mockedListOfBooleanCall.execute()).thenReturn(Response.success(new ArrayList<>()));

        sut.isFollowing(fakeEntityType, listOfFakeEntityIds);

        verify(mockedListOfBooleanCall).execute();
    }

    @Test
    void isFollowingThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedListOfBooleanCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.isFollowing(fakeEntityType, listOfFakeEntityIds));
    }

    @Test
    void isFollowingThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedListOfBooleanCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.isFollowing(fakeEntityType, listOfFakeEntityIds));
    }

    @Test
    void isFollowingReturnsAListOfBooleansWhenSuccessful() throws IOException {
        when(mockedListOfBooleanCall.execute()).thenReturn(Response.success(new ArrayList<>()));

        Assertions.assertNotNull(sut.isFollowing(fakeEntityType, listOfFakeEntityIds));
    }

    @Test
    void isFollowingPlaylistUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedListOfBooleanCall.execute()).thenReturn(Response.success(new ArrayList<>()));

        sut.isFollowingPlaylist(fakePlaylistId, listOfFakeUserIds);

        verify(mockedFollowService).isFollowingPlaylist(fakeAccessTokenWithBearer, fakePlaylistId, fakeUserIds);
    }

    @Test
    void isFollowingPlaylistExecutesHttpCall() throws IOException {
        when(mockedListOfBooleanCall.execute()).thenReturn(Response.success(new ArrayList<>()));

        sut.isFollowingPlaylist(fakePlaylistId, listOfFakeUserIds);

        verify(mockedListOfBooleanCall).execute();
    }

    @Test
    void isFollowingPlaylistThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedListOfBooleanCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.isFollowingPlaylist(fakePlaylistId, listOfFakeUserIds));
    }

    @Test
    void isFollowingPlaylistThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedListOfBooleanCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.isFollowingPlaylist(fakePlaylistId, listOfFakeUserIds));
    }

    @Test
    void isFollowingPlaylistReturnsAListOfBooleansWhenSuccessful() throws IOException {
        when(mockedListOfBooleanCall.execute()).thenReturn(Response.success(new ArrayList<>()));

        Assertions.assertNotNull(sut.isFollowingPlaylist(fakePlaylistId, listOfFakeUserIds));
    }

    @Test
    void followEntitiesUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedVoidCall.execute()).thenReturn(Response.success(null));

        sut.followEntities(fakeEntityType, listOfFakeEntityIds);

        verify(mockedFollowService).followEntities(fakeAccessTokenWithBearer, fakeEntityType, fakeEntityIds);
    }

    @Test
    void followEntitiesExecutesHttpCall() throws IOException {
        when(mockedVoidCall.execute()).thenReturn(Response.success(null));

        sut.followEntities(fakeEntityType, listOfFakeEntityIds);

        verify(mockedVoidCall).execute();
    }

    @Test
    void followEntitiesThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedVoidCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.followEntities(fakeEntityType, listOfFakeEntityIds));
    }

    @Test
    void followEntitiesThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedVoidCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.followEntities(fakeEntityType, listOfFakeEntityIds));
    }

    @Test
    void followPlaylistUsesCorrectValuesToCreateHttpCall() throws IOException {
        ArgumentCaptor<FollowPlaylistRequestBody> argumentCaptorRequestBody = ArgumentCaptor.forClass(FollowPlaylistRequestBody.class);
        ArgumentCaptor<String> argumentCaptorAccessToken = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> argumentCaptorPlaylistId = ArgumentCaptor.forClass(String.class);

        when(mockedVoidCall.execute()).thenReturn(Response.success(null));

        sut.followPlaylist(fakePlaylistId, true);

        verify(mockedFollowService).followPlaylist(argumentCaptorAccessToken.capture(), argumentCaptorPlaylistId.capture(), argumentCaptorRequestBody.capture());

        Assertions.assertEquals(fakeAccessTokenWithBearer, argumentCaptorAccessToken.getValue());
        Assertions.assertEquals(fakePlaylistId, argumentCaptorPlaylistId.getValue());
        Assertions.assertEquals(fakeFollowPlaylistRequestBody.isSetPlaylistPublic(), argumentCaptorRequestBody.getValue().isSetPlaylistPublic());
    }

    @Test
    void followPlaylistExecutesHttpCall() throws IOException {
        when(mockedVoidCall.execute()).thenReturn(Response.success(null));

        sut.followPlaylist(fakePlaylistId, false);

        verify(mockedVoidCall).execute();
    }

    @Test
    void followPlaylistThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedVoidCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.followPlaylist(fakePlaylistId, false));
    }

    @Test
    void followPlaylistThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedVoidCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.followPlaylist(fakePlaylistId, false));
    }

    @Test
    void getFollowedArtistsUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedArtistFullCursorBasedPagingWrapperCall.execute()).thenReturn(Response.success(new ArtistFullCursorBasedPagingWrapper()));

        sut.getFollowedArtists(fakeEntityType, null);

        verify(mockedFollowService).getFollowedArtists(fakeAccessTokenWithBearer, fakeEntityType, fakeOptionalParameters);
    }

    @Test
    void getFollowedArtistsExecutesHttpCall() throws IOException {
        when(mockedArtistFullCursorBasedPagingWrapperCall.execute()).thenReturn(Response.success(new ArtistFullCursorBasedPagingWrapper()));

        sut.getFollowedArtists(fakeEntityType, fakeOptionalParameters);

        verify(mockedArtistFullCursorBasedPagingWrapperCall).execute();
    }

    @Test
    void getFollowedArtistsThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedArtistFullCursorBasedPagingWrapperCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.getFollowedArtists(fakeEntityType, fakeOptionalParameters));
    }

    @Test
    void getFollowedArtistsThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedArtistFullCursorBasedPagingWrapperCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.getFollowedArtists(fakeEntityType, fakeOptionalParameters));
    }

    @Test
    void getFollowedArtistsThrowsSpotifyActionFailedExceptionWhenResponseBodyIsNull() throws IOException {
        when(mockedArtistFullCursorBasedPagingWrapperCall.execute()).thenReturn(Response.success(null));

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.getFollowedArtists(fakeEntityType, fakeOptionalParameters));
    }

    @Test
    void getFollowedArtistsReturnsCursorBasedPagingWhenSuccessful() throws IOException {
        final ArtistFullCursorBasedPagingWrapper fakeBody = new ArtistFullCursorBasedPagingWrapper();
        fakeBody.setArtists(new CursorBasedPaging<>());

        when(mockedArtistFullCursorBasedPagingWrapperCall.execute()).thenReturn(Response.success(fakeBody));

        Assertions.assertNotNull(sut.getFollowedArtists(fakeEntityType, fakeOptionalParameters));
    }

    @Test
    void unfollowEntitiesUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedVoidCall.execute()).thenReturn(Response.success(null));

        sut.unfollowEntities(fakeEntityType, listOfFakeEntityIds);

        verify(mockedFollowService).unfollowEntities(fakeAccessTokenWithBearer, fakeEntityType, fakeEntityIds);
    }

    @Test
    void unfollowEntitiesExecutesHttpCall() throws IOException {
        when(mockedVoidCall.execute()).thenReturn(Response.success(null));

        sut.unfollowEntities(fakeEntityType, listOfFakeEntityIds);

        verify(mockedVoidCall).execute();
    }

    @Test
    void unfollowEntitiesThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedVoidCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.unfollowEntities(fakeEntityType, listOfFakeEntityIds));
    }

    @Test
    void unfollowEntitiesThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedVoidCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.unfollowEntities(fakeEntityType, listOfFakeEntityIds));
    }

    @Test
    void unfollowPlaylistUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedVoidCall.execute()).thenReturn(Response.success(null));

        sut.unfollowPlaylist(fakePlaylistId);

        verify(mockedFollowService).unfollowPlaylist(fakeAccessTokenWithBearer, fakePlaylistId);
    }

    @Test
    void unfollowPlaylistExecutesHttpCall() throws IOException {
        when(mockedVoidCall.execute()).thenReturn(Response.success(null));

        sut.unfollowPlaylist(fakePlaylistId);

        verify(mockedVoidCall).execute();
    }

    @Test
    void unfollowPlaylistThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedVoidCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.unfollowPlaylist(fakePlaylistId));
    }

    @Test
    void unfollowPlaylistThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedVoidCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.unfollowPlaylist(fakePlaylistId));
    }
}
