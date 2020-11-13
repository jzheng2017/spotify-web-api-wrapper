package spotify.retrofit.services;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;
import spotify.models.TrackFull;

public interface TrackService {

    @GET("tracks/{id}")
    Call<TrackFull> getTrack(@Header("Authorization") String accessToken, @Path("id") String trackId, @Query("market") String market);
}
