package spotify.retrofit.services;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;
import spotify.models.audio.AudioAnalysis;
import spotify.models.audio.AudioFeatures;
import spotify.models.audio.AudioFeaturesList;
import spotify.models.tracks.TrackFull;
import spotify.models.tracks.TrackFullList;

public interface TrackService {

    @GET("tracks/{id}")
    Call<TrackFull> getTrack(@Header("Authorization") String accessToken, @Path("id") String trackId, @Query("market") String market);

    @GET("tracks")
    Call<TrackFullList> getTracks(@Header("Authorization") String accessToken, @Query("ids") String trackIds, @Query("market") String market);

    @GET("audio-features/{id}")
    Call<AudioFeatures> getTrackAudioFeatures(@Header("Authorization") String accessToken, @Path("id") String trackId);

    @GET("audio-features")
    Call<AudioFeaturesList> getTracksAudioFeatures(@Header("Authorization") String accessToken, @Query("ids") String trackIds);

    @GET("audio-analysis/{id}")
    Call<AudioAnalysis> getTrackAudioAnalysis(@Header("Authorization") String accessToken, @Path("id") String trackId);

}
