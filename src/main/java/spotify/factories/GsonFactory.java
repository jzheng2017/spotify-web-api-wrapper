package spotify.factories;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spotify.models.generic.AbstractPlayableObject;

/**
 *
 */
class GsonFactory {
    private final static Logger logger = LoggerFactory.getLogger(GsonFactory.class);

    static Gson getGsonLowerCaseUnderScorePolicy() {
        return getGson(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
    }

    static Gson getGsonLowerCaseUnderScorePolicyWithAbstractPlayableObjectDeserializer() {
        return getGsonWithCustomTypeAdapter(
                FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES,
                AbstractPlayableObject.class,
                new AbstractPlayableObjectDeserializer());
    }

    private static Gson getGson(final FieldNamingPolicy fieldNamingPolicy) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(fieldNamingPolicy);

        logger.trace("Constructing Gson object with the FieldNamingPolicy {}", fieldNamingPolicy);
        return gsonBuilder.create();
    }

    private static <T> Gson getGsonWithCustomTypeAdapter(final FieldNamingPolicy fieldNamingPolicy,
                                                         final Class<T> baseClass,
                                                         final JsonDeserializer<T> jsonDeserializer) {
        final GsonBuilder gsonBuilder = new GsonBuilder();

        gsonBuilder.setFieldNamingPolicy(fieldNamingPolicy);
        gsonBuilder.registerTypeAdapter(baseClass, jsonDeserializer);

        logger.trace("Constructing Gson object with the FieldNamingPolicy {}", fieldNamingPolicy);
        logger.trace("Constructing Gson object with custom type adapter for base type {}", baseClass);
        return gsonBuilder.create();
    }

}
