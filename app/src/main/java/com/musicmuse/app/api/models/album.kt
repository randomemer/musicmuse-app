package com.musicmuse.app.api.models

import com.squareup.moshi.Json

data class SpotifyAlbum(
  @Json(name = "total_tracks") val totalTracks: Int,
  @Json(name = "external_urls") val externalUrls: SpotifyExternalUrls,
  @Json(name = "href") val href: String,
  @Json(name = "id") val id: String,
  @Json(name = "images") val images: List<SpotifyImage>,
  @Json(name = "name") val name: String
)
