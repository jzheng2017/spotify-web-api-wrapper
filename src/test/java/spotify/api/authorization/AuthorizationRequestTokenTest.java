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

public class AuthorizationRequestTokenTest extends AbstractAuthorizationTest {
    private final GrantType fakeGrantType = GrantType.AUTHORIZATION_CODE;
    private AuthorizationRequestToken sut;
    @Mock
    private AuthorizationCodeFlowService mockedAuthorizationCodeFlowService;
    @Mock
    private Call<AuthorizationCodeFlowTokenResponse> mockedAuthorizationCodeFlowTokenResponseCall;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        sut = new AuthorizationRequestToken(mockedAuthorizationCodeFlowService);

        when(mockedAuthorizationCodeFlowService.getAccessAndRefreshToken(fakeBasicAuth, fakeCode, fakeUri, fakeGrantType)).thenReturn(mockedAuthorizationCodeFlowTokenResponseCall);

        when(mockedAuthorizationCodeFlowTokenResponseCall.request()).thenReturn(new Request.Builder().url(fakeUrl).build());
    }

    @Test
    void getAuthorizationRequestTokenUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedAuthorizationCodeFlowTokenResponseCall.execute()).thenReturn(Response.success(new AuthorizationCodeFlowTokenResponse()));

        sut.getAuthorizationCodeToken(fakeClientId, fakeClientSecret, fakeCode, fakeUri);

        verify(mockedAuthorizationCodeFlowService).getAccessAndRefreshToken(fakeBasicAuth, fakeCode, fakeUri, fakeGrantType);
    }

    @Test
    void getAuthorizationRequestTokenExecutesHttpCall() throws IOException {
        when(mockedAuthorizationCodeFlowTokenResponseCall.execute()).thenReturn(Response.success(new AuthorizationCodeFlowTokenResponse()));

        sut.getAuthorizationCodeToken(fakeClientId, fakeClientSecret, fakeCode, fakeUri);

        verify(mockedAuthorizationCodeFlowTokenResponseCall).execute();
    }

    @Test
    void getAuthorizationRequestTokenThrowsSpotifyAuthorizationExceptionWhenResponseBodyIsNull() throws IOException {
        when(mockedAuthorizationCodeFlowTokenResponseCall.execute()).thenReturn(Response.success(null));

        Assertions.assertThrows(SpotifyAuthorizationFailedException.class, () -> sut.getAuthorizationCodeToken(fakeClientId, fakeClientSecret, fakeCode, fakeUri));
    }

    @Test
    void getAuthorizationRequestTokenThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedAuthorizationCodeFlowTokenResponseCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.getAuthorizationCodeToken(fakeClientId, fakeClientSecret, fakeCode, fakeUri));
    }

    @Test
    void getAuthorizationRequestTokenReturnsAuthorizationCodeFlowTokenResponseWhenSuccessful() throws IOException {
        when(mockedAuthorizationCodeFlowTokenResponseCall.execute()).thenReturn(Response.success(new AuthorizationCodeFlowTokenResponse()));

        Assertions.assertNotNull(sut.getAuthorizationCodeToken(fakeClientId, fakeClientSecret, fakeCode, fakeUri));
    }
}
