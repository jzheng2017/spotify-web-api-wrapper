package spotify.retrofit.services;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import spotify.models.albums.AlbumFull;

public interface AlbumService {

    @GET("albums/{id}")
    Call<AlbumFull> getAlbum(@Header("Authorization") String accessToken, @Path("id") String albumId);
}
