package spotify.retrofit.services;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;
import spotify.models.episodes.EpisodeFull;
import spotify.models.episodes.EpisodeFullCollection;

public interface EpisodeService {
    @GET("episodes/{id}")
    Call<EpisodeFull> getEpisode(@Header("Authorization") String accessToken, @Path("id") String episodeId, @Query("market") String market);

    @GET("episodes")
    Call<EpisodeFullCollection> getEpisodes(@Header("Authorization") String accessToken, @Query("ids") String episodeIds, @Query("market") String market);
}
