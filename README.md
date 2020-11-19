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


