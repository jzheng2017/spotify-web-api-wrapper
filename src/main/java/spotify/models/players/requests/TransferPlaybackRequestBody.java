package spotify.models.players.requests;

import java.util.List;

public class TransferPlaybackRequestBody {
    private List<String> deviceIds;
    private boolean play;

    public TransferPlaybackRequestBody() {
    }

    public TransferPlaybackRequestBody(List<String> deviceIds, boolean play) {
        this.deviceIds = deviceIds;
        this.play = play;
    }

    public List<String> getDeviceIds() {
        return deviceIds;
    }

    public void setDeviceIds(List<String> deviceIds) {
        this.deviceIds = deviceIds;
    }

    public boolean isPlay() {
        return play;
    }

    public void setPlay(boolean play) {
        this.play = play;
    }
}
