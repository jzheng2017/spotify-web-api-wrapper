package spotify.retrofit.services;

import retrofit2.Call;
import retrofit2.http.*;
import spotify.models.albums.AlbumFull;
import spotify.models.albums.AlbumFullCollection;
import spotify.models.paging.Paging;
import spotify.models.tracks.TrackSimplified;

import java.util.Map;

public interface AlbumService {
    @GET("albums/{id}")
    Call<AlbumFull> getAlbum(@Header("Authorization") String accessToken, @Path("id") String albumId, @QueryMap Map<String, String> options);

    @GET("albums")
    Call<AlbumFullCollection> getAlbums(@Header("Authorization") String accessToken, @Query("ids") String albumIds, @QueryMap Map<String, String> options);

    @GET("albums/{id}/tracks")
    Call<Paging<TrackSimplified>> getAlbumTracks(@Header("Authorization") String accessToken,
                                                 @Path("id") String albumId,
                                                 @QueryMap Map<String, String> options);
}
