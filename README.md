# spotify-web-api-wrapper
Spotify API wrapper for Java

## Example usage
```java
        ClientCredentialsFlow clientCredentialsFlow = new ClientCredentialsFlow();
        clientCredentialsFlow.doClientCredentialsFlow(
                "CLIENT ID",
                "CLIENT SECRET",
                "client_credentials");

        String accessToken = clientCredentialsFlow.getClientCredentialsFlowToken().getAccessToken();

        SpotifyApi spotifyApi = new SpotifyApi(accessToken);

        AlbumFull albumFull = spotifyApi.getAlbum("ALBUM ID");
```
