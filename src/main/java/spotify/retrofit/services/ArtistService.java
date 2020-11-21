package spotify.retrofit.services;

import retrofit2.Call;
import retrofit2.http.*;
import spotify.models.artists.ArtistFull;
import spotify.models.artists.ArtistFullCollection;
import spotify.models.artists.ArtistSimplified;
import spotify.models.paging.Paging;
import spotify.models.tracks.TrackFullCollection;

import java.util.Map;

public interface ArtistService {
    @GET("artists/{id}")
    Call<ArtistFull> getArtist(@Header("Authorization") String accessToken, @Path("id") String artistId);

    @GET("artists/{id}/albums")
    Call<Paging<ArtistSimplified>> getArtistAlbums(@Header("Authorization") String accessToken,
                                                   @Path("id") String artistId,
                                                   @QueryMap Map<String, String> options);

    @GET("artists/{id}/top-tracks")
    Call<TrackFullCollection> getArtistTopTracks(@Header("Authorization") String accessToken, @Path("id") String artistId, @QueryMap Map<String, String> options);

    @GET("artists/{id}/related-artists")
    Call<ArtistFullCollection> getRelatedArtists(@Header("Authorization") String accessToken, @Path("id") String artistId);

    @GET("artists")
    Call<ArtistFullCollection> getArtists(@Header("Authorization") String accessToken, @Query("ids") String artistIds);

}
