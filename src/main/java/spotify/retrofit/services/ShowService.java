package spotify.retrofit.services;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;
import spotify.models.episodes.EpisodeSimplified;
import spotify.models.paging.Paging;
import spotify.models.shows.ShowFull;

public interface ShowService {
    @GET("shows/{id}")
    Call<ShowFull> getShow(@Header("Authorization") String accessToken, @Path("id") String showId, @Query("market") String market);

    @GET("shows/{id}/episodes")
    Call<Paging<EpisodeSimplified>> getShowEpisodes(@Header("Authorization") String accessToken,
                                                    @Path("id") String showId,
                                                    @Query("limit") int limit,
                                                    @Query("offset") int offset,
                                                    @Query("market") String market);
}
