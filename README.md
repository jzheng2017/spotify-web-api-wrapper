[![Maven Central](https://img.shields.io/maven-central/v/nl.jiankai/spotify-web-api-wrapper.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22nl.jiankai%22%20AND%20a:%22spotify-web-api-wrapper%22) [![Build Status](https://travis-ci.com/jzheng2017/spotify-web-api-wrapper.svg?branch=main)](https://travis-ci.com/jzheng2017/spotify-web-api-wrapper) [![Coverage Status](https://coveralls.io/repos/github/jzheng2017/spotify-web-api-wrapper/badge.svg?branch=main)](https://coveralls.io/github/jzheng2017/spotify-web-api-wrapper?branch=main) [![Language grade: Java](https://img.shields.io/lgtm/grade/java/g/jzheng2017/spotify-web-api-wrapper.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/jzheng2017/spotify-web-api-wrapper/context:java) [![Maintainability](https://api.codeclimate.com/v1/badges/7d64992a6eee6bf3ce10/maintainability)](https://codeclimate.com/github/jzheng2017/spotify-web-api-wrapper/maintainability) ![GitHub commit activity](https://img.shields.io/github/commit-activity/m/jzheng2017/spotify-web-api-wrapper)

# Spotify Web API Wrapper 
Spotify API wrapper for Java

## Table of Contents
- [Guides](#guides)
- [Support](#support)
- [Example usages](#example-usages)
- [Error Handling](#error-handling)
- [Why use this library?](#why-use-this-library)
- [Installation](#installation)
- [Contributing](#contributing)
- [License](#license)
- [Spotify endpoint coverage](#spotify-endpoint-coverage)

## Guides
Use the following guides provided by Spotify to use this library:
- [Authorization Guide](https://developer.spotify.com/documentation/general/guides/authorization-guide/)
- [Web API](https://developer.spotify.com/documentation/web-api/reference/)

## Support
If you need any help or have any questions there is a [Discord](https://discord.gg/EZrK9wSNBs) channel.

## Example usages
### Client Credentials Flow
The Client Credentials flow is used in server-to-server authentication. Only endpoints that do not access user information can be accessed. 
```java
ClientCredentialsFlow clientCredentialsFlow = new ClientCredentialsFlow();
String accessToken = clientCredentialsFlow.getClientCredentialToken(
        "CLIENT ID",
        "CLIENT SECRET")
        .getAccessToken();

SpotifyApi spotifyApi = new SpotifyApi(accessToken);

AlbumFull albumFull = spotifyApi.getAlbum("ALBUM ID");
```
### Authorization Code Flow
This flow is suitable for long-running applications in which the user grants permission only once. It provides an access token that can be refreshed. Since the token exchange involves sending your secret key, perform this on a secure location, like a backend service, and not from a client such as a browser or from a mobile app.

The first step to get an access and refresh token through the Authorization Code Flow is to build an url.
```java
AuthorizationCodeFlow authorizationCodeFlow = new AuthorizationCodeFlow.Builder()
        .setClientId("CLIENT ID")
        .setRedirectUri("https://www.example.com/callback/")
        .setResponseType("code")
        .setScopes(Arrays.asList(
                 AuthorizationScope.APP_REMOTE_CONTROL,
                 AuthorizationScope.PLAYLIST_MODIFY_PRIVATE))
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
AuthorizationRequestToken authorizationRequestToken = new AuthorizationRequestToken();
AuthorizationCodeFlowTokenResponse token = authorizationRequestToken
                .getAuthorizationCodeToken(
                        "CLIENT ID",
                        "CLIENT SECRET",
                        "AUTHORIZATION CODE",
                        "REDIRECT URI");
```

### Authorization Code Flow with Proof Key for Code Exchange (PKCE)
The authorization code flow with PKCE is the best option for mobile and desktop applications where it is unsafe to store your client secret. It provides your app with an access token that can be refreshed. For further information about this flow, see [IETF RFC-7636](https://tools.ietf.org/html/rfc7636).

The first step to get an access and refresh token through the Authorization PKCE Code Flow is to build an url.
```java
AuthorizationCodeFlowPKCE pkce = new AuthorizationCodeFlowPKCE.Builder()
     .setClientId("CLIENT ID")
     .setRedirectUri("REDIRECT URI")
     .setResponseType("code")
     .setScopes(Arrays.asList(
              AuthorizationScope.APP_REMOTE_CONTROL,
              AuthorizationScope.PLAYLIST_MODIFY_PRIVATE))
     .setCodeChallengeMethod("S256")
     .setCodeChallenge("CODE CHALLENGE")
     .setState("STATE")
     .build();
```

The above code will result in the following url.
```
https://accounts.spotify.com/authorize?client_id=CLIENT ID&response_type=code&redirect_uri=REDIRECT URI&scope=app-remote-control playlist-modify-private&state=STATE&code_challenge_method=S256&code_challenge=CODE CHALLENGE
```
By visiting the url it will display a prompt to authorize access within the given scopes. Authorizing access will redirect the user to the given redirect uri. An authorization code will also be returned, it can be found as a query parameter in the redirect uri. Use this authorization code for the second step. 

For the second step the following values need to be provided:
- Client ID
- Authorization Code (the code that got returned when redirected from spotify)
- Redirect Uri (the redirect uri that was given in the first step)
- Code verifier (the one that was generated at the first step)

```java
AuthorizationPKCERequestToken auth = new AuthorizationPKCERequestToken();
final String accessToken = auth.getAuthorizationCodeToken(
        "CLIENT ID",
        "CODE",
        "REDIRECT URI",
        "CODE VERIFIER")
        .getAccessToken();
```
### Using access token
The `AuthorizationCodeFlowTokenResponse` contains the access and refresh token. The access and refresh token can be used to access api endpoints.
```java
SpotifyApi spotifyApi = new SpotifyApi("ACCESS TOKEN", "REFRESH TOKEN");
AlbumFull albumFull = spotifyApi.getAlbum("ALBUM ID");
```

### Refreshing access token
When the access token has expired it can be refreshed using `AuthorizationRefreshToken`
```java
AuthorizationCodeFlowTokenResponse token = authorizationRefreshToken
                .refreshAccessToken(
                        "CLIENT ID",
                        "CLIENT SECRET",
                        "REFRESH TOKEN");
```
The above code example will return an `AuthorizationCodeFlowTokenResponse` which contains the new access and refresh token.

### Optional parameters
Many API endpoints have optional parameters. Passing in optional parameters is done with a `Map`. If you don't want to pass any optional parameters then just pass in an empty `Map`.
```java
SpotifyApi spotifyApi = new SpotifyApi("ACCESS TOKEN");

Map<String, String> optionalParameters = new HashMap<>();
optionalParameters.put("limit", "10");

CategoryFullPaging categories = spotifyApi.getCategories(optionalParameters);
```

## Error Handling
As of this moment the library can throw three different exceptions. 
### HttpRequestFailedException
This exception will be thrown when the HTTP request has failed on the server side of Spotify. This can for instance happen when the Spotify server is not reachable at the moment.
### SpotifyActionFailedException
This exception will be thrown when the HTTP request has been successfully handled, but the desired results has not been returned. This can for instance happen when one of the provided parameters are invalid.
### SpotifyAuthorizationFailedException
This exception will be thrown when authorization has failed. This may be thrown when for instance the provided credentials are not valid.

## Why use this library?
Because this library is very simple and straightforward with a clear and simple interface. You only need to provide a few credential info and you're ready to access the endpoints. All other stuff is already taken care for you. When looking at other similar existing libraries, they have really complicated constructs to access an endpoint, where with this library it can be done with 1 line of code. 

## Installation
### Maven
Latest official release:
```xml
<dependency>
  <groupId>nl.jiankai</groupId>
    <artifactId>spotify-web-api-wrapper</artifactId>
    <version>1.5.7</version>
</dependency>
```
### Gradle
Latest official release:
```gradle
implementation 'nl.jiankai:spotify-web-api-wrapper:1.5.7'
```

## Contributing
Do you want to contribute to this library? Well, you're in the right place! We'd love your help. Please refer to our [contribution guideline](https://github.com/jzheng2017/spotify-web-api-wrapper/blob/main/CONTRIBUTING.md).

## License
See the [LICENSE](https://github.com/jzheng2017/spotify-web-api-wrapper/blob/main/LICENSE) file for the license rights and limitations (MIT).

## Spotify endpoint coverage
This is the most recent coverage in the repository. The marked endpoints may not be available yet in the most recent official release.
- [x] Albums
- - [x] Get an Album
- - [x] Get Several Albums
- - [x] Get an Album's Tracks
- [x] Artists
- - [x] Get an Artist's Albums
- - [x] Get an Artist's Related Artists
- - [x] Get an Artist's Top Tracks
- - [x] Get an Artist
- - [x] Get Several Artists
- [x] Browse
- - [x] Get Available Genre Seeds
- - [x] Get a List of Browse Categories
- - [x] Get a Single Browse Category
- - [x] Get a Category's playlists
- - [x] Get a List of Featured Playlists
- - [x] Get a List of New Releases
- - [x] Get Recommendations Based on Seeds
- [x] Episodes
- - [x] Get an Episode
- - [x] Get Several Episodes
- [x] Follow
- - [x] Unfollow Artists or Users
- - [x] Unfollow a Playlist
- - [x] Check if Current User Follows Artists or Users
- - [x] Get Followed Artists
- - [x] Check if Users Follow a Playlist
- - [x] Follow Artists or Users
- - [x] Follow a Playlist
- [x] Library
- - [x] Remove Albums for Current User
- - [x] Remove User's Saved Shows
- - [x] Remove Tracks for Current User
- - [x] Check Current User's Saved Albums
- - [x] Check User's Saved Shows
- - [x] Check Current User's Saved Tracks
- - [x] Get Current User's Saved Albums
- - [x] Get User's Saved Shows
- - [x] Get Current User's Saved Tracks
- - [x] Save Albums for Current User
- - [x] Save Shows for Current User
- - [x] Save Tracks for Current User
- [x] Personalization
- - [x] Get User's Top Artists and Tracks
- [x] Player
- - [x] Get the Current User's Recently Played Tracks
- - [x] Get Information About The User's Current Playback
- - [x] Get a User's Available Devices
- - [x] Get the User's Currently Playing Track
- - [x] Skip User's Playback To Next Track
- - [x] Skip User's Playback To Previous Track
- - [x] Add an item to the end of the user's current playback queue.
- - [x] Pause a User's Playback
- - [x] Start/Resume a User's Playback
- - [x] Set Repeat Mode On User's Playback
- - [x] Seek To Position In Currently Playing Track
- - [x] Toggle Shuffle For User's Playback
- - [x]	Transfer a User's Playback
- - [x] Set Volume For User's Playback
- [x] Playlists
- - [x] Remove Items from a Playlist
- - [x] Get a List of Current User's Playlists
- - [x] Get a Playlist Cover Image
- - [x] Get a Playlist's Items
- - [x] Get a Playlist
- - [x] Get a List of a User's Playlists
- - [x] Add Items to a Playlist
- - [x] Create a Playlist
- - [x] Upload a Custom Playlist Cover Image
- - [x] Reorder a Playlist's Items
- - [x] Replace a Playlist's Items
- - [x] Change a Playlist's Details
- [x] Search
- - [x] Search for an item
- [x] Tracks
- - [x] Get a Track
- - [x] Get Several Tracks
- - [x] Get Audio Analysis for a Track
- - [x] Get Audio Features for a Track
- - [x] Get Audio Features for Several Tracks
- [x] Shows 
- - [x] Get Several Shows
- - [x] Get a Show's Episodes
- - [x] Get a Show
- [x] Users Profile
- - [x] Get Current User's Profile
- - [x] Get a User's Profile


