package spotify.api.authorization;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import spotify.api.enums.AuthorizationScope;
import spotify.config.ApiUrl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AuthorizationCodeFlowTest {
    private final String clientId = "pee";
    private final String responseType = "pee";
    private final String redirectUri = "poo";
    private final String state = "poo";
    private final boolean showDialog = true;
    private final List<AuthorizationScope> scopes = Arrays.asList(AuthorizationScope.STREAMING, AuthorizationScope.USER_FOLLOW_MODIFY);
    private AuthorizationCodeFlow sut;

    @Test
    void constructUrlConstructsCorrectUrlString() {
        sut = new AuthorizationCodeFlow.Builder()
                .setClientId(clientId)
                .setResponseType(responseType)
                .setRedirectUri(redirectUri)
                .setState(state)
                .setScopes(scopes)
                .setShowDialog(true)
                .build();

        final String scopesAsString = scopes.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(" "));

        final String expectedUrl = ApiUrl.ACCOUNTS_URL_HTTPS + "/authorize?" +
                "client_id=" + clientId +
                "&response_type=" + responseType +
                "&redirect_uri=" + redirectUri +
                "&scope=" + scopesAsString +
                "&state=" + state +
                "&show_dialog=" + showDialog;

        Assertions.assertEquals(expectedUrl, sut.constructUrl());
    }
}
