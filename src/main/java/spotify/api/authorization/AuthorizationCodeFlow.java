package spotify.api.authorization;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spotify.api.enums.AuthorizationScope;
import spotify.config.ApiUrl;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This class constructs the URL needed for the first step in the Authorization Code Flow.
 * Executing this step should result in a code that can be used to retrieve an access and refresh token.
 * <p>
 * For more information see: {@link <a href="https://developer.spotify.com/documentation/general/guides/authorization-guide/#authorization-code-flow">Authorization Code Flow</a>}
 *
 * @author Jiankai Zheng
 */
public class AuthorizationCodeFlow {
    private final Logger logger = LoggerFactory.getLogger(AuthorizationCodeFlow.class);
    private String clientId;
    private String responseType;
    private String redirectUri;
    private String state;
    private List<AuthorizationScope> scopes;
    private boolean showDialog;

    private AuthorizationCodeFlow() {
    }

    /**
     * Constructs a url for the first step in the authorization code flow
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
                "&show_dialog=" + showDialog;

        logger.trace("Constructing url for authorization code flow");
        logger.debug(String.format("Constructed url: %s.", constructedUrl));
        return constructedUrl;
    }

    /**
     * Builder for constructing an AuthorizationCodeFlow object.
     */
    public static class Builder {
        private final Logger logger = LoggerFactory.getLogger(Builder.class);
        private String clientId;
        private String responseType;
        private String redirectUri;
        private String state;
        private List<AuthorizationScope> scopes;
        private boolean showDialog;

        public Builder setClientId(String clientId) {
            this.clientId = clientId;

            return this;
        }

        public Builder setResponseType(String responseType) {
            this.responseType = responseType;

            return this;
        }

        public Builder setRedirectUri(String redirectUri) {
            this.redirectUri = redirectUri;

            return this;
        }

        public Builder setState(String state) {
            this.state = state;

            return this;
        }

        public Builder setScope(List<AuthorizationScope> scopes) {
            this.scopes = scopes;

            return this;
        }

        public Builder setShowDialog(boolean showDialog) {
            this.showDialog = showDialog;

            return this;
        }

        /**
         * Build authorization code flow.
         *
         * @return the authorization code flow
         */
        public AuthorizationCodeFlow build() {
            logger.trace("Constructing AuthorizationCodeFlow object.");
            AuthorizationCodeFlow authorizationCodeFlow = new AuthorizationCodeFlow();
            authorizationCodeFlow.clientId = this.clientId;
            authorizationCodeFlow.responseType = this.responseType;
            authorizationCodeFlow.redirectUri = this.redirectUri;
            authorizationCodeFlow.state = this.state;
            authorizationCodeFlow.scopes = this.scopes;
            authorizationCodeFlow.showDialog = this.showDialog;

            logger.trace("AuthorizationCodeFlow successfully constructed.");
            logger.debug(authorizationCodeFlow.toString());
            return authorizationCodeFlow;
        }
    }

    @Override
    public String toString() {
        return "AuthorizationCodeFlow{" +
                "clientId='" + clientId + '\'' +
                ", responseType='" + responseType + '\'' +
                ", redirectUri='" + redirectUri + '\'' +
                ", state='" + state + '\'' +
                ", scopes=" + scopes +
                ", showDialog=" + showDialog +
                '}';
    }
}
