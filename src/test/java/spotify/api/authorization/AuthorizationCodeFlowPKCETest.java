package spotify.api.authorization;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import spotify.api.enums.AuthorizationScope;
import spotify.config.ApiUrl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AuthorizationCodeFlowPKCETest {
    private final String clientId = "pee";
    private final String responseType = "pee";
    private final String redirectUri = "poo";
    private final String state = "poo";
    private final List<AuthorizationScope> scopes = Arrays.asList(AuthorizationScope.STREAMING, AuthorizationScope.USER_FOLLOW_MODIFY);
    private AuthorizationCodeFlowPKCE sut;
    private String codeChallengeMethod = "check";
    private String codeChallenge = ":D";

    @Test
    void constructUrlConstructsCorrectUrlString() {
        sut = new AuthorizationCodeFlowPKCE.Builder()
                .setClientId(clientId)
                .setResponseType(responseType)
                .setRedirectUri(redirectUri)
                .setState(state)
                .setScopes(scopes)
                .setCodeChallenge(codeChallenge)
                .setCodeChallengeMethod(codeChallengeMethod)
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
                "&code_challenge_method=" + codeChallengeMethod +
                "&code_challenge=" + codeChallenge;

        Assertions.assertEquals(expectedUrl, sut.constructUrl());
    }
}
