# Spotify Web API Wrapper [![Build Status](https://dev.azure.com/jzheng21/jzheng/_apis/build/status/jzheng2017.spotify-web-api-wrapper?branchName=main)](https://dev.azure.com/jzheng21/jzheng/_build/latest?definitionId=1&branchName=main) [![Language grade: Java](https://img.shields.io/lgtm/grade/java/g/jzheng2017/spotify-web-api-wrapper.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/jzheng2017/spotify-web-api-wrapper/context:java)
Spotify API wrapper for Java

## Example usages
### Client Credentials Flow
The Client Credentials flow is used in server-to-server authentication. Only endpoints that do not access user information can be accessed. 
```java
ClientCredentialsFlow clientCredentialsFlow = new ClientCredentialsFlow();
String accessToken = clientCredentialsFlow.getAccessToken(
        "CLIENT ID",
        "CLIENT SECRET")
        .getAccessToken();

SpotifyApi spotifyApi = new SpotifyApi(accessToken);

AlbumFull albumFull = spotifyApi.getAlbum("ALBUM ID");
```
### Authorization Code Flow
This flow is suitable for long-running applications in which the user grants permission only once. It provides an access token that can be refreshed. Since the token exchange involves sending your secret key, perform this on a secure location, like a backend service, and not from a client such as a browser or from a mobile app.

The first step to get an access and refresh token through the Authorization Code Flow is build an url.
```java
AuthorizationCodeFlow authorizationCodeFlow = new AuthorizationCodeFlow.Builder()
        .setClientId("CLIENT ID")
        .setRedirectUri("https://www.example.com/callback/")
        .setResponseType("code")
        .setScope(Arrays.asList(AuthorizationScope.APP_REMOTE_CONTROL, AuthorizationScope.PLAYLIST_MODIFY_PRIVATE))
        .build();
```
The above code will result in the following url.
```
https://accounts.spotify.com/authorize?client_id=CLIENT ID&response_type=code&redirect_uri=https://www.example.com/callback/&scope=app-remote-control playlist-modify-private&state=null&show_dialog=false
```
By visiting the url it will display a prompt to authorize access within the given scopes. Authorizing access will redirect the user to the given redirect uri. An authorization code will also be returned, it can be found as a query parameter in the redirect uri. Use this authorization code for the second step. 

For the second step the following values need to be provided:
- Client ID
- Client Secret
- Authorization Code (the code that got returned when redirected from spotify)
- Redirect Uri (the redirect uri that was given in the first step)

```java
   AuthorizationRequestTokens authorizationRequestTokens = new AuthorizationRequestTokens();
   AuthorizationCodeFlowTokenResponse token = authorizationRequestTokens.getAccessAndRefreshToken("CLIENT ID", "CLIENT SECRET", "AUTHORIZATION CODE", "REDIRECT URI");
```
The `AuthorizationCodeFlowTokenResponse` contains the access and refresh token. The access and refresh token can be used to access api endpoints.
```java
SpotifyApi spotifyApi = new SpotifyApi("ACCESS TOKEN", "REFRESH TOKEN");
AlbumFull albumFull = spotifyApi.getAlbum("ALBUM ID");
```

When the access token has expired it can be refreshed using `AuthorizationRefreshToken`
```java
AuthorizationCodeFlowTokenResponse token = authorizationRefreshToken.refreshAccessToken("CIENT ID", "CLIENT SECRET", "REFRESH TOKEN");
```
The above code example will return an `AuthorizationCodeFlowTokenResponse` which contains the new access and refresh token.

## Spotify endpoint coverage
- [x] Albums
- - [x] Get an Album
- - [x] Get Several Albums
- - [x] Get an Album's Tracks
- [ ] Artists
- - [ ] Get an Artist's Albums
- - [ ] Get an Artist's Related Artists
- - [ ] Get an Artist's Top Tracks
- - [ ] Get an Artist
- - [ ] Get Several Artists
- [ ] Browse
- - [ ] Get Available Genre Seeds
- - [ ] Get a List of Browse Categories
- - [ ] Get a Single Browse Category
- - [ ] Get a Category's playlists
- - [ ] Get a List of Featured Playlists
- - [ ] Get a List of New Releases
- - [ ] Get Recommendations Based on Seeds
- [ ] Episodes
- - [ ] Get an Episode
- - [ ] Get Several Episodes
- [ ] Follow
- - [ ] Unfollow Artists or Users
- - [ ] Unfollow a Playlist
- - [ ] Check if Current User Follows Artists or Users
- - [ ] Get Followed Artists
- - [ ] Check if Users Follow a Playlist
- - [ ] Follow Artists or Users
- - [ ] Follow a Playlist
- [ ] Library
- - [ ] Remove Albums for Current User
- - [ ] Remove User's Saved Shows
- - [ ] Remove Tracks for Current User
- - [ ] Check Current User's Saved Albums
- - [ ] Check User's Saved Shows
- - [ ] Check Current User's Saved Tracks
- - [ ] Get Current User's Saved Albums
- - [ ] Get User's Saved Shows
- - [ ] Get Current User's Saved Tracks
- - [ ] Save Albums for Current User
- - [ ] Save Shows for Current User
- - [ ] Save Tracks for Current User
- [ ] Personalization
- - [ ] Get User's Top Artists and Tracks
- [ ] Player
- - [ ] Get the Current User's Recently Played Tracks
- - [ ] Get Information About The User's Current Playback
- - [ ] Get a User's Available Devices
- - [ ] Get the User's Currently Playing Track
- - [ ] Skip User's Playback To Next Track
- - [ ] Skip User's Playback To Previous Track
- - [ ] Add an item to the end of the user's current playback queue.
- - [ ] Pause a User's Playback
- - [ ] Start/Resume a User's Playback
- - [ ] Set Repeat Mode On User's Playback
- - [ ] Seek To Position In Currently Playing Track
- - [ ] Toggle Shuffle For User's Playback
- - [ ]	Transfer a User's Playback
- - [ ] Set Volume For User's Playback
- [ ] Playlists
- - [ ] Remove Items from a Playlist
- - [ ] Get a List of Current User's Playlists
- - [ ] Get a Playlist Cover Image
- - [ ] Get a Playlist's Items
- - [ ] Get a Playlist
- - [ ] Get a List of a User's Playlists
- - [ ] Add Items to a Playlist
- - [ ] Create a Playlist
- - [ ] Upload a Custom Playlist Cover Image
- - [ ] Reorder or Replace a Playlist's Items
- - [ ] Change a Playlist's Details
- [ ] Search
- - [ ] Search for an item
- [x] Tracks
- - [x] Get a Track
- - [x] Get Several Tracks
- - [x] Get Audio Analysis for a Track
- - [x] Get Audio Features for a Track
- - [x] Get Audio Features for Several Tracks
- [ ] Shows 
- - [ ] Get Several Shows
- - [ ] Get a Show's Episodes
- - [ ] Get a Show
- [ ] Users Profile
- - [ ] Get Current User's Profile
- - [ ] Get a User's Profile


