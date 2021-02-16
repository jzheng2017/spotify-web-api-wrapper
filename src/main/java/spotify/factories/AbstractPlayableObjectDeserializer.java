package spotify.factories;

import com.google.gson.*;
import spotify.models.episodes.EpisodeFull;
import spotify.models.generic.AbstractPlayableObject;
import spotify.models.tracks.TrackFull;

import java.lang.reflect.Type;

/**
 * This class is a custom {@link JsonDeserializer} which deserializes json and returns a {@link AbstractPlayableObject} subclass based on the type property in the json
 * @see spotify.models.generic.AbstractPlayableObject
 * @author Jiankai Zheng (jk.zheng@hotmail.com)
 * @since // check with Jiankai
 */

class AbstractPlayableObjectDeserializer implements JsonDeserializer<AbstractPlayableObject> {

    /**
     * This function takes a {@link JsonElement}  and returns an {@link AbstractPlayableObject}  based off the type specified
     * @param json to be deserialized
     * @param type the type to convert the data into
     * @param context for deserialization that is passed to a custom deserializer during invocation of its JsonDeserializer.deserialize method
     * @return an object which is a subclass of AbstractPlayableObject
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
