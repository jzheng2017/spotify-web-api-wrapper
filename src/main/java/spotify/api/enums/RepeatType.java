package spotify.api.enums;

/**
 * This class contains and sets the repeat type. This is what determines whether a song or context will be
 * repeated.
 * <p>
 * For further information see: <a href="https://developer.spotify.com/documentation/web-api/reference/player/set-repeat-mode-on-users-playback/">
 * Set Repeat Mode On User's Playback</a>.
 *
 * @author Jiankai Zheng (jk.zheng@hotmail.com)
 * @since 1.0.0
 */

public enum RepeatType {
    TRACK("track"),
    CONTEXT("context"),
    OFF("off");

    private final String value;

    /**
     * Set the repeat type.
     * 
     * @param value The repeat type.
     */
    RepeatType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
