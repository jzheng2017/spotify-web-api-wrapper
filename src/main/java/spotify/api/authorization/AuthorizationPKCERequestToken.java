package spotify.api.authorization;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import spotify.api.enums.GrantType;
import spotify.factories.RetrofitHttpServiceFactory;
import spotify.models.authorization.AuthorizationCodeFlowTokenResponse;
import spotify.retrofit.services.AuthorizationCodeFlowService;
import spotify.utils.HttpUtil;

/**
 * This class takes care of the second step in the Authorization Code PKCE Flow.
 * Executing this step should result in an access and refresh token.
 * <p>
 * For more information see: @see <a href="https://developer.spotify.com/documentation/general/guides/authorization-guide/#authorization-code-flow-with-proof-key-for-code-exchange-pkce">Authorization Code Flow (PKCE)</a>
 *
 * @author Jiankai Zheng (jk.zheng@hotmail.com)
 * @since 1.5.0
 */
public class AuthorizationPKCERequestToken {
    private final Logger logger = LoggerFactory.getLogger(AuthorizationRequestToken.class);
    private final AuthorizationCodeFlowService authorizationCodeFlowService;

    public AuthorizationPKCERequestToken() {
        this.authorizationCodeFlowService = RetrofitHttpServiceFactory.getAuthorizationCodeFlowService();
    }

    public AuthorizationPKCERequestToken(AuthorizationCodeFlowService authorizationCodeFlowService) {
        this.authorizationCodeFlowService = authorizationCodeFlowService;
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
