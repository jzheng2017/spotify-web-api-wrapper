package spotify.models.audio;

public class Section {
    private float start;
    private float duration;
    private float confidence;
    private float loudness;
    private float tempo;
    private float tempoConfidence;
    private int key;
    private float keyConfidence;
    private int mode;
    private float modeConfidence;
    private int timeSignature;
    private float timeSignatureConfidence;

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

    public float getLoudness() {
        return loudness;
    }

    public void setLoudness(float loudness) {
        this.loudness = loudness;
    }

    public float getTempo() {
        return tempo;
    }

    public void setTempo(float tempo) {
        this.tempo = tempo;
    }

    public float getTempoConfidence() {
        return tempoConfidence;
    }

    public void setTempoConfidence(float tempoConfidence) {
        this.tempoConfidence = tempoConfidence;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public float getKeyConfidence() {
        return keyConfidence;
    }

    public void setKeyConfidence(float keyConfidence) {
        this.keyConfidence = keyConfidence;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public float getModeConfidence() {
        return modeConfidence;
    }

    public void setModeConfidence(float modeConfidence) {
        this.modeConfidence = modeConfidence;
    }

    public int getTimeSignature() {
        return timeSignature;
    }

    public void setTimeSignature(int timeSignature) {
        this.timeSignature = timeSignature;
    }

    public float getTimeSignatureConfidence() {
        return timeSignatureConfidence;
    }

    public void setTimeSignatureConfidence(float timeSignatureConfidence) {
        this.timeSignatureConfidence = timeSignatureConfidence;
    }
}
