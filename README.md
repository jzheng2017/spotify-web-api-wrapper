# Spotify Web API Wrapper [![Build Status](https://dev.azure.com/jzheng21/jzheng/_apis/build/status/jzheng2017.spotify-web-api-wrapper?branchName=main)](https://dev.azure.com/jzheng21/jzheng/_build/latest?definitionId=1&branchName=main) [![Language grade: Java](https://img.shields.io/lgtm/grade/java/g/jzheng2017/spotify-web-api-wrapper.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/jzheng2017/spotify-web-api-wrapper/context:java)
Spotify API wrapper for Java

## Example usage
```java
        ClientCredentialsFlow clientCredentialsFlow = new ClientCredentialsFlow();
        String accessToken = clientCredentialsFlow.getAccessToken(
                "CLIENT ID",
                "CLIENT SECRET")
                .getAccessToken();

        SpotifyApi spotifyApi = new SpotifyApi(accessToken);

        AlbumFull albumFull = spotifyApi.getAlbum("ALBUM ID");
```
