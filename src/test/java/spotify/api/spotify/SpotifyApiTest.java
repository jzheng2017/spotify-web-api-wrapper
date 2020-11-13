package spotify.api.spotify;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import spotify.api.impl.TrackApiRetrofit;
import spotify.api.interfaces.AlbumApi;
import spotify.models.tracks.TrackFull;

import static org.mockito.Mockito.when;

public class SpotifyApiTest {
    @InjectMocks
    private SpotifyApi sut;
    @Mock
    private TrackApiRetrofit mockedTrackApi;
    @Mock
    private AlbumApi mockedAlbumApi;

    private final String fakeAccessToken = "fake";
    private String fakeMarket = "NL";
    private String fakeTrackId = "qweqwe1231";

    @BeforeEach
    void setup() {
        sut = new SpotifyApi(fakeAccessToken);

        MockitoAnnotations.openMocks(this);
        when(mockedTrackApi.getTrack(fakeTrackId, fakeMarket)).thenReturn(new TrackFull());

    }

    @Test
    void getTrackReturnsTrackFullObject() {
        Assertions.assertTrue(sut.getTrack(fakeTrackId, fakeMarket) instanceof TrackFull);
    }
}
