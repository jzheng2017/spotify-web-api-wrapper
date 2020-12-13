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
import spotify.models.authorization.AuthorizationCodeFlowTokenResponse;
import spotify.retrofit.services.AuthorizationCodeFlowService;

import java.io.IOException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AuthorizationPKCERequestTokenTest extends AbstractAuthorizationTest {
    private final String fakeCode = "jzheng2017";
    private final String fakeUri = "https://github.com/jzheng2017/spotify-web-api-wrapper";
    private final String fakeCodeVerifier = "jiankai zheng";
    private final GrantType fakeGrantType = GrantType.AUTHORIZATION_CODE;
    private AuthorizationPKCERequestToken sut;
    @Mock
    private AuthorizationCodeFlowService mockedAuthorizationCodeFlowService;
    @Mock
    private Call<AuthorizationCodeFlowTokenResponse> mockedAuthorizationCodeFlowTokenResponseCall;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        sut = new AuthorizationPKCERequestToken(mockedAuthorizationCodeFlowService);

        when(mockedAuthorizationCodeFlowService.getAccessAndRefreshTokenPKCE(fakeClientId, fakeGrantType, fakeCode, fakeUri, fakeCodeVerifier)).thenReturn(mockedAuthorizationCodeFlowTokenResponseCall);

        when(mockedAuthorizationCodeFlowTokenResponseCall.request()).thenReturn(new Request.Builder().url(fakeUrl).build());
    }

    @Test
    void getAuthorizationPKCERequestTokenUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedAuthorizationCodeFlowTokenResponseCall.execute()).thenReturn(Response.success(new AuthorizationCodeFlowTokenResponse()));

        sut.getAuthorizationCodeToken(fakeClientId, fakeCode, fakeUri, fakeCodeVerifier);

        verify(mockedAuthorizationCodeFlowService).getAccessAndRefreshTokenPKCE(fakeClientId, fakeGrantType, fakeCode, fakeUri, fakeCodeVerifier);
    }

    @Test
    void getAuthorizationPKCERequestTokenExecutesHttpCall() throws IOException {
        when(mockedAuthorizationCodeFlowTokenResponseCall.execute()).thenReturn(Response.success(new AuthorizationCodeFlowTokenResponse()));

        sut.getAuthorizationCodeToken(fakeClientId, fakeCode, fakeUri, fakeCodeVerifier);

        verify(mockedAuthorizationCodeFlowTokenResponseCall).execute();
    }

    @Test
    void getAuthorizationPKCERequestTokenThrowsSpotifyAuthorizationExceptionWhenResponseBodyIsNull() throws IOException {
        when(mockedAuthorizationCodeFlowTokenResponseCall.execute()).thenReturn(Response.success(null));

        Assertions.assertThrows(SpotifyAuthorizationFailedException.class, () -> sut.getAuthorizationCodeToken(fakeClientId, fakeCode, fakeUri, fakeCodeVerifier));
    }

    @Test
    void getAuthorizationPKCERequestTokenThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedAuthorizationCodeFlowTokenResponseCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.getAuthorizationCodeToken(fakeClientId, fakeCode, fakeUri, fakeCodeVerifier));
    }

    @Test
    void getAuthorizationPKCERequestTokenReturnsClientCredentialsFlowTokenResponseWhenSuccessful() throws IOException {
        when(mockedAuthorizationCodeFlowTokenResponseCall.execute()).thenReturn(Response.success(new AuthorizationCodeFlowTokenResponse()));

        Assertions.assertNotNull(sut.getAuthorizationCodeToken(fakeClientId, fakeCode, fakeUri, fakeCodeVerifier));
    }
}
