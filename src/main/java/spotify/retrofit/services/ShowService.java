package spotify.retrofit.services;

import retrofit2.Call;
import retrofit2.http.*;
import spotify.models.episodes.EpisodeSimplified;
import spotify.models.paging.Paging;
import spotify.models.shows.ShowFull;
import spotify.models.shows.ShowSimplifiedCollection;

import java.util.Map;

public interface ShowService {
    @GET("shows/{id}")
    Call<ShowFull> getShow(@Header("Authorization") String accessToken, @Path("id") String showId, @QueryMap Map<String, String> options);

    @GET("shows/{id}/episodes")
    Call<Paging<EpisodeSimplified>> getShowEpisodes(@Header("Authorization") String accessToken,
                                                    @Path("id") String showId,
                                                    @QueryMap Map<String, String> options);

    @GET("shows")
    Call<ShowSimplifiedCollection> getShows(@Header("Authorization") String accessToken, @Query("ids") String showIds, @QueryMap Map<String, String> options);
}
