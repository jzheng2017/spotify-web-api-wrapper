package spotify.retrofit.services;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import spotify.models.artists.ArtistFull;

public interface ArtistService {
    @GET("artists/{id}")
    Call<ArtistFull> getArtist(@Header("Authorization") String accessToken, @Path("id") String artistId);
}
