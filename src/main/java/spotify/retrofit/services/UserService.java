package spotify.retrofit.services;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import spotify.models.users.User;

public interface UserService {

    @GET("me")
    Call<User> getCurrentUser(@Header("Authorization") String accessToken);
}
