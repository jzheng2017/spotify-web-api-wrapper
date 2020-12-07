package spotify.api.authorization;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spotify.api.enums.AuthorizationScope;
import spotify.config.ApiUrl;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This class constructs the URL needed for the first step in the Authorization Code Flow PKCE.
 * Executing this step should result in a code that can be used to retrieve an access and refresh token.
 * <p>
 * For more information see: @see <a href="https://developer.spotify.com/documentation/general/guides/authorization-guide/#authorization-code-flow">Authorization Code Flow</a>
 *
 * @author Jiankai Zheng
 */
public class AuthorizationCodeFlowPKCE {
    private final Logger logger = LoggerFactory.getLogger(AuthorizationCodeFlowPKCE.class);
    private String clientId;
    private String responseType;
    private String redirectUri;
    private String state;
    private List<AuthorizationScope> scopes;
    private String codeChallengeMethod;
    private String codeChallenge;

    private AuthorizationCodeFlowPKCE() {
    }

    /**
     * Constructs a url for the first step in the authorization PKCE code flow
     *
     * @return constructed url to be used to authorize access and retrieve a code
     */
    public String constructUrl() {
        final String scopesWithSpaceDelimiter = this.scopes.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(" "));

        final String constructedUrl = ApiUrl.ACCOUNTS_URL_HTTPS + "/authorize?" +
                "client_id=" + clientId +
                "&response_type=" + responseType +
                "&redirect_uri=" + redirectUri +
                "&scope=" + scopesWithSpaceDelimiter +
                "&state=" + state +
                "&code_challenge_method=" + codeChallengeMethod +
                "&code_challenge=" + codeChallenge;

        logger.trace("Constructing url for authorization PKCE code flow");
        logger.debug("Constructed url: {}.", constructedUrl);
        return constructedUrl;
    }

    @Override
    public String toString() {
        return "AuthorizationCodeFlowPKCE{" +
                "clientId='" + clientId + '\'' +
                ", responseType='" + responseType + '\'' +
                ", redirectUri='" + redirectUri + '\'' +
                ", state='" + state + '\'' +
                ", scopes=" + scopes +
                ", codeChallengeMethod='" + codeChallengeMethod + '\'' +
                ", codeChallenge='" + codeChallenge + '\'' +
                '}';
    }

    /**
     * Builder for constructing an AuthorizationCodeFlowPKCE object.
     */
    public static class Builder {
        private final Logger logger = LoggerFactory.getLogger(AuthorizationCodeFlowPKCE.Builder.class);
        private String clientId;
        private String responseType;
        private String redirectUri;
        private String state;
        private List<AuthorizationScope> scopes;
        private String codeChallengeMethod;
        private String codeChallenge;

        public AuthorizationCodeFlowPKCE.Builder setClientId(String clientId) {
            this.clientId = clientId;

            return this;
        }

        public AuthorizationCodeFlowPKCE.Builder setResponseType(String responseType) {
            this.responseType = responseType;

            return this;
        }

        public AuthorizationCodeFlowPKCE.Builder setRedirectUri(String redirectUri) {
            this.redirectUri = redirectUri;

            return this;
        }

        public AuthorizationCodeFlowPKCE.Builder setScopes(List<AuthorizationScope> scopes) {
            this.scopes = scopes;

            return this;
        }

        public AuthorizationCodeFlowPKCE.Builder setCodeChallengeMethod(String codeChallengeMethod) {
            this.codeChallengeMethod = codeChallengeMethod;

            return this;
        }

        public AuthorizationCodeFlowPKCE.Builder setCodeChallenge(String codeChallenge) {
            this.codeChallenge = codeChallenge;

            return this;
        }

        public AuthorizationCodeFlowPKCE.Builder setState(String state) {
            this.state = state;

            return this;
        }

        /**
         * Build authorization PKCE code flow.
         *
         * @return the authorization PKCE code flow
         */
        public AuthorizationCodeFlowPKCE build() {
            logger.trace("Constructing AuthorizationCodeFlowPKCE object.");
            AuthorizationCodeFlowPKCE authorizationCodeFlowPKCE = new AuthorizationCodeFlowPKCE();
            authorizationCodeFlowPKCE.clientId = this.clientId;
            authorizationCodeFlowPKCE.responseType = this.responseType;
            authorizationCodeFlowPKCE.redirectUri = this.redirectUri;
            authorizationCodeFlowPKCE.state = this.state;
            authorizationCodeFlowPKCE.scopes = this.scopes;
            authorizationCodeFlowPKCE.codeChallengeMethod = this.codeChallengeMethod;
            authorizationCodeFlowPKCE.codeChallenge = this.codeChallenge;

            logger.trace("AuthorizationCodeFlowPKCE successfully constructed.");
            logger.debug(authorizationCodeFlowPKCE.toString());
            return authorizationCodeFlowPKCE;
        }
    }
}
