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

public class AuthorizationRefreshTokenTest extends AbstractAuthorizationTest {
    private final String fakeRefreshToken = "jiankai zheng";
    private final GrantType fakeGrantType = GrantType.REFRESH_TOKEN;
    private AuthorizationRefreshToken sut;
    @Mock
    private AuthorizationCodeFlowService mockedAuthorizationCodeFlowService;
    @Mock
    private Call<AuthorizationCodeFlowTokenResponse> mockedAuthorizationCodeFlowTokenResponseCall;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        sut = new AuthorizationRefreshToken(mockedAuthorizationCodeFlowService);

        when(mockedAuthorizationCodeFlowService.refreshAccessToken(fakeBasicAuth, fakeRefreshToken, fakeGrantType)).thenReturn(mockedAuthorizationCodeFlowTokenResponseCall);

        when(mockedAuthorizationCodeFlowTokenResponseCall.request()).thenReturn(new Request.Builder().url(fakeUrl).build());
    }


    @Test
    void refreshAccessTokenUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedAuthorizationCodeFlowTokenResponseCall.execute()).thenReturn(Response.success(new AuthorizationCodeFlowTokenResponse()));

        sut.refreshAccessToken(fakeClientId, fakeClientSecret, fakeRefreshToken);

        verify(mockedAuthorizationCodeFlowService).refreshAccessToken(fakeBasicAuth, fakeRefreshToken, fakeGrantType);
    }

    @Test
    void refreshAccessTokenExecutesHttpCall() throws IOException {
        when(mockedAuthorizationCodeFlowTokenResponseCall.execute()).thenReturn(Response.success(new AuthorizationCodeFlowTokenResponse()));

        sut.refreshAccessToken(fakeClientId, fakeClientSecret, fakeRefreshToken);

        verify(mockedAuthorizationCodeFlowTokenResponseCall).execute();
    }

    @Test
    void refreshAccessTokenThrowsSpotifyAuthorizationExceptionWhenResponseBodyIsNull() throws IOException {
        when(mockedAuthorizationCodeFlowTokenResponseCall.execute()).thenReturn(Response.success(null));

        Assertions.assertThrows(SpotifyAuthorizationFailedException.class, () -> sut.refreshAccessToken(fakeClientId, fakeClientSecret, fakeRefreshToken));
    }

    @Test
    void refreshAccessTokenThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedAuthorizationCodeFlowTokenResponseCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.refreshAccessToken(fakeClientId, fakeClientSecret, fakeRefreshToken));
    }

    @Test
    void refreshAccessTokenReturnsAuthorizationCodeFlowTokenResponseWhenSuccessful() throws IOException {
        when(mockedAuthorizationCodeFlowTokenResponseCall.execute()).thenReturn(Response.success(new AuthorizationCodeFlowTokenResponse()));

        Assertions.assertNotNull(sut.refreshAccessToken(fakeClientId, fakeClientSecret, fakeRefreshToken));
    }
}
