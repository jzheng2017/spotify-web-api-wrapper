package spotify.models.generic;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;

import spotify.models.episodes.EpisodeFull;

public class EpisodePlayableObjectFactory implements PlayableObjectFactory {
    @Override
    public AbstractPlayableObject deserialize(JsonElement json, JsonDeserializationContext context) {
        return context.deserialize(json, EpisodeFull.class);
    }
}