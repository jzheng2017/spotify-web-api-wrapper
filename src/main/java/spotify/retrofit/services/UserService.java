package spotify.retrofit.services;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import spotify.models.users.User;

/**
 * Retrofit service interface for Spotify User API endpoints.
 * Provides methods to retrieve user profile information.
 */
public interface UserService {

    /**
     * Retrieves the profile of the current authenticated user.
     *
     * @param accessToken the OAuth access token for authorization
     * @return a Call object containing the current user's profile
     */
    @GET("me")
    Call<User> getCurrentUser(@Header("Authorization") String accessToken);

    /**
     * Retrieves the public profile of a specific user.
     *
     * @param accessToken the OAuth access token for authorization
     * @param userId the Spotify user ID
     * @return a Call object containing the user's public profile
     */
    @GET("users/{user_id}")
    Call<User> getUser(@Header("Authorization") String accessToken, @Path("user_id") String userId);
}