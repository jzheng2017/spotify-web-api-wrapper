package spotify.factories;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spotify.models.generic.AbstractPlayableObject;

/**
 * Factory class for creating configured {@link Gson} instances.
 * Provides methods to create Gson objects with specific field naming policies
 * and custom type adapters for deserializing Spotify API responses.
 */
class GsonFactory {
    private final static Logger logger = LoggerFactory.getLogger(GsonFactory.class);

    /**
     * Creates a Gson instance configured with lowercase underscore field naming policy.
     * This matches the JSON field naming convention used by the Spotify API.
     *
     * @return a configured Gson instance
     */
    static Gson getGsonLowerCaseUnderScorePolicy() {
        return getGson(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
    }

    /**
     * Creates a Gson instance configured with lowercase underscore field naming policy
     * and a custom deserializer for {@link AbstractPlayableObject}.
     * Used for endpoints that return polymorphic playable objects (tracks or episodes).
     *
     * @return a configured Gson instance with the custom type adapter
     */
    static Gson getGsonLowerCaseUnderScorePolicyWithAbstractPlayableObjectDeserializer() {
        return getGsonWithCustomTypeAdapter(
                FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES,
                AbstractPlayableObject.class,
                new AbstractPlayableObjectDeserializer());
    }

    /**
     * Creates a Gson instance with the specified field naming policy.
     *
     * @param fieldNamingPolicy the field naming policy to apply
     * @return a configured Gson instance
     */
    private static Gson getGson(final FieldNamingPolicy fieldNamingPolicy) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(fieldNamingPolicy);

        logger.trace("Constructing Gson object with the FieldNamingPolicy {}", fieldNamingPolicy);
        return gsonBuilder.create();
    }

    /**
     * Creates a Gson instance with the specified field naming policy and a custom type adapter.
     *
     * @param fieldNamingPolicy the field naming policy to apply
     * @param baseClass the base class for which the custom deserializer is registered
     * @param jsonDeserializer the custom deserializer to handle the base class
     * @param <T> the type of the base class
     * @return a configured Gson instance with the custom type adapter
     */
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