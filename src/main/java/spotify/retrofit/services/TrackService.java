package spotify.retrofit.services;

import retrofit2.Call;
import retrofit2.http.*;
import spotify.models.audio.AudioAnalysis;
import spotify.models.audio.AudioFeatures;
import spotify.models.audio.AudioFeaturesCollection;
import spotify.models.tracks.TrackFull;
import spotify.models.tracks.TrackFullCollection;

import java.util.Map;

public interface TrackService {
    @GET("tracks/{id}")
    Call<TrackFull> getTrack(@Header("Authorization") String accessToken, @Path("id") String trackId, @QueryMap Map<String, String> options);

    @GET("tracks")
    Call<TrackFullCollection> getTracks(@Header("Authorization") String accessToken, @Query("ids") String trackIds, @QueryMap Map<String, String> options);

    @GET("audio-features/{id}")
    Call<AudioFeatures> getTrackAudioFeatures(@Header("Authorization") String accessToken, @Path("id") String trackId);

    @GET("audio-features")
    Call<AudioFeaturesCollection> getTracksAudioFeatures(@Header("Authorization") String accessToken, @Query("ids") String trackIds);

    @GET("audio-analysis/{id}")
    Call<AudioAnalysis> getTrackAudioAnalysis(@Header("Authorization") String accessToken, @Path("id") String trackId);

}
