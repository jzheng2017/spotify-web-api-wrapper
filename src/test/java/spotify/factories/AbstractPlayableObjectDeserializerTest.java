package spotify.factories;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import spotify.models.episodes.EpisodeFull;
import spotify.models.generic.AbstractPlayableObject;
import spotify.models.tracks.TrackFull;

import java.lang.reflect.Type;

import static org.mockito.Mockito.when;

public class AbstractPlayableObjectDeserializerTest {
    private AbstractPlayableObjectDeserializer sut;
    private JsonObject mockedJsonObject;

    @Mock
    private JsonElement mockedJsonElement;
    @Mock
    private JsonDeserializationContext mockedJsonDeserializationContext;
    @Mock
    private Type mockedType;


    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        sut = new AbstractPlayableObjectDeserializer();
        mockedJsonObject = new JsonObject();

        when(mockedJsonElement.getAsJsonObject()).thenReturn(mockedJsonObject);
        when(mockedJsonDeserializationContext.deserialize(mockedJsonElement, TrackFull.class)).thenReturn(new TrackFull());
        when(mockedJsonDeserializationContext.deserialize(mockedJsonElement, EpisodeFull.class)).thenReturn(new EpisodeFull());
    }

    @Test
    void deserializerMapsToTrackFullObjectWhenTypeIsTrack() {
        mockedJsonObject.addProperty("type", "track");

        AbstractPlayableObject object = sut.deserialize(mockedJsonElement, mockedType, mockedJsonDeserializationContext);

        Assertions.assertTrue(object instanceof TrackFull);
    }

    @Test
    void deserializerMapsToEpisodeFullObjectWhenTypeIsEpisode() {
        mockedJsonObject.addProperty("type", "episode");

        AbstractPlayableObject object = sut.deserialize(mockedJsonElement, mockedType, mockedJsonDeserializationContext);

        Assertions.assertTrue(object instanceof EpisodeFull);
    }
}
