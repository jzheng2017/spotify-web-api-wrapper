package spotify.factories;

import com.google.gson.*;
import spotify.models.episodes.EpisodeFull;
import spotify.models.generic.AbstractPlayableObject;
import spotify.models.tracks.TrackFull;

import java.lang.reflect.Type;

class AbstractPlayableObjectDeserializer implements JsonDeserializer<AbstractPlayableObject> {
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
