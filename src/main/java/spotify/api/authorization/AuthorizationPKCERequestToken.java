package spotify.api.authorization;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import spotify.api.enums.GrantType;
import spotify.factories.RetrofitHttpServiceFactory;
import spotify.models.authorization.AuthorizationCodeFlowTokenResponse;
import spotify.retrofit.services.AuthorizationCodeFlowService;
import spotify.utils.HttpUtil;

public class AuthorizationPKCERequestToken {
    private final Logger logger = LoggerFactory.getLogger(AuthorizationRequestToken.class);
    private final AuthorizationCodeFlowService authorizationCodeFlowService;

    public AuthorizationPKCERequestToken() {
        this.authorizationCodeFlowService = RetrofitHttpServiceFactory.getAuthorizationCodeFlowService();
    }

    /**
     * Makes an http call to retrieve the access and refresh tokens.
     *
     * @param clientId    the id of the client
     * @param code        the authorization code returned from the initial request to the Account /authorize endpoint
     * @param redirectUri the redirect uri supplied when requesting the authorization code
     * @return an object containing values like the access and refresh token
     */
    public AuthorizationCodeFlowTokenResponse getAuthorizationCodeToken(String clientId, String code, String redirectUri, String codeVerifier) {
        logger.trace("Constructing HTTP call to fetch an access and refresh token.");
        final Call<AuthorizationCodeFlowTokenResponse> httpCall = authorizationCodeFlowService
                .getAccessAndRefreshTokenPKCE(
                        clientId,
                        GrantType.AUTHORIZATION_CODE,
                        code,
                        redirectUri,
                        codeVerifier
                );

        return HttpUtil.executeAuthorizationHttpCall(httpCall, logger);
    }
}
