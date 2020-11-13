package spotify.models.audio;

import java.util.List;

public class AudioAnalysis {
    private List<TimeInterval> bars;
    private List<TimeInterval> beats;
    private List<Section> sections;
    private List<Segment> segments;
    private List<TimeInterval> tatums;

    public List<TimeInterval> getBars() {
        return bars;
    }

    public void setBars(List<TimeInterval> bars) {
        this.bars = bars;
    }

    public List<TimeInterval> getBeats() {
        return beats;
    }

    public void setBeats(List<TimeInterval> beats) {
        this.beats = beats;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public List<Segment> getSegments() {
        return segments;
    }

    public void setSegments(List<Segment> segments) {
        this.segments = segments;
    }

    public List<TimeInterval> getTatums() {
        return tatums;
    }

    public void setTatums(List<TimeInterval> tatums) {
        this.tatums = tatums;
    }
}
