package spotify.retrofit.services;

import retrofit2.Call;
import retrofit2.http.*;
import spotify.models.ClientCredentialsFlowTokenResponse;

public interface ClientCredentialsFlowService {

    @POST("api/token")
    @FormUrlEncoded
    Call<ClientCredentialsFlowTokenResponse> getToken(@Header("Authorization") String basicAuth, @Field("grant_type") String grantType);

}
