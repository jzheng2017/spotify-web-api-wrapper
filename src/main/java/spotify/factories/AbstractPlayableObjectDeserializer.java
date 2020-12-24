package spotify.factories;

import com.google.gson.*;
import spotify.models.episodes.EpisodeFull;
import spotify.models.generic.AbstractPlayableObject;
import spotify.models.tracks.TrackFull;

import java.lang.reflect.Type;

/**
 * This is a AbstractPlayableObjectDeserializer class to implement JsonDeserializer.
 *
 * @see spotify.models.generic.AbstractPlayableObject
 * @author Jiankai Zheng (jk.zheng@hotmail.com)
 * @since // check with Jiankai
 */

class AbstractPlayableObjectDeserializer implements JsonDeserializer<AbstractPlayableObject> {

    /**
     * Custom deserialize method to deserialize json, type and jsonDeserializationContext
     * @param json represents a json element
     * @param type represents various types
     * @param jsonDeserializationContext
     * @return childClass
     * @throws JsonParseException (RuntimeException) if the incoming Json is bad/malicious/ serious issues that occurs during parsing of a Json string.
     */

    @Override
    public AbstractPlayableObject deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        /**
         * jsonObject type that gets
         * See the {@link https://www.javadoc.io/doc/com.google.code.gson/gson/2.8.5/com/google/gson/JsonObject.html}
         * getAsJsonObject method to get the specified member as JsonObject
         * See the {@link https://www.javadoc.io/doc/com.google.code.gson/gson/2.8.5/com/google/gson/JsonObject.html}
         */
        JsonObject jsonObject = json.getAsJsonObject();

        /**
         * The jsonElement that gets type
         * See the {@link https://www.javadoc.io/doc/com.google.code.gson/gson/2.8.5/com/google/gson/JsonElement.html}
         */

        JsonElement jsonElement = jsonObject.get("type");

        /**
         * The objectType which gets jsonElement as a string value
         */

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
