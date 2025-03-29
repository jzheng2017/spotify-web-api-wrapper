package spotify.models.generic;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;

public interface PlayableObjectFactory {

    AbstractPlayableObject deserialize(JsonElement json, JsonDeserializationContext context);
    
}
