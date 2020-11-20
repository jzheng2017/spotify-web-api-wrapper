package spotify.retrofit.services;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import spotify.models.users.User;

public interface UserService {
    @GET("me")
    Call<User> getCurrentUser(@Header("Authorization") String accessToken);

    @GET("users/{user_id}")
    Call<User> getUserById(@Header("Authorization") String accessToken, @Path("user_id") String userId);
}
