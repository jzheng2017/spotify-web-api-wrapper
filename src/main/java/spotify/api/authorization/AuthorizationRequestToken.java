package spotify.api.authorization;

import okhttp3.Credentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import spotify.api.enums.GrantType;
import spotify.factories.RetrofitHttpServiceFactory;
import spotify.models.authorization.AuthorizationCodeFlowTokenResponse;
import spotify.retrofit.services.AuthorizationCodeFlowService;
import spotify.utils.HttpUtil;

/**
 * This class takes care of the second step in the Authorization Code Flow.
 * Executing this step should result in an access and refresh token.
 * <p>
 * For more information see: <a href="https://developer.spotify.com/documentation/general/guides/authorization-guide/#authorization-code-flow">Authorization Code Flow</a>
 *
 * @author Jiankai Zheng (jk.zheng@hotmail.com)
 * @since 1.0.0
 */
public class AuthorizationRequestToken {
    private final Logger logger = LoggerFactory.getLogger(AuthorizationRequestToken.class);
    private final AuthorizationCodeFlowService authorizationCodeFlowService;

    public AuthorizationRequestToken() {
        this.authorizationCodeFlowService = RetrofitHttpServiceFactory.getAuthorizationCodeFlowService();
    }

    public AuthorizationRequestToken(AuthorizationCodeFlowService authorizationCodeFlowService) {
        this.authorizationCodeFlowService = authorizationCodeFlowService;
    }

    /**
     * Makes an http call to retrieve the access and refresh tokens.
     *
     * @param clientId     the id of the client
     * @param clientSecret the secret of the client
     * @param code         the authorization code returned from the initial request to the Account /authorize endpoint
     * @param redirectUri  the redirect uri supplied when requesting the authorization code
     * @return an object containing values like the access and refresh token
     */
    public AuthorizationCodeFlowTokenResponse getAuthorizationCodeToken(String clientId, String clientSecret, String code, String redirectUri) {
        logger.trace("Encoding client id and secret to base 64.");
        final String base64EncodedBasicAuth = Credentials.basic(clientId, clientSecret);

        logger.trace("Constructing HTTP call to fetch an access and refresh token.");
        final Call<AuthorizationCodeFlowTokenResponse> httpCall = authorizationCodeFlowService
                .getAccessAndRefreshToken(
                        base64EncodedBasicAuth,
                        code,
                        redirectUri,
                        GrantType.AUTHORIZATION_CODE);

        return HttpUtil.executeAuthorizationHttpCall(httpCall, logger);
    }
}
