package spotify.api.authorization;

import okhttp3.Request;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import retrofit2.Call;
import retrofit2.Response;
import spotify.api.enums.GrantType;
import spotify.exceptions.HttpRequestFailedException;
import spotify.exceptions.SpotifyAuthorizationFailedException;
import spotify.models.authorization.ClientCredentialsFlowTokenResponse;
import spotify.retrofit.services.ClientCredentialsFlowService;

import java.io.IOException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ClientCredentialsFlowTest extends AbstractAuthorizationTest {
    private final GrantType fakeGrantType = GrantType.CLIENT_CREDENTIALS;
    private ClientCredentialsFlow sut;
    @Mock
    private ClientCredentialsFlowService mockedClientCredentialsFlowService;
    @Mock
    private Call<ClientCredentialsFlowTokenResponse> mockedClientCredentialsFlowTokenResponseCall;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        sut = new ClientCredentialsFlow(mockedClientCredentialsFlowService);

        when(mockedClientCredentialsFlowService.getToken(fakeBasicAuth, fakeGrantType)).thenReturn(mockedClientCredentialsFlowTokenResponseCall);

        when(mockedClientCredentialsFlowTokenResponseCall.request()).thenReturn(new Request.Builder().url(fakeUrl).build());
    }

    @Test
    void getClientCredentialsTokenUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedClientCredentialsFlowTokenResponseCall.execute()).thenReturn(Response.success(new ClientCredentialsFlowTokenResponse()));

        sut.getClientCredentialToken(fakeClientId, fakeClientSecret);

        verify(mockedClientCredentialsFlowService).getToken(fakeBasicAuth, fakeGrantType);
    }

    @Test
    void getClientCredentialsTokenExecutesHttpCall() throws IOException {
        when(mockedClientCredentialsFlowTokenResponseCall.execute()).thenReturn(Response.success(new ClientCredentialsFlowTokenResponse()));

        sut.getClientCredentialToken(fakeClientId, fakeClientSecret);

        verify(mockedClientCredentialsFlowTokenResponseCall).execute();
    }

    @Test
    void getClientCredentialsTokenThrowsSpotifyAuthorizationExceptionWhenResponseBodyIsNull() throws IOException {
        when(mockedClientCredentialsFlowTokenResponseCall.execute()).thenReturn(Response.success(null));

        Assertions.assertThrows(SpotifyAuthorizationFailedException.class, () -> sut.getClientCredentialToken(fakeClientId, fakeClientSecret));
    }

    @Test
    void getClientCredentialsTokenThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedClientCredentialsFlowTokenResponseCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.getClientCredentialToken(fakeClientId, fakeClientSecret));
    }

    @Test
    void getClientCredentialsTokenReturnsClientCredentialsFlowTokenResponseWhenSuccessful() throws IOException {
        when(mockedClientCredentialsFlowTokenResponseCall.execute()).thenReturn(Response.success(new ClientCredentialsFlowTokenResponse()));

        Assertions.assertNotNull(sut.getClientCredentialToken(fakeClientId, fakeClientSecret));
    }
}
