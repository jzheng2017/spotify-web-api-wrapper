package spotify.factories;

import com.google.gson.*;
import spotify.models.generic.AbstractPlayableObject;
import spotify.models.generic.EpisodePlayableObjectFactory;
import spotify.models.generic.PlayableObjectFactory;
import spotify.models.generic.TrackPlayableObjectFactory;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;


class AbstractPlayableObjectDeserializer implements JsonDeserializer<AbstractPlayableObject> {

    // Factory map
    private static final Map<String, PlayableObjectFactory> FACTORY_MAP = new HashMap<>();

    static {
        FACTORY_MAP.put("track", new TrackPlayableObjectFactory());
        FACTORY_MAP.put("episode", new EpisodePlayableObjectFactory());
    }

    @Override
    public AbstractPlayableObject deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonElement jsonElement = jsonObject.get("type");
        String objectType = jsonElement.getAsString();

        // Use the factory map to deserialize the correct type
        PlayableObjectFactory factory = FACTORY_MAP.get(objectType);
        
        if (factory != null) {
            return factory.deserialize(json, jsonDeserializationContext);
        } else {
            throw new JsonParseException("Unknown object type: " + objectType);
        }
    }
}
