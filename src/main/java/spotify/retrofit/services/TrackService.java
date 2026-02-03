package spotify.retrofit.services;

import retrofit2.Call;
import retrofit2.http.*;
import spotify.models.audio.AudioAnalysis;
import spotify.models.audio.AudioFeatures;
import spotify.models.audio.AudioFeaturesCollection;
import spotify.models.tracks.TrackFull;
import spotify.models.tracks.TrackFullCollection;

import java.util.Map;

/**
 * Retrofit service interface for Spotify Track API endpoints.
 * Provides methods to retrieve track information, audio features, and audio analysis.
 */
public interface TrackService {

    /**
     * Retrieves detailed information about a specific track.
     *
     * @param accessToken the OAuth access token for authorization
     * @param trackId the Spotify ID of the track
     * @param options optional query parameters such as market
     * @return a Call object containing the full track details
     */
    @GET("tracks/{id}")
    Call<TrackFull> getTrack(@Header("Authorization") String accessToken, @Path("id") String trackId, @QueryMap Map<String, String> options);

    /**
     * Retrieves detailed information about multiple tracks.
     *
     * @param accessToken the OAuth access token for authorization
     * @param trackIds a comma-separated list of Spotify track IDs
     * @param options optional query parameters such as market
     * @return a Call object containing a collection of full track details
     */
    @GET("tracks")
    Call<TrackFullCollection> getTracks(@Header("Authorization") String accessToken, @Query("ids") String trackIds, @QueryMap Map<String, String> options);

    /**
     * Retrieves audio features for a specific track.
     * Audio features include tempo, key, time signature, and other acoustic attributes.
     *
     * @param accessToken the OAuth access token for authorization
     * @param trackId the Spotify ID of the track
     * @return a Call object containing the track's audio features
     */
    @GET("audio-features/{id}")
    Call<AudioFeatures> getTrackAudioFeatures(@Header("Authorization") String accessToken, @Path("id") String trackId);

    /**
     * Retrieves audio features for multiple tracks.
     *
     * @param accessToken the OAuth access token for authorization
     * @param trackIds a comma-separated list of Spotify track IDs
     * @return a Call object containing a collection of audio features for the tracks
     */
    @GET("audio-features")
    Call<AudioFeaturesCollection> getTracksAudioFeatures(@Header("Authorization") String accessToken, @Query("ids") String trackIds);

    /**
     * Retrieves a detailed audio analysis for a specific track.
     * Audio analysis provides low-level audio attributes including bars, beats, sections, and segments.
     *
     * @param accessToken the OAuth access token for authorization
     * @param trackId the Spotify ID of the track
     * @return a Call object containing the track's audio analysis
     */
    @GET("audio-analysis/{id}")
    Call<AudioAnalysis> getTrackAudioAnalysis(@Header("Authorization") String accessToken, @Path("id") String trackId);

}