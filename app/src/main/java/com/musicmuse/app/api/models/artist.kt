package com.musicmuse.app.api.models

import com.squareup.moshi.Json

data class SpotifySimplifiedArtist(
  @Json(name = "external_urls") val externalUrls: SpotifyExternalUrls,
  @Json(name = "href") val href: String,
  @Json(name = "id") val id: String,
  @Json(name = "name") val name: String
)
