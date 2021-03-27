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
import spotify.models.albums.AlbumSimplifiedPaging;
import spotify.models.categories.CategoryFull;
import spotify.models.categories.CategoryFullPaging;
import spotify.models.paging.Paging;
import spotify.models.playlists.FeaturedPlaylistCollection;
import spotify.models.playlists.PlaylistSimplifiedPaging;
import spotify.models.recommendations.RecommendationCollection;
import spotify.retrofit.services.BrowseService;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BrowseApiRetrofitTest extends AbstractApiRetrofitTest {
    private final String fakeCategoryId = "69";
    private final List<String> listOfFakeSeedArtists = Arrays.asList("jiankai", "zheng");
    private final List<String> listOfFakeSeedGenres = Arrays.asList("kpop", "krnb");
    private final List<String> listOfFakeSeedTracks = Arrays.asList("bts", "blackpink");
    private final String fakeSeedArtists = String.join(",", listOfFakeSeedArtists);
    private final String fakeSeedGenres = String.join(",", listOfFakeSeedGenres);
    private final String fakeSeedTracks = String.join(",", listOfFakeSeedTracks);
    private final Map<String, String> fakeOptionalParametersWithSeeds = new HashMap<>() {
        {
            put("seed_artists", fakeSeedArtists);
            put("seed_genres", fakeSeedGenres);
            put("seed_tracks", fakeSeedTracks);
        }
    };
    private BrowseApiRetrofit sut;
    @Mock
    private BrowseService mockedBrowseService;
    @Mock
    private Call<CategoryFull> mockedCategoryFullCall;
    @Mock
    private Call<PlaylistSimplifiedPaging> mockedPlaylistSimplifiedPagingCall;
    @Mock
    private Call<CategoryFullPaging> mockedCategoryFullPagingCall;
    @Mock
    private Call<FeaturedPlaylistCollection> mockedFeaturedPlaylistCollectionCall;
    @Mock
    private Call<AlbumSimplifiedPaging> mockedAlbumSimplifiedPagingCall;
    @Mock
    private Call<RecommendationCollection> mockedRecommendationCollectionCall;
    @Mock
    private Map<String, String> mockedOptionalParametersWithSeeds;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        sut = new BrowseApiRetrofit(fakeAccessToken, mockedBrowseService);

        when(mockedBrowseService.getCategory(fakeAccessTokenWithBearer, fakeCategoryId, fakeOptionalParameters)).thenReturn(mockedCategoryFullCall);
        when(mockedBrowseService.getCategories(fakeAccessTokenWithBearer, fakeOptionalParameters)).thenReturn(mockedCategoryFullPagingCall);
        when(mockedBrowseService.getCategoryPlaylists(fakeAccessTokenWithBearer, fakeCategoryId, fakeOptionalParameters)).thenReturn(mockedPlaylistSimplifiedPagingCall);
        when(mockedBrowseService.getFeaturedPlaylists(fakeAccessTokenWithBearer, fakeOptionalParameters)).thenReturn(mockedFeaturedPlaylistCollectionCall);
        when(mockedBrowseService.getNewReleases(fakeAccessTokenWithBearer, fakeOptionalParameters)).thenReturn(mockedAlbumSimplifiedPagingCall);
        when(mockedBrowseService.getRecommendations(fakeAccessTokenWithBearer, mockedOptionalParametersWithSeeds)).thenReturn(mockedRecommendationCollectionCall);

        when(mockedCategoryFullCall.request()).thenReturn(new Request.Builder().url(fakeUrl).build());
        when(mockedCategoryFullPagingCall.request()).thenReturn(new Request.Builder().url(fakeUrl).build());
        when(mockedPlaylistSimplifiedPagingCall.request()).thenReturn(new Request.Builder().url(fakeUrl).build());
        when(mockedFeaturedPlaylistCollectionCall.request()).thenReturn(new Request.Builder().url(fakeUrl).build());
        when(mockedAlbumSimplifiedPagingCall.request()).thenReturn(new Request.Builder().url(fakeUrl).build());
        when(mockedRecommendationCollectionCall.request()).thenReturn(new Request.Builder().url(fakeUrl).build());
    }

    @Test
    void getCategoryUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedCategoryFullCall.execute()).thenReturn(Response.success(new CategoryFull()));

        sut.getCategory(fakeCategoryId, null);

        verify(mockedBrowseService).getCategory(fakeAccessTokenWithBearer, fakeCategoryId, fakeOptionalParameters);
    }

    @Test
    void getCategoryExecutesHttpCall() throws IOException {
        when(mockedCategoryFullCall.execute()).thenReturn(Response.success(new CategoryFull()));

        sut.getCategory(fakeCategoryId, fakeOptionalParameters);
        verify(mockedCategoryFullCall).execute();
    }

    @Test
    void getCategoryThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedCategoryFullCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.getCategory(fakeCategoryId, fakeOptionalParameters));
    }

    @Test
    void getCategoryThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedCategoryFullCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.getCategory(fakeCategoryId, fakeOptionalParameters));
    }

    @Test
    void getCategoryReturnsCategoryFullWhenSuccessful() throws IOException {
        when(mockedCategoryFullCall.execute()).thenReturn(Response.success(new CategoryFull()));

        Assertions.assertNotNull(sut.getCategory(fakeCategoryId, fakeOptionalParameters));
    }

    @Test @Deprecated(since = "1.5.8")
    void getCategoriesUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedCategoryFullPagingCall.execute()).thenReturn(Response.success(createCategoryFullPaging()));

        sut.getCategories(null);

        verify(mockedBrowseService).getCategories(fakeAccessTokenWithBearer, fakeOptionalParameters);
    }
    
    @Test
    void getCategoriesPagingUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedCategoryFullPagingCall.execute()).thenReturn(Response.success(createCategoryFullPaging()));

        sut.getCategoriesPaging(null);

        verify(mockedBrowseService).getCategories(fakeAccessTokenWithBearer, fakeOptionalParameters);
    }
    
    @Test @Deprecated(since = "1.5.8")
    void getCategoriesExecutesHttpCall() throws IOException {
        when(mockedCategoryFullPagingCall.execute()).thenReturn(Response.success(createCategoryFullPaging()));

        sut.getCategories(fakeOptionalParameters);
        verify(mockedCategoryFullPagingCall).execute();
    }
    
    @Test
    void getCategoriesPagingExecutesHttpCall() throws IOException {
        when(mockedCategoryFullPagingCall.execute()).thenReturn(Response.success(createCategoryFullPaging()));

        sut.getCategoriesPaging(fakeOptionalParameters);
        verify(mockedCategoryFullPagingCall).execute();
    }


    
    @Test @Deprecated(since = "1.5.8") @SuppressWarnings("deprecation")
    void getCategoriesThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedCategoryFullPagingCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.getCategories(fakeOptionalParameters));
    }
    
    @Test
    void getCategoriesPagingThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedCategoryFullPagingCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.getCategoriesPaging(fakeOptionalParameters));
    }

    @Test @Deprecated(since = "1.5.8") @SuppressWarnings("deprecation")
    void getCategoriesThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedCategoryFullPagingCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.getCategories(fakeOptionalParameters));
    }
    
    @Test
    void getCategoriesPagingThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedCategoryFullPagingCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.getCategoriesPaging(fakeOptionalParameters));
    }

    @Test @Deprecated(since = "1.5.8")
    void getCategoriesReturnsCategoryFullPagingWhenSuccessful() throws IOException {
        when(mockedCategoryFullPagingCall.execute()).thenReturn(Response.success(createCategoryFullPaging()));

        Assertions.assertNotNull(sut.getCategories(fakeOptionalParameters));
    }
    
    @Test 
    void getCategoriesPagingReturnsCategoryFullPagingWhenSuccessful() throws IOException {
        when(mockedCategoryFullPagingCall.execute()).thenReturn(Response.success(createCategoryFullPaging()));

        Assertions.assertNotNull(sut.getCategoriesPaging(fakeOptionalParameters));
    }

    @Test @Deprecated(since = "1.5.8")
    void getCategoryPlaylistsUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedPlaylistSimplifiedPagingCall.execute()).thenReturn(Response.success(createPlaylistSimplifiedPaging()));

        sut.getCategoryPlaylists(fakeCategoryId, null);

        verify(mockedBrowseService).getCategoryPlaylists(fakeAccessTokenWithBearer, fakeCategoryId, fakeOptionalParameters);
    }
    
    @Test
    void getCategoryPlaylistsPagingUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedPlaylistSimplifiedPagingCall.execute()).thenReturn(Response.success(createPlaylistSimplifiedPaging()));

        sut.getCategoryPlaylistsPaging(fakeCategoryId, null);

        verify(mockedBrowseService).getCategoryPlaylists(fakeAccessTokenWithBearer, fakeCategoryId, fakeOptionalParameters);
    }

    @Test @Deprecated(since = "1.5.8")
    void getCategoryPlaylistsExecutesHttpCall() throws IOException {
        when(mockedPlaylistSimplifiedPagingCall.execute()).thenReturn(Response.success(createPlaylistSimplifiedPaging()));

        sut.getCategoryPlaylists(fakeCategoryId, fakeOptionalParameters);
        verify(mockedPlaylistSimplifiedPagingCall).execute();
    }
    
    @Test
    void getCategoryPlaylistsPagingExecutesHttpCall() throws IOException {
        when(mockedPlaylistSimplifiedPagingCall.execute()).thenReturn(Response.success(createPlaylistSimplifiedPaging()));

        sut.getCategoryPlaylistsPaging(fakeCategoryId, fakeOptionalParameters);
        verify(mockedPlaylistSimplifiedPagingCall).execute();
    }

    @Test @Deprecated(since = "1.5.8") @SuppressWarnings("deprecation")
    void getCategoryPlaylistsThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedPlaylistSimplifiedPagingCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.getCategoryPlaylists(fakeCategoryId, fakeOptionalParameters));
    }

    @Test
    void getCategoryPlaylistsPagingThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedPlaylistSimplifiedPagingCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.getCategoryPlaylistsPaging(fakeCategoryId, fakeOptionalParameters));
    }

    @Test @Deprecated(since = "1.5.8") @SuppressWarnings("deprecation")
    void getCategoryPlaylistsThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedPlaylistSimplifiedPagingCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.getCategoryPlaylists(fakeCategoryId, fakeOptionalParameters));
    }
    
    @Test
    void getCategoryPlaylistsPagingThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedPlaylistSimplifiedPagingCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.getCategoryPlaylistsPaging(fakeCategoryId, fakeOptionalParameters));
    }

    @Test @Deprecated(since = "1.5.8")
    void getCategoryPlaylistsReturnsPlaylistSimplifiedPagingWhenSuccessful() throws IOException {
        when(mockedPlaylistSimplifiedPagingCall.execute()).thenReturn(Response.success(createPlaylistSimplifiedPaging()));

        Assertions.assertNotNull(sut.getCategoryPlaylists(fakeCategoryId, fakeOptionalParameters));
    }
    
    @Test
    void getCategoryPlaylistsPagingReturnsPlaylistSimplifiedPagingWhenSuccessful() throws IOException {
        when(mockedPlaylistSimplifiedPagingCall.execute()).thenReturn(Response.success(createPlaylistSimplifiedPaging()));

        Assertions.assertNotNull(sut.getCategoryPlaylistsPaging(fakeCategoryId, fakeOptionalParameters));
    }

    @Test
    void getFeaturedPlaylistsUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedFeaturedPlaylistCollectionCall.execute()).thenReturn(Response.success(new FeaturedPlaylistCollection()));

        sut.getFeaturedPlaylists(null);

        verify(mockedBrowseService).getFeaturedPlaylists(fakeAccessTokenWithBearer, fakeOptionalParameters);
    }

    @Test
    void getFeaturedPlaylistsExecutesHttpCall() throws IOException {
        when(mockedFeaturedPlaylistCollectionCall.execute()).thenReturn(Response.success(new FeaturedPlaylistCollection()));

        sut.getFeaturedPlaylists(fakeOptionalParameters);
        verify(mockedFeaturedPlaylistCollectionCall).execute();
    }

    @Test
    void getFeaturedPlaylistsThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedFeaturedPlaylistCollectionCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.getFeaturedPlaylists(fakeOptionalParameters));
    }

    @Test
    void getFeaturedPlaylistsThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedFeaturedPlaylistCollectionCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.getFeaturedPlaylists(fakeOptionalParameters));
    }

    @Test
    void getFeaturedPlaylistsReturnsFeaturedPlaylistCollectionWhenSuccessful() throws IOException {
        when(mockedFeaturedPlaylistCollectionCall.execute()).thenReturn(Response.success(new FeaturedPlaylistCollection()));

        Assertions.assertNotNull(sut.getFeaturedPlaylists(fakeOptionalParameters));
    }

    @Test @Deprecated(since = "1.5.8")
    void getNewReleasesUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedAlbumSimplifiedPagingCall.execute()).thenReturn(Response.success(createAlbumSimplifiedPaging()));

        sut.getNewReleases(null);

        verify(mockedBrowseService).getNewReleases(fakeAccessTokenWithBearer, fakeOptionalParameters);
    }
    
    @Test
    void getNewReleasesPagingUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedAlbumSimplifiedPagingCall.execute()).thenReturn(Response.success(createAlbumSimplifiedPaging()));

        sut.getNewReleasesPaging(null);

        verify(mockedBrowseService).getNewReleases(fakeAccessTokenWithBearer, fakeOptionalParameters);
    }

    @Test @Deprecated(since = "1.5.8")
    void getNewReleasesExecutesHttpCall() throws IOException {
        when(mockedAlbumSimplifiedPagingCall.execute()).thenReturn(Response.success(createAlbumSimplifiedPaging()));

        sut.getNewReleases(fakeOptionalParameters);
        verify(mockedAlbumSimplifiedPagingCall).execute();
    }
    
    @Test
    void getNewReleasesPagingExecutesHttpCall() throws IOException {
        when(mockedAlbumSimplifiedPagingCall.execute()).thenReturn(Response.success(createAlbumSimplifiedPaging()));

        sut.getNewReleasesPaging(fakeOptionalParameters);
        verify(mockedAlbumSimplifiedPagingCall).execute();
    }

    @Test @Deprecated(since = "1.5.8") @SuppressWarnings("deprecation")
    void getNewReleasesThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedAlbumSimplifiedPagingCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.getNewReleases(fakeOptionalParameters));
    }
    
    @Test
    void getNewReleasesPagingThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedAlbumSimplifiedPagingCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.getNewReleasesPaging(fakeOptionalParameters));
    }

    @Test @Deprecated(since = "1.5.8") @SuppressWarnings("deprecation")
    void getNewReleasesThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedAlbumSimplifiedPagingCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.getNewReleases(fakeOptionalParameters));
    }
    
    @Test
    void getNewReleasesPagingThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedAlbumSimplifiedPagingCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.getNewReleasesPaging(fakeOptionalParameters));
    }

    @Test@Deprecated(since = "1.5.8")
    void getNewReleasesReturnsAlbumSimplifiedPagingWhenSuccessful() throws IOException {
        when(mockedAlbumSimplifiedPagingCall.execute()).thenReturn(Response.success(createAlbumSimplifiedPaging()));

        Assertions.assertNotNull(sut.getNewReleases(fakeOptionalParameters));
    }
    
    @Test
    void getNewReleasesPagingReturnsAlbumSimplifiedPagingWhenSuccessful() throws IOException {
        when(mockedAlbumSimplifiedPagingCall.execute()).thenReturn(Response.success(createAlbumSimplifiedPaging()));

        Assertions.assertNotNull(sut.getNewReleasesPaging(fakeOptionalParameters));
    }

    @Test
    void getRecommendationsUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedBrowseService.getRecommendations(fakeAccessTokenWithBearer, fakeOptionalParametersWithSeeds)).thenReturn(mockedRecommendationCollectionCall);
        when(mockedRecommendationCollectionCall.execute()).thenReturn(Response.success(new RecommendationCollection()));

        sut.getRecommendations(listOfFakeSeedArtists, listOfFakeSeedGenres, listOfFakeSeedTracks, null);

        verify(mockedBrowseService).getRecommendations(fakeAccessTokenWithBearer, fakeOptionalParametersWithSeeds);
    }

    @Test
    void getRecommendationsExecutesHttpCall() throws IOException {
        when(mockedRecommendationCollectionCall.execute()).thenReturn(Response.success(new RecommendationCollection()));

        sut.getRecommendations(
                listOfFakeSeedArtists,
                listOfFakeSeedGenres,
                listOfFakeSeedTracks,
                mockedOptionalParametersWithSeeds);

        verify(mockedRecommendationCollectionCall).execute();
    }

    @Test
    void getRecommendationsThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedRecommendationCollectionCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class,
                () -> sut.getRecommendations(
                        listOfFakeSeedArtists,
                        listOfFakeSeedGenres,
                        listOfFakeSeedTracks,
                        mockedOptionalParametersWithSeeds
                ));
    }

    @Test
    void getRecommendationsThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedRecommendationCollectionCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class,
                () -> sut.getRecommendations(
                        listOfFakeSeedArtists,
                        listOfFakeSeedGenres,
                        listOfFakeSeedTracks,
                        mockedOptionalParametersWithSeeds
                ));
    }

    @Test
    void getRecommendationsReturnsRecommendationCollectionWhenSuccessful() throws IOException {
        when(mockedRecommendationCollectionCall.execute()).thenReturn(Response.success(new RecommendationCollection()));

        Assertions.assertNotNull(sut.getRecommendations(listOfFakeSeedArtists, listOfFakeSeedGenres, listOfFakeSeedTracks, mockedOptionalParametersWithSeeds));
    }

    @Test
    void getRecommendationsMapsListOfSeedsCorrectly() throws IOException {
        when(mockedRecommendationCollectionCall.execute()).thenReturn(Response.success(new RecommendationCollection()));

        sut.getRecommendations(listOfFakeSeedArtists, listOfFakeSeedGenres, listOfFakeSeedTracks, mockedOptionalParametersWithSeeds);

        verify(mockedOptionalParametersWithSeeds).put("seed_artists", fakeSeedArtists);
        verify(mockedOptionalParametersWithSeeds).put("seed_genres", fakeSeedGenres);
        verify(mockedOptionalParametersWithSeeds).put("seed_tracks", fakeSeedTracks);
    }
    
    private AlbumSimplifiedPaging createAlbumSimplifiedPaging() {
        AlbumSimplifiedPaging albumSimplifiedPaging = new AlbumSimplifiedPaging();
        albumSimplifiedPaging.setAlbums(new Paging<>());
        return albumSimplifiedPaging;
    }

    private PlaylistSimplifiedPaging createPlaylistSimplifiedPaging() {
        PlaylistSimplifiedPaging playlistSimplifiedPaging = new PlaylistSimplifiedPaging();
        playlistSimplifiedPaging.setPlaylists(new Paging<>());
        return playlistSimplifiedPaging;
    }
    
    private CategoryFullPaging createCategoryFullPaging() {
        CategoryFullPaging categoryFullPaging = new CategoryFullPaging();
        categoryFullPaging.setCategories(new Paging<>());
        return categoryFullPaging;
    }

}
