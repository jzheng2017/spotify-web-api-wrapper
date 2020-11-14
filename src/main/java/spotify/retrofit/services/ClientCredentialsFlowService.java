package spotify.retrofit.services;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import spotify.api.enums.GrantType;
import spotify.models.authorization.ClientCredentialsFlowTokenResponse;

public interface ClientCredentialsFlowService {

    @POST("api/token")
    @FormUrlEncoded
    Call<ClientCredentialsFlowTokenResponse> getToken(@Header("Authorization") String basicAuth, @Field("grant_type") GrantType grantType);
}
