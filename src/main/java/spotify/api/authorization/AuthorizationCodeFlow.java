package spotify.api.authorization;

import spotify.api.enums.AuthorizationScopes;
import spotify.config.ApiUrl;

import java.util.List;
import java.util.stream.Collectors;

public class AuthorizationCodeFlow {
    private String clientId;
    private String responseType;
    private String redirectUri;
    private String state;
    private List<AuthorizationScopes> scopes;
    private boolean showDialog;

    private AuthorizationCodeFlow() {
    }

    public String constructUrl() {
        String scopesWithSpaceDelimiter = this.scopes.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(" "));

        return ApiUrl.ACCOUNTS_URL_HTTPS + "/authorize?" +
                "client_id=" + clientId +
                "&response_type=" + responseType +
                "&redirect_uri=" + redirectUri +
                "&scope=" + scopesWithSpaceDelimiter +
                "&state=" + state +
                "&show_dialog=" + showDialog;
    }


    public static class Builder {
        private String clientId;
        private String responseType;
        private String redirectUri;
        private String state;
        private List<AuthorizationScopes> scopes;
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

        public Builder setScope(List<AuthorizationScopes> scopes) {
            this.scopes = scopes;

            return this;
        }

        public Builder setShowDialog(boolean showDialog) {
            this.showDialog = showDialog;

            return this;
        }

        public AuthorizationCodeFlow build() {
            AuthorizationCodeFlow authorizationCodeFlow = new AuthorizationCodeFlow();
            authorizationCodeFlow.clientId = this.clientId;
            authorizationCodeFlow.responseType = this.responseType;
            authorizationCodeFlow.redirectUri = this.redirectUri;
            authorizationCodeFlow.state = this.state;
            authorizationCodeFlow.scopes = this.scopes;
            authorizationCodeFlow.showDialog = this.showDialog;

            return authorizationCodeFlow;
        }
    }
}
