package spotify.retrofit.services;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;
import spotify.models.albums.AlbumFull;
import spotify.models.albums.AlbumFullList;

public interface AlbumService {

    @GET("albums/{id}")
    Call<AlbumFull> getAlbum(@Header("Authorization") String accessToken, @Path("id") String albumId);

    @GET("albums")
    Call<AlbumFullList> getAlbums(@Header("Authorization") String accessToken, @Query("ids") String albumIds, @Query("market") String market);
}
