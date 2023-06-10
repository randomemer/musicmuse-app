package com.musicmuse.app.api.models

import com.squareup.moshi.Json

data class SpotifyTrack(
  @Json(name = "album") val album: SpotifyAlbum,
  @Json(name = "artists") val artists: List<SpotifySimplifiedArtist>,
  @Json(name = "name") val name: String,
  @Json(name = "preview_url") val previewUrl: String?,
  override val href: String,
  override val id: String,
  override val type: String,
  override val uri: String
) : SpotifyResource(href, id, type, uri)

data class SpotifyEpisode(
  override val href: String,
  override val id: String,
  override val type: String,
  override val uri: String
) : SpotifyResource(href, id, type, uri)

data class SpotifyPlaylistTrack(
  @Json(name = "added_at") val addedAt: String,
  @Json(name = "is_local") val isLocal: Boolean,
  @Json(name = "track") val track: SpotifyResource
)
