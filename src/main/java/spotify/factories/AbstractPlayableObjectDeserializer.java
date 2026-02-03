package spotify.factories;

import com.google.gson.*;
import spotify.models.episodes.EpisodeFull;
import spotify.models.generic.AbstractPlayableObject;
import spotify.models.tracks.TrackFull;

import java.lang.reflect.Type;

/**
 * Custom JSON deserializer for handling polymorphic deserialization of playable objects.
 * Determines whether to deserialize the JSON as a {@link TrackFull} or {@link EpisodeFull}
 * based on the "type" field in the JSON response.
 */
class AbstractPlayableObjectDeserializer implements JsonDeserializer<AbstractPlayableObject> {

    /**
     * Deserializes a JSON element into the appropriate {@link AbstractPlayableObject} subclass.
     * Inspects the "type" field to determine whether the object is a track or episode.
     *
     * @param json the JSON element to deserialize
     * @param type the type of the object to deserialize to
     * @param jsonDeserializationContext the context for deserialization
     * @return a {@link TrackFull} if type is "track", {@link EpisodeFull} if type is "episode", or null if neither
     * @throws JsonParseException if the JSON is not in the expected format
     */
    @Override
    public AbstractPlayableObject deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        JsonElement jsonElement = jsonObject.get("type");

        String objectType = jsonElement.getAsString();

        AbstractPlayableObject childClass = null;

        if (objectType.equals("track")) {
            childClass = jsonDeserializationContext.deserialize(json, TrackFull.class);
        } else if (objectType.equals("episode")) {
            childClass = jsonDeserializationContext.deserialize(json, EpisodeFull.class);
        }

        return childClass;
    }
}