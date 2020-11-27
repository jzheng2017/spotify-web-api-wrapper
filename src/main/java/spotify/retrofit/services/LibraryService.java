package spotify.retrofit.services;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import spotify.models.albums.SavedAlbumFull;
import spotify.models.paging.Paging;
import spotify.models.shows.SavedShowSimplified;
import spotify.models.tracks.SavedTrackFull;

import java.util.List;
import java.util.Map;

public interface LibraryService {
    @GET("me/albums/contains")
    Call<List<Boolean>> hasSavedAlbums(@Header("Authorization") String accessToken, @Query("ids") String albumIds);

    @GET("me/shows/contains")
    Call<List<Boolean>> hasSavedShows(@Header("Authorization") String accessToken, @Query("ids") String showIds);

    @GET("me/tracks/contains")
    Call<List<Boolean>> hasSavedTracks(@Header("Authorization") String accessToken, @Query("ids") String trackIds);

    @GET("me/albums")
    Call<Paging<SavedAlbumFull>> getSavedAlbums(@Header("Authorization") String accessToken, @QueryMap Map<String, String> options);

    @GET("me/shows")
    Call<Paging<SavedShowSimplified>> getSavedShows(@Header("Authorization") String accessToken, @QueryMap Map<String, String> options);

    @GET("me/tracks")
    Call<Paging<SavedTrackFull>> getSavedTracks(@Header("Authorization") String accessToken, @QueryMap Map<String, String> options);
}
