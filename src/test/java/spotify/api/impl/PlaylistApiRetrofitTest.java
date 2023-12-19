package spotify.api.impl;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import retrofit2.Call;
import retrofit2.Response;
import spotify.exceptions.HttpRequestFailedException;
import spotify.exceptions.SpotifyActionFailedException;
import spotify.models.generic.Image;
import spotify.models.paging.Paging;
import spotify.models.playlists.PlaylistFull;
import spotify.models.playlists.PlaylistSimplified;
import spotify.models.playlists.PlaylistTrack;
import spotify.models.playlists.Snapshot;
import spotify.models.playlists.requests.*;
import spotify.retrofit.services.PlaylistService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PlaylistApiRetrofitTest extends AbstractApiRetrofitTest {
    private final String fakePlaylistId = "69";
    private final String fakeUserId = "420";
    private final String fakeBase64EncodedJpegImage = "peepeepoopoo";
    private final List<String> listOfFakeObjectUris = Arrays.asList("69", "420");
    private final List<String> listOfFakeItemUris = Arrays.asList("1337", "026");
    private final int fakePositionToInsert = 1;
    private PlaylistApiRetrofit sut;
    @Mock
    private PlaylistService mockedPlaylistService;
    @Mock
    private Call<Paging<PlaylistSimplified>> mockedPlaylistSimplifiedPagingCall;
    @Mock
    private Call<List<Image>> mockedListOfImagesCall;
    @Mock
    private Call<PlaylistFull> mockedPlaylistFullCall;
    @Mock
    private Call<Paging<PlaylistTrack>> mockedPlaylistTrackPagingCall;
    @Mock
    private Call<Void> mockedVoidCall;
    @Mock
    private Call<Snapshot> mockedSnapshotCall;
    @Mock
    private CreateUpdatePlaylistRequestBody mockedCreateUpdatePlaylistRequestBody;
    @Mock
    private ReorderPlaylistItemsRequestBody mockedReorderPlaylistItemsRequestBody;
    @Mock
    private DeleteItemsPlaylistRequestBody mockedDeleteItemsPlaylistRequestBody;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        sut = new PlaylistApiRetrofit(fakeAccessToken, mockedPlaylistService);

        when(mockedPlaylistService.getPlaylists(fakeAccessTokenWithBearer, fakeOptionalParameters)).thenReturn(mockedPlaylistSimplifiedPagingCall);
        when(mockedPlaylistService.getUserPlaylists(fakeAccessTokenWithBearer, fakeUserId, fakeOptionalParameters)).thenReturn(mockedPlaylistSimplifiedPagingCall);
        when(mockedPlaylistService.getPlaylistCoverImages(fakeAccessTokenWithBearer, fakePlaylistId)).thenReturn(mockedListOfImagesCall);
        when(mockedPlaylistService.getPlaylist(fakeAccessTokenWithBearer, fakePlaylistId, fakeOptionalParameters)).thenReturn(mockedPlaylistFullCall);
        when(mockedPlaylistService.getPlaylistTracks(fakeAccessTokenWithBearer, fakePlaylistId, fakeOptionalParameters)).thenReturn(mockedPlaylistTrackPagingCall);
        when(mockedPlaylistService.addItemToPlaylist(eq(fakeAccessTokenWithBearer), eq(fakePlaylistId), isA(AddItemPlaylistRequestBody.class))).thenReturn(mockedVoidCall);
        when(mockedPlaylistService.createPlaylist(eq(fakeAccessTokenWithBearer), eq(fakeUserId), isA(CreateUpdatePlaylistRequestBody.class))).thenReturn(mockedVoidCall);
        when(mockedPlaylistService.updatePlaylist(eq(fakeAccessTokenWithBearer), eq(fakePlaylistId), isA(CreateUpdatePlaylistRequestBody.class))).thenReturn(mockedVoidCall);
        when(mockedPlaylistService.reorderPlaylistItems(eq(fakeAccessTokenWithBearer), eq(fakePlaylistId), isA(ReorderPlaylistItemsRequestBody.class))).thenReturn(mockedSnapshotCall);
        when(mockedPlaylistService.replacePlaylistItems(eq(fakeAccessTokenWithBearer), eq(fakePlaylistId), isA(ReplacePlaylistItemsRequestBody.class))).thenReturn(mockedVoidCall);
        when(mockedPlaylistService.uploadCoverImageToPlaylist(eq(fakeAccessTokenWithBearer), eq(fakePlaylistId), isA(RequestBody.class))).thenReturn(mockedVoidCall);
        when(mockedPlaylistService.deleteItemsFromPlaylist(eq(fakeAccessTokenWithBearer), eq(fakePlaylistId), isA(DeleteItemsPlaylistRequestBody.class))).thenReturn(mockedSnapshotCall);

        when(mockedPlaylistSimplifiedPagingCall.request()).thenReturn(new Request.Builder().url(fakeUrl).build());
        when(mockedPlaylistFullCall.request()).thenReturn(new Request.Builder().url(fakeUrl).build());
        when(mockedVoidCall.request()).thenReturn(new Request.Builder().url(fakeUrl).build());
        when(mockedListOfImagesCall.request()).thenReturn(new Request.Builder().url(fakeUrl).build());
        when(mockedPlaylistTrackPagingCall.request()).thenReturn(new Request.Builder().url(fakeUrl).build());
        when(mockedSnapshotCall.request()).thenReturn(new Request.Builder().url(fakeUrl).build());
    }

    @Test
    void getPlaylistsUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedPlaylistSimplifiedPagingCall.execute()).thenReturn(Response.success(new Paging<>()));

        sut.getPlaylists(null);

        verify(mockedPlaylistService).getPlaylists(fakeAccessTokenWithBearer, fakeOptionalParameters);
    }

    @Test
    void getPlaylistsExecutesHttpCall() throws IOException {
        when(mockedPlaylistSimplifiedPagingCall.execute()).thenReturn(Response.success(new Paging<>()));

        sut.getPlaylists(fakeOptionalParameters);

        verify(mockedPlaylistSimplifiedPagingCall).execute();
    }

    @Test
    void getPlaylistsThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedPlaylistSimplifiedPagingCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.getPlaylists(fakeOptionalParameters));
    }

    @Test
    void getPlaylistsThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedPlaylistSimplifiedPagingCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.getPlaylists(fakeOptionalParameters));
    }

    @Test
    void getPlaylistsReturnsPlaylistSimplifiedPagingWhenSuccessful() throws IOException {
        when(mockedPlaylistSimplifiedPagingCall.execute()).thenReturn(Response.success(new Paging<>()));

        Assertions.assertNotNull(sut.getPlaylists(fakeOptionalParameters));
    }

    @Test
    void getUserPlaylistsUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedPlaylistSimplifiedPagingCall.execute()).thenReturn(Response.success(new Paging<>()));

        sut.getUserPlaylists(fakeUserId, null);

        verify(mockedPlaylistService).getUserPlaylists(fakeAccessTokenWithBearer, fakeUserId, fakeOptionalParameters);
    }

    @Test
    void getUserPlaylistsExecutesHttpCall() throws IOException {
        when(mockedPlaylistSimplifiedPagingCall.execute()).thenReturn(Response.success(new Paging<>()));

        sut.getUserPlaylists(fakeUserId, fakeOptionalParameters);

        verify(mockedPlaylistSimplifiedPagingCall).execute();
    }

    @Test
    void getUserPlaylistsThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedPlaylistSimplifiedPagingCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.getUserPlaylists(fakeUserId, fakeOptionalParameters));
    }

    @Test
    void getUserPlaylistsThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedPlaylistSimplifiedPagingCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.getUserPlaylists(fakeUserId, fakeOptionalParameters));
    }

    @Test
    void getUserPlaylistsReturnsPlaylistSimplifiedPagingWhenSuccessful() throws IOException {
        when(mockedPlaylistSimplifiedPagingCall.execute()).thenReturn(Response.success(new Paging<>()));

        Assertions.assertNotNull(sut.getUserPlaylists(fakeUserId, fakeOptionalParameters));
    }

    @Test
    void getPlaylistCoverImagesUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedListOfImagesCall.execute()).thenReturn(Response.success(new ArrayList<>()));

        sut.getPlaylistCoverImages(fakePlaylistId);

        verify(mockedPlaylistService).getPlaylistCoverImages(fakeAccessTokenWithBearer, fakePlaylistId);
    }

    @Test
    void getPlaylistCoverImagesExecutesHttpCall() throws IOException {
        when(mockedListOfImagesCall.execute()).thenReturn(Response.success(new ArrayList<>()));

        sut.getPlaylistCoverImages(fakePlaylistId);

        verify(mockedListOfImagesCall).execute();
    }

    @Test
    void getPlaylistCoverImagesThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedListOfImagesCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.getPlaylistCoverImages(fakePlaylistId));
    }

    @Test
    void getPlaylistCoverImagesThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedListOfImagesCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.getPlaylistCoverImages(fakePlaylistId));
    }

    @Test
    void getPlaylistCoverImagesReturnsListOfImagesWhenSuccessful() throws IOException {
        when(mockedListOfImagesCall.execute()).thenReturn(Response.success(new ArrayList<>()));

        Assertions.assertNotNull(sut.getPlaylistCoverImages(fakePlaylistId));
    }

    @Test
    void getPlaylistUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedPlaylistFullCall.execute()).thenReturn(Response.success(new PlaylistFull()));

        sut.getPlaylist(fakePlaylistId, null);

        verify(mockedPlaylistService).getPlaylist(fakeAccessTokenWithBearer, fakePlaylistId, fakeOptionalParameters);
    }

    @Test
    void getPlaylistExecutesHttpCall() throws IOException {
        when(mockedPlaylistFullCall.execute()).thenReturn(Response.success(new PlaylistFull()));

        sut.getPlaylist(fakePlaylistId, fakeOptionalParameters);

        verify(mockedPlaylistFullCall).execute();
    }

    @Test
    void getPlaylistThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedPlaylistFullCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.getPlaylist(fakePlaylistId, fakeOptionalParameters));
    }

    @Test
    void getPlaylistThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedPlaylistFullCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.getPlaylist(fakePlaylistId, fakeOptionalParameters));
    }

    @Test
    void getPlaylistReturnsPlaylistFullWhenSuccessful() throws IOException {
        when(mockedPlaylistFullCall.execute()).thenReturn(Response.success(new PlaylistFull()));

        Assertions.assertNotNull(sut.getPlaylist(fakePlaylistId, fakeOptionalParameters));
    }

    @Test
    void getPlaylistTracksUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedPlaylistTrackPagingCall.execute()).thenReturn(Response.success(new Paging<>()));

        sut.getPlaylistTracks(fakePlaylistId, null);

        verify(mockedPlaylistService).getPlaylistTracks(fakeAccessTokenWithBearer, fakePlaylistId, fakeOptionalParameters);
    }

    @Test
    void getPlaylistTracksExecutesHttpCall() throws IOException {
        when(mockedPlaylistTrackPagingCall.execute()).thenReturn(Response.success(new Paging<>()));

        sut.getPlaylistTracks(fakePlaylistId, fakeOptionalParameters);

        verify(mockedPlaylistTrackPagingCall).execute();
    }

    @Test
    void getPlaylistTracksThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedPlaylistTrackPagingCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.getPlaylistTracks(fakePlaylistId, fakeOptionalParameters));
    }

    @Test
    void getPlaylistTracksThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedPlaylistTrackPagingCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.getPlaylistTracks(fakePlaylistId, fakeOptionalParameters));
    }

    @Test
    void getPlaylistTracksReturnsPlaylistTrackPagingCallWhenSuccessful() throws IOException {
        when(mockedPlaylistTrackPagingCall.execute()).thenReturn(Response.success(new Paging<>()));

        Assertions.assertNotNull(sut.getPlaylistTracks(fakePlaylistId, fakeOptionalParameters));
    }

    @Test
    void addItemToPlaylistUsesCorrectValuesToCreateHttpCall() throws IOException {
        ArgumentCaptor<AddItemPlaylistRequestBody> requestBodyArgumentCaptor = ArgumentCaptor.forClass(AddItemPlaylistRequestBody.class);

        when(mockedVoidCall.execute()).thenReturn(Response.success(null));

        sut.addItemToPlaylist(listOfFakeObjectUris, fakePlaylistId, fakePositionToInsert);

        //verify that correct values are passed in the service function
        verify(mockedPlaylistService).addItemToPlaylist(eq(fakeAccessTokenWithBearer), eq(fakePlaylistId), requestBodyArgumentCaptor.capture());

        final List<String> actualListOfFakeObjectUris = requestBodyArgumentCaptor.getValue().getUris();
        final int actualFakePositionToInsert = requestBodyArgumentCaptor.getValue().getPosition();

        //verify that the content of request body is as expected
        Assertions.assertEquals(actualListOfFakeObjectUris, listOfFakeObjectUris);
        Assertions.assertEquals(actualFakePositionToInsert, fakePositionToInsert);
    }

    @Test
    void addItemToPlaylistExecutesHttpCall() throws IOException {
        when(mockedVoidCall.execute()).thenReturn(Response.success(null));

        sut.addItemToPlaylist(listOfFakeObjectUris, fakePlaylistId, fakePositionToInsert);

        verify(mockedVoidCall).execute();
    }

    @Test
    void addItemToPlaylistThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedVoidCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.addItemToPlaylist(listOfFakeObjectUris, fakePlaylistId, fakePositionToInsert));
    }

    @Test
    void addItemToPlaylistThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedVoidCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.addItemToPlaylist(listOfFakeObjectUris, fakePlaylistId, fakePositionToInsert));
    }

    @Test
    void createPlaylistUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedCreateUpdatePlaylistRequestBody.getName()).thenReturn("jiankai zheng");
        when(mockedVoidCall.execute()).thenReturn(Response.success(null));

        sut.createPlaylist(fakeUserId, mockedCreateUpdatePlaylistRequestBody);

        verify(mockedPlaylistService).createPlaylist(fakeAccessTokenWithBearer, fakeUserId, mockedCreateUpdatePlaylistRequestBody);
    }

    @Test
    void createPlaylistExecutesHttpCall() throws IOException {
        when(mockedCreateUpdatePlaylistRequestBody.getName()).thenReturn("jiankai zheng");
        when(mockedVoidCall.execute()).thenReturn(Response.success(null));

        sut.createPlaylist(fakeUserId, mockedCreateUpdatePlaylistRequestBody);

        verify(mockedVoidCall).execute();
    }

    @Test
    void createPlaylistThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedCreateUpdatePlaylistRequestBody.getName()).thenReturn("jiankai zheng");
        when(mockedVoidCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.createPlaylist(fakeUserId, mockedCreateUpdatePlaylistRequestBody));
    }

    @Test
    void createPlaylistThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedCreateUpdatePlaylistRequestBody.getName()).thenReturn("jiankai zheng");
        when(mockedVoidCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.createPlaylist(fakeUserId, mockedCreateUpdatePlaylistRequestBody));
    }

    @Test
    void createPlaylistThrowsIllegalArgumentExceptionWhenUserIdIsNull() throws IOException {
        when(mockedVoidCall.execute()).thenReturn(Response.success(null));

        Assertions.assertThrows(IllegalArgumentException.class, () -> sut.createPlaylist(null, mockedCreateUpdatePlaylistRequestBody));
    }

    @Test
    void createPlaylistThrowsIllegalArgumentExceptionWhenUserIdIsEmptry() throws IOException {
        when(mockedVoidCall.execute()).thenReturn(Response.success(null));

        Assertions.assertThrows(IllegalArgumentException.class, () -> sut.createPlaylist("", mockedCreateUpdatePlaylistRequestBody));
    }

    @Test
    void createPlaylistThrowsIllegalArgumentExceptionWhenNameIsNullInsideRequestBody() throws IOException {
        when(mockedCreateUpdatePlaylistRequestBody.getName()).thenReturn(null);
        when(mockedVoidCall.execute()).thenReturn(Response.success(null));

        Assertions.assertThrows(IllegalArgumentException.class, () -> sut.createPlaylist(fakeUserId, mockedCreateUpdatePlaylistRequestBody));
    }

    @Test
    void createPlaylistThrowsIllegalArgumentExceptionWhenNameIsEmptyInsideRequestBody() throws IOException {
        when(mockedCreateUpdatePlaylistRequestBody.getName()).thenReturn("");
        when(mockedVoidCall.execute()).thenReturn(Response.success(null));

        Assertions.assertThrows(IllegalArgumentException.class, () -> sut.createPlaylist(fakeUserId, mockedCreateUpdatePlaylistRequestBody));
    }

    @Test
    void updatePlaylistUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedVoidCall.execute()).thenReturn(Response.success(null));

        sut.updatePlaylist(fakePlaylistId, mockedCreateUpdatePlaylistRequestBody);

        verify(mockedPlaylistService).updatePlaylist(fakeAccessTokenWithBearer, fakePlaylistId, mockedCreateUpdatePlaylistRequestBody);
    }

    @Test
    void updatePlaylistExecutesHttpCall() throws IOException {
        when(mockedVoidCall.execute()).thenReturn(Response.success(null));

        sut.updatePlaylist(fakePlaylistId, mockedCreateUpdatePlaylistRequestBody);

        verify(mockedVoidCall).execute();
    }

    @Test
    void updatePlaylistThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedVoidCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.updatePlaylist(fakePlaylistId, mockedCreateUpdatePlaylistRequestBody));
    }

    @Test
    void updatePlaylistThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedVoidCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.updatePlaylist(fakePlaylistId, mockedCreateUpdatePlaylistRequestBody));
    }

    @Test
    void updatePlaylistThrowsIllegalArgumentExceptionWhenPlaylistIdIsNull() throws IOException {
        when(mockedVoidCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(IllegalArgumentException.class, () -> sut.updatePlaylist(null, mockedCreateUpdatePlaylistRequestBody));
    }

    @Test
    void updatePlaylistThrowsIllegalArgumentExceptionWhenPlaylistIdEmpty() throws IOException {
        when(mockedVoidCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(IllegalArgumentException.class, () -> sut.updatePlaylist("", mockedCreateUpdatePlaylistRequestBody));
    }

    @Test
    void reorderPlaylistItemsUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedSnapshotCall.execute()).thenReturn(Response.success(new Snapshot()));

        sut.reorderPlaylistItems(fakePlaylistId, mockedReorderPlaylistItemsRequestBody);

        verify(mockedPlaylistService).reorderPlaylistItems(fakeAccessTokenWithBearer, fakePlaylistId, mockedReorderPlaylistItemsRequestBody);
    }

    @Test
    void reorderPlaylistItemsExecutesHttpCall() throws IOException {
        when(mockedSnapshotCall.execute()).thenReturn(Response.success(new Snapshot()));

        sut.reorderPlaylistItems(fakePlaylistId, mockedReorderPlaylistItemsRequestBody);

        verify(mockedSnapshotCall).execute();
    }

    @Test
    void reorderPlaylistItemsThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedSnapshotCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.reorderPlaylistItems(fakePlaylistId, mockedReorderPlaylistItemsRequestBody));
    }

    @Test
    void reorderPlaylistItemsThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedSnapshotCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.reorderPlaylistItems(fakePlaylistId, mockedReorderPlaylistItemsRequestBody));
    }

    @Test
    void reorderPlaylistItemsThrowsIllegalArgumentExceptionWhenPlaylistIdIsNull() throws IOException {
        when(mockedSnapshotCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(IllegalArgumentException.class, () -> sut.reorderPlaylistItems(null, mockedReorderPlaylistItemsRequestBody));
    }

    @Test
    void reorderPlaylistItemsThrowsIllegalArgumentExceptionWhenPlaylistIdEmpty() throws IOException {
        when(mockedSnapshotCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(IllegalArgumentException.class, () -> sut.reorderPlaylistItems("", mockedReorderPlaylistItemsRequestBody));
    }

    @Test
    void reorderPlaylistItemsSetsSnapshotIdToNullWhenSnapshotIsEmpty() throws IOException {
        when(mockedSnapshotCall.execute()).thenReturn(Response.success(new Snapshot()));
        when(mockedReorderPlaylistItemsRequestBody.getSnapshotId()).thenReturn("");

        sut.reorderPlaylistItems("69", mockedReorderPlaylistItemsRequestBody);

        verify(mockedReorderPlaylistItemsRequestBody).setSnapshotId(null);
    }

    @Test
    void reorderPlaylistItemsUnwrappedUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedSnapshotCall.execute()).thenReturn(Response.success(new Snapshot()));

        sut.reorderPlaylistItemsUnwrapped(fakePlaylistId, mockedReorderPlaylistItemsRequestBody);

        verify(mockedPlaylistService).reorderPlaylistItems(fakeAccessTokenWithBearer, fakePlaylistId, mockedReorderPlaylistItemsRequestBody);
    }

    @Test
    void reorderPlaylistItemsUnwrappedExecutesHttpCall() throws IOException {
        when(mockedSnapshotCall.execute()).thenReturn(Response.success(new Snapshot()));

        sut.reorderPlaylistItemsUnwrapped(fakePlaylistId, mockedReorderPlaylistItemsRequestBody);

        verify(mockedSnapshotCall).execute();
    }

    @Test
    void reorderPlaylistItemsUnwrappedThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedSnapshotCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.reorderPlaylistItemsUnwrapped(fakePlaylistId, mockedReorderPlaylistItemsRequestBody));
    }

    @Test
    void reorderPlaylistItemsUnwrappedThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedSnapshotCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.reorderPlaylistItemsUnwrapped(fakePlaylistId, mockedReorderPlaylistItemsRequestBody));
    }

    @Test
    void reorderPlaylistItemsUnwrappedThrowsIllegalArgumentExceptionWhenPlaylistIdIsNull() throws IOException {
        when(mockedSnapshotCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(IllegalArgumentException.class, () -> sut.reorderPlaylistItemsUnwrapped(null, mockedReorderPlaylistItemsRequestBody));
    }

    @Test
    void reorderPlaylistItemsUnwrappedThrowsIllegalArgumentExceptionWhenPlaylistIdEmpty() throws IOException {
        when(mockedSnapshotCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(IllegalArgumentException.class, () -> sut.reorderPlaylistItemsUnwrapped("", mockedReorderPlaylistItemsRequestBody));
    }

    @Test
    void reorderPlaylistItemsUnwrappedSetsSnapshotIdToNullWhenSnapshotIsEmpty() throws IOException {
        when(mockedSnapshotCall.execute()).thenReturn(Response.success(new Snapshot()));
        when(mockedReorderPlaylistItemsRequestBody.getSnapshotId()).thenReturn("");

        sut.reorderPlaylistItemsUnwrapped("69", mockedReorderPlaylistItemsRequestBody);

        verify(mockedReorderPlaylistItemsRequestBody).setSnapshotId(null);
    }

    @Test
    void reorderPlaylistItemsUnwrappedThrowsSpotifyActionFailedExceptionWhenEmptyResponseBody() throws IOException {
        when(mockedSnapshotCall.execute())
                .thenReturn(
                        Response.success(null)
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.reorderPlaylistItemsUnwrapped(fakePlaylistId, mockedReorderPlaylistItemsRequestBody));
    }

    @Test
    void replacePlaylistItemsUsesCorrectValuesToCreateHttpCall() throws IOException {
        ArgumentCaptor<ReplacePlaylistItemsRequestBody> requestBodyArgumentCaptor = ArgumentCaptor.forClass(ReplacePlaylistItemsRequestBody.class);

        when(mockedVoidCall.execute()).thenReturn(Response.success(null));

        sut.replacePlaylistItems(fakePlaylistId, listOfFakeItemUris);

        verify(mockedPlaylistService).replacePlaylistItems(eq(fakeAccessTokenWithBearer), eq(fakePlaylistId), requestBodyArgumentCaptor.capture());

        //verify that request body got correctly constructed with the list inside it
        Assertions.assertEquals(listOfFakeItemUris, requestBodyArgumentCaptor.getValue().getUris());
    }

    @Test
    void replacePlaylistItemsExecutesHttpCall() throws IOException {
        when(mockedVoidCall.execute()).thenReturn(Response.success(null));

        sut.replacePlaylistItems(fakePlaylistId, listOfFakeItemUris);

        verify(mockedVoidCall).execute();
    }

    @Test
    void replacePlaylistItemsThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedVoidCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.replacePlaylistItems(fakePlaylistId, listOfFakeItemUris));
    }

    @Test
    void replacePlaylistItemsThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedVoidCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.replacePlaylistItems(fakePlaylistId, listOfFakeItemUris));
    }

    @Test
    void replacePlaylistItemsThrowsIllegalArgumentExceptionWhenPlaylistIdIsNull() throws IOException {
        when(mockedVoidCall.execute()).thenReturn(Response.success(null));

        Assertions.assertThrows(IllegalArgumentException.class, () -> sut.replacePlaylistItems(null, listOfFakeItemUris));
    }

    @Test
    void replacePlaylistItemsThrowsIllegalArgumentExceptionWhenPlaylistIdEmpty() throws IOException {
        when(mockedVoidCall.execute()).thenReturn(Response.success(null));

        Assertions.assertThrows(IllegalArgumentException.class, () -> sut.replacePlaylistItems("", listOfFakeItemUris));
    }

    @Test
    void uploadCoverImageToPlaylistUsesCorrectValuesToCreateHttpCall() throws IOException {
        ArgumentCaptor<RequestBody> requestBodyArgumentCaptor = ArgumentCaptor.forClass(RequestBody.class);

        when(mockedVoidCall.execute()).thenReturn(Response.success(null));

        sut.uploadCoverImageToPlaylist(fakePlaylistId, fakeBase64EncodedJpegImage);

        verify(mockedPlaylistService).uploadCoverImageToPlaylist(eq(fakeAccessTokenWithBearer), eq(fakePlaylistId), requestBodyArgumentCaptor.capture());

        final Buffer actualValue = new Buffer();
        requestBodyArgumentCaptor.getValue().writeTo(actualValue);

        //verify that request body got correctly constructed with the base64EncodedJpegImage inside it
        Assertions.assertEquals(fakeBase64EncodedJpegImage, actualValue.readUtf8());
    }

    @Test
    void uploadCoverImageToPlaylistExecutesHttpCall() throws IOException {
        when(mockedVoidCall.execute()).thenReturn(Response.success(null));

        sut.uploadCoverImageToPlaylist(fakePlaylistId, fakeBase64EncodedJpegImage);

        verify(mockedVoidCall).execute();
    }

    @Test
    void uploadCoverImageToPlaylistThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedVoidCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.uploadCoverImageToPlaylist(fakePlaylistId, fakeBase64EncodedJpegImage));
    }

    @Test
    void uploadCoverImageToPlaylistThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedVoidCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.uploadCoverImageToPlaylist(fakePlaylistId, fakeBase64EncodedJpegImage));
    }

    @Test
    void uploadCoverImageToPlaylistThrowsIllegalArgumentExceptionWhenPlaylistIdIsNull() throws IOException {
        when(mockedVoidCall.execute()).thenReturn(Response.success(null));

        Assertions.assertThrows(IllegalArgumentException.class, () -> sut.uploadCoverImageToPlaylist(null, fakeBase64EncodedJpegImage));
    }

    @Test
    void uploadCoverImageToPlaylistThrowsIllegalArgumentExceptionWhenPlaylistIdEmpty() throws IOException {
        when(mockedVoidCall.execute()).thenReturn(Response.success(null));

        Assertions.assertThrows(IllegalArgumentException.class, () -> sut.uploadCoverImageToPlaylist("", fakeBase64EncodedJpegImage));
    }

    @Test
    void uploadCoverImageToPlaylistThrowsIllegalArgumentExceptionWhenBase64EncodedJpegImageIsNull() throws IOException {
        when(mockedVoidCall.execute()).thenReturn(Response.success(null));

        Assertions.assertThrows(IllegalArgumentException.class, () -> sut.uploadCoverImageToPlaylist(fakePlaylistId, null));
    }

    @Test
    void uploadCoverImageToPlaylistThrowsIllegalArgumentExceptionWhenBase64EncodedJpegImageIsEmpty() throws IOException {
        when(mockedVoidCall.execute()).thenReturn(Response.success(null));

        Assertions.assertThrows(IllegalArgumentException.class, () -> sut.uploadCoverImageToPlaylist(fakePlaylistId, ""));
    }

    @Test
    void deleteItemsFromPlaylistUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedSnapshotCall.execute()).thenReturn(Response.success(null));

        sut.deleteItemsFromPlaylist(fakePlaylistId, mockedDeleteItemsPlaylistRequestBody);

        verify(mockedPlaylistService).deleteItemsFromPlaylist(fakeAccessTokenWithBearer, fakePlaylistId, mockedDeleteItemsPlaylistRequestBody);
    }

    @Test
    void deleteItemsFromPlaylistExecutesHttpCall() throws IOException {
        when(mockedSnapshotCall.execute()).thenReturn(Response.success(null));

        sut.deleteItemsFromPlaylist(fakePlaylistId, mockedDeleteItemsPlaylistRequestBody);

        verify(mockedSnapshotCall).execute();
    }

    @Test
    void deleteItemsFromPlaylistThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedSnapshotCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.deleteItemsFromPlaylist(fakePlaylistId, mockedDeleteItemsPlaylistRequestBody));
    }

    @Test
    void deleteItemsFromPlaylistThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedSnapshotCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.deleteItemsFromPlaylist(fakePlaylistId, mockedDeleteItemsPlaylistRequestBody));
    }

    @Test
    void deleteItemsFromPlaylistThrowsIllegalArgumentExceptionWhenPlaylistIdIsNull() throws IOException {
        when(mockedSnapshotCall.execute()).thenReturn(Response.success(null));

        Assertions.assertThrows(IllegalArgumentException.class, () -> sut.deleteItemsFromPlaylist(null, mockedDeleteItemsPlaylistRequestBody));
    }

    @Test
    void deleteItemsFromPlaylistThrowsIllegalArgumentExceptionWhenPlaylistIdEmpty() throws IOException {
        when(mockedSnapshotCall.execute()).thenReturn(Response.success(null));

        Assertions.assertThrows(IllegalArgumentException.class, () -> sut.deleteItemsFromPlaylist("", mockedDeleteItemsPlaylistRequestBody));
    }

    @Test
    void deleteItemsFromPlaylistSetsSnapshotIdToNullWhenSnapshotIdIsEmpty() throws IOException {
        when(mockedDeleteItemsPlaylistRequestBody.getSnapshotId()).thenReturn("");
        when(mockedSnapshotCall.execute()).thenReturn(Response.success(null));

        sut.deleteItemsFromPlaylist(fakePlaylistId, mockedDeleteItemsPlaylistRequestBody);

        verify(mockedDeleteItemsPlaylistRequestBody).setSnapshotId(null);
    }

    @Test
    void deleteItemsFromPlaylistUnwrappedUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedSnapshotCall.execute()).thenReturn(Response.success(new Snapshot()));

        sut.deleteItemsFromPlaylistUnwrapped(fakePlaylistId, mockedDeleteItemsPlaylistRequestBody);

        verify(mockedPlaylistService).deleteItemsFromPlaylist(fakeAccessTokenWithBearer, fakePlaylistId, mockedDeleteItemsPlaylistRequestBody);
    }

    @Test
    void deleteItemsFromPlaylistUnwrappedExecutesHttpCall() throws IOException {
        when(mockedSnapshotCall.execute()).thenReturn(Response.success(new Snapshot()));

        sut.deleteItemsFromPlaylistUnwrapped(fakePlaylistId, mockedDeleteItemsPlaylistRequestBody);

        verify(mockedSnapshotCall).execute();
    }

    @Test
    void deleteItemsFromPlaylistUnwrappedThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedSnapshotCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.deleteItemsFromPlaylistUnwrapped(fakePlaylistId, mockedDeleteItemsPlaylistRequestBody));
    }

    @Test
    void deleteItemsFromPlaylistUnwrappedThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedSnapshotCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.deleteItemsFromPlaylistUnwrapped(fakePlaylistId, mockedDeleteItemsPlaylistRequestBody));
    }

    @Test
    void deleteItemsFromPlaylistUnwrappedThrowsIllegalArgumentExceptionWhenPlaylistIdIsNull() throws IOException {
        when(mockedSnapshotCall.execute()).thenReturn(Response.success(null));

        Assertions.assertThrows(IllegalArgumentException.class, () -> sut.deleteItemsFromPlaylistUnwrapped(null, mockedDeleteItemsPlaylistRequestBody));
    }

    @Test
    void deleteItemsFromPlaylistUnwrappedThrowsIllegalArgumentExceptionWhenPlaylistIdEmpty() throws IOException {
        when(mockedSnapshotCall.execute()).thenReturn(Response.success(null));

        Assertions.assertThrows(IllegalArgumentException.class, () -> sut.deleteItemsFromPlaylistUnwrapped("", mockedDeleteItemsPlaylistRequestBody));
    }

    @Test
    void deleteItemsFromPlaylistUnwrappedSetsSnapshotIdToNullWhenSnapshotIdIsEmpty() throws IOException {
        when(mockedDeleteItemsPlaylistRequestBody.getSnapshotId()).thenReturn("");
        when(mockedSnapshotCall.execute()).thenReturn(Response.success(new Snapshot()));

        sut.deleteItemsFromPlaylistUnwrapped(fakePlaylistId, mockedDeleteItemsPlaylistRequestBody);

        verify(mockedDeleteItemsPlaylistRequestBody).setSnapshotId(null);
    }

    @Test
    void deleteItemsFromPlaylistUnwrappedThrowsSpotifyActionFailedExceptionWhenEmptyResponseBody() throws IOException {
        when(mockedSnapshotCall.execute())
                .thenReturn(
                        Response.success(null)
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.deleteItemsFromPlaylistUnwrapped(fakePlaylistId, mockedDeleteItemsPlaylistRequestBody));
    }
}
