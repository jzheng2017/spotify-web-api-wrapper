package spotify.factories;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

class GsonFactory {

    public static Gson getGson(FieldNamingPolicy fieldNamingPolicy) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(fieldNamingPolicy);

        return gsonBuilder.create();
    }

    public static Gson getGsonLowerCaseUnderScorePolicy() {
        return getGson(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
    }
}
