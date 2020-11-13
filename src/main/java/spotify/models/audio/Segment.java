package spotify.models.audio;

import java.util.List;

public class Segment {
    private float start;
    private float duration;
    private float confidence;
    private float loudnessStart;
    private float loudnessMaxTime;
    private float loudnessTime;
    private List<Float> pitches;
    private List<Float> timbre;

    public float getStart() {
        return start;
    }

    public void setStart(float start) {
        this.start = start;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public float getConfidence() {
        return confidence;
    }

    public void setConfidence(float confidence) {
        this.confidence = confidence;
    }

    public float getLoudnessStart() {
        return loudnessStart;
    }

    public void setLoudnessStart(float loudnessStart) {
        this.loudnessStart = loudnessStart;
    }

    public float getLoudnessMaxTime() {
        return loudnessMaxTime;
    }

    public void setLoudnessMaxTime(float loudnessMaxTime) {
        this.loudnessMaxTime = loudnessMaxTime;
    }

    public float getLoudnessTime() {
        return loudnessTime;
    }

    public void setLoudnessTime(float loudnessTime) {
        this.loudnessTime = loudnessTime;
    }

    public List<Float> getPitches() {
        return pitches;
    }

    public void setPitches(List<Float> pitches) {
        this.pitches = pitches;
    }

    public List<Float> getTimbre() {
        return timbre;
    }

    public void setTimbre(List<Float> timbre) {
        this.timbre = timbre;
    }
}
