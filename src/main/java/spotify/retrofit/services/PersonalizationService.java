package spotify.retrofit.services;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.QueryMap;
import spotify.models.artists.ArtistFull;
import spotify.models.paging.Paging;
import spotify.models.tracks.TrackFull;

import java.util.Map;

public interface PersonalizationService {
    @GET("me/top/artists")
    Call<Paging<ArtistFull>> getTopArtists(@Header("Authorization") String accessToken, @QueryMap Map<String, String> options);

    @GET("me/top/tracks")
    Call<Paging<TrackFull>> getTopTracks(@Header("Authorization") String accessToken, @QueryMap Map<String, String> options);
}
