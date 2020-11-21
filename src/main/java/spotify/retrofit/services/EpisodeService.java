package spotify.retrofit.services;

import retrofit2.Call;
import retrofit2.http.*;
import spotify.models.episodes.EpisodeFull;
import spotify.models.episodes.EpisodeFullCollection;

import java.util.Map;

public interface EpisodeService {
    @GET("episodes/{id}")
    Call<EpisodeFull> getEpisode(@Header("Authorization") String accessToken, @Path("id") String episodeId, @QueryMap Map<String, String> options);

    @GET("episodes")
    Call<EpisodeFullCollection> getEpisodes(@Header("Authorization") String accessToken, @Query("ids") String episodeIds, @QueryMap Map<String, String> options);
}
