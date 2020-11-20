package spotify.retrofit.services;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import spotify.models.episodes.EpisodeFull;

public interface EpisodeService {
    @GET("episodes/{id}")
    Call<EpisodeFull> getEpisode(@Header("Authorization") String accessToken, @Path("id") String episodeId);
}
