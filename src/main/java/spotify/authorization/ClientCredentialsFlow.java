package spotify.authorization;

import okhttp3.Credentials;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import spotify.config.ApiUrl;
import spotify.exceptions.HttpRequestFailedException;
import spotify.exceptions.SpotifyAuthorizationFailedException;
import spotify.models.ClientCredentialsFlowTokenResponse;
import spotify.retrofit.services.ClientCredentialsFlowService;

import java.io.IOException;

public class ClientCredentialsFlow {
    private final Retrofit httpClient;
    private ClientCredentialsFlowTokenResponse clientCredentialsFlowTokenResponse;

    public ClientCredentialsFlow() {
        httpClient = new Retrofit.Builder()
                .baseUrl(ApiUrl.ACCOUNTS_URL_HTTPS)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void doClientCredentialsFlow(String clientId, String clientSecret, String grantType) {
        ClientCredentialsFlowService clientCredentialsFlowService = httpClient.create(ClientCredentialsFlowService.class);
        final String base64EncodedBasicAuth = Credentials.basic(clientId, clientSecret);

        Call<ClientCredentialsFlowTokenResponse> call = clientCredentialsFlowService.getToken(base64EncodedBasicAuth, grantType);

        try {
            Response<ClientCredentialsFlowTokenResponse> response = call.execute();

            if (response.body() == null) {

                throw new SpotifyAuthorizationFailedException("Authorizing with the given credentials has failed!");
            }

            this.clientCredentialsFlowTokenResponse = response.body();
        } catch (IOException e) {
            throw new HttpRequestFailedException(e.getMessage());
        }
    }

    public ClientCredentialsFlowTokenResponse getClientCredentialsFlowToken() {
        return clientCredentialsFlowTokenResponse;
    }
}
