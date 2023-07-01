# Music Muse App

Music Muse is a music streaming app with rich features written in kotlin purely
with Jetpack Compose, which means zero xml layout files. Content in the app is
from Spotify API and ownership is reserved with Spotify. All tracks are only 30
second previews in accordance with their API usage policy. Includes
authentication with firebase google sign-in.
It also implements the latest Material 3 design into its components and theme.

## Navigation Graph

<p align="center">
  <img alt="Navigation Graph" src="https://media.discordapp.net/attachments/1010184187082461196/1124693695829135370/App.png?width=1191&height=670" style="margin: 0 auto;" />
</p>

The navigation graph primarily has three main screen : Home, Explore and
Profile. Each main screen is a nested navigation graph in jetpack compose
navigation. Each screen is explained in detail below.

## Home Screen

<p align="center">
  <img alt="Home Screen" src="https://media.discordapp.net/attachments/1010184187082461196/1124695790854615040/home.png?width=312&height=668" style="margin: 0 auto;" />
</p>

Spotify
Documentation : https://developer.spotify.com/documentation/web-api/reference/get-featured-playlists

Shows a collection of features playlists from Spotify for the current time of
the day.

## Explore Screen

<p align="center">
  <img alt="Profile Screen" src="https://media.discordapp.net/attachments/1010184187082461196/1124693009385136138/explore.png?width=316&height=670" style="margin: 0 auto;" />
</p>

Spotify
Documentation : https://developer.spotify.com/documentation/web-api/reference/get-categories

Displays all the available categories / genres of music available.

## Profile Screen

<p align="center">
  <img alt="Profile Screen" src="https://media.discordapp.net/attachments/1010184187082461196/1124697792359706654/profile.png?width=316&height=670" style="margin: 0 auto;" />
</p>

Simple screen showing the current logged-in user with Google.

## Category Screen

<p align="center">
  <img alt="Profile Screen" src="https://media.discordapp.net/attachments/1010184187082461196/1124709131056582756/image.png?width=316&height=670" style="margin: 0 auto;" />
</p>

Spotify
Documentation : https://developer.spotify.com/documentation/web-api/reference/get-a-categories-playlists

Shows a list of playlists for the selected category.

## Search Screen

<p align="center">
  <img alt="Profile Screen" src="https://media.discordapp.net/attachments/1010184187082461196/1124699493179326510/search.png?width=316&height=670" style="margin: 0 auto;" />
</p>

Spotify
Documentation : https://developer.spotify.com/documentation/web-api/reference/search

Search for tracks and playlists in the spotify database.

## Playlist Screen

<p align="center">
  <img alt="Profile Screen" src="https://media.discordapp.net/attachments/1010184187082461196/1124710978311958528/playlist.png?width=316&height=670" style="margin: 0 auto;" />
</p>

Spotify
Documentation : https://developer.spotify.com/documentation/web-api/reference/get-playlist

List of tracks in a playlist. Upon clicking a track, the floating track player
comes into view and starts playing the track


