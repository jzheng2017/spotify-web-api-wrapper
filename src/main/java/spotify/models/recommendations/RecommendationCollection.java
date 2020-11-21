package spotify.models.recommendations;

import spotify.models.tracks.TrackSimplified;

import java.util.List;

public class RecommendationCollection {
    private List<RecommendationSeed> seeds;
    private List<TrackSimplified> tracks;

    public List<RecommendationSeed> getSeeds() {
        return seeds;
    }

    public void setSeeds(List<RecommendationSeed> seeds) {
        this.seeds = seeds;
    }

    public List<TrackSimplified> getTracks() {
        return tracks;
    }

    public void setTracks(List<TrackSimplified> tracks) {
        this.tracks = tracks;
    }
}
