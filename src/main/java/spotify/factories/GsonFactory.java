package spotify.factories;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class GsonFactory {
    private final static Logger logger = LoggerFactory.getLogger(GsonFactory.class);

    public static Gson getGson(FieldNamingPolicy fieldNamingPolicy) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(fieldNamingPolicy);

        logger.trace(String.format("Constructing Gson object with the FieldNamingPolicy %s", fieldNamingPolicy));
        return gsonBuilder.create();
    }

    public static Gson getGsonLowerCaseUnderScorePolicy() {
        return getGson(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
    }
}
