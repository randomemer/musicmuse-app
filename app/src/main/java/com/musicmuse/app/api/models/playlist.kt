package com.musicmuse.app.api.models

import com.squareup.moshi.Json

data class SpotifySimplifiedPlaylist(
  @Json(name = "description") val description: String?,
  @Json(name = "href") val href: String,
  @Json(name = "id") val id: String,
  @Json(name = "images") val images: List<SpotifyImage>,
  @Json(name = "name") val name: String,
  @Json(name = "public") val public: Boolean?,
  @Json(name = "type") val type: String,
  @Json(name = "uri") val uri: String
)

data class SpotifyCategoryPlaylistsResponse(
  @Json(name = "message") val message: String?,
  @Json(name = "playlists") val playlists: SpotifyPaginatedModel<SpotifySimplifiedPlaylist>
)

data class SpotifyPlaylistResponse(
  @Json(name = "description") val description: String?,
  @Json(name = "href") val href: String,
  @Json(name = "id") val id: String,
  @Json(name = "images") val images: List<SpotifyImage>,
  @Json(name = "name") val name: String,
  @Json(name = "tracks") val tracks: SpotifyPaginatedModel<SpotifyPlaylistTrack>
)
