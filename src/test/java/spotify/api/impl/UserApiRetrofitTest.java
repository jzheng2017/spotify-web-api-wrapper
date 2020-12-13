package spotify.api.impl;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import retrofit2.Call;
import retrofit2.Response;
import spotify.exceptions.HttpRequestFailedException;
import spotify.exceptions.SpotifyActionFailedException;
import spotify.models.users.User;
import spotify.retrofit.services.UserService;

import java.io.IOException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserApiRetrofitTest extends AbstractApiRetrofitTest {
    private final String fakeUserId = "69";
    private UserApiRetrofit sut;
    @Mock
    private UserService mockedUserService;
    @Mock
    private Call<User> mockedUserCall;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        sut = new UserApiRetrofit(fakeAccessToken, mockedUserService);

        when(mockedUserService.getUser(fakeAccessTokenWithBearer, fakeUserId)).thenReturn(mockedUserCall);
        when(mockedUserService.getCurrentUser(fakeAccessTokenWithBearer)).thenReturn(mockedUserCall);

        when(mockedUserCall.request()).thenReturn(new Request.Builder().url(fakeUrl).build());
    }

    @Test
    void getCurrentUserUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedUserCall.execute()).thenReturn(Response.success(new User()));

        sut.getCurrentUser();

        verify(mockedUserService).getCurrentUser(fakeAccessTokenWithBearer);
    }

    @Test
    void getCurrentUserExecutesHttpCall() throws IOException {
        when(mockedUserCall.execute()).thenReturn(Response.success(new User()));

        sut.getCurrentUser();

        verify(mockedUserCall).execute();
    }

    @Test
    void getCurrentUserThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedUserCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.getCurrentUser());
    }

    @Test
    void getCurrentUserThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedUserCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.getCurrentUser());
    }

    @Test
    void getCurrentUserReturnsUserWhenSuccessful() throws IOException {
        when(mockedUserCall.execute()).thenReturn(Response.success(new User()));

        Assertions.assertNotNull(sut.getCurrentUser());
    }

    @Test
    void getUserUsesCorrectValuesToCreateHttpCall() throws IOException {
        when(mockedUserCall.execute()).thenReturn(Response.success(new User()));

        sut.getUser(fakeUserId);

        verify(mockedUserService).getUser(fakeAccessTokenWithBearer, fakeUserId);
    }

    @Test
    void getUserExecutesHttpCall() throws IOException {
        when(mockedUserCall.execute()).thenReturn(Response.success(new User()));

        sut.getUser(fakeUserId);

        verify(mockedUserCall).execute();
    }

    @Test
    void getUserThrowsSpotifyActionFailedExceptionWhenError() throws IOException {
        when(mockedUserCall.execute())
                .thenReturn(
                        Response.error(
                                400,
                                ResponseBody.create(MediaType.get("application/json"), getJson("error.json"))
                        )
                );

        Assertions.assertThrows(SpotifyActionFailedException.class, () -> sut.getUser(fakeUserId));
    }

    @Test
    void getUserThrowsHttpRequestFailedWhenHttpFails() throws IOException {
        when(mockedUserCall.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(HttpRequestFailedException.class, () -> sut.getUser(fakeUserId));
    }

    @Test
    void getUserReturnsUserWhenSuccessful() throws IOException {
        when(mockedUserCall.execute()).thenReturn(Response.success(new User()));

        Assertions.assertNotNull(sut.getUser(fakeUserId));
    }
}
