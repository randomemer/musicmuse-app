package com.musicmuse.app.utils

import com.squareup.moshi.Json

data class SpotifyTokenResponse(
  @Json(name = "access_token") val accessToken: String,
  @Json(name = "expires_in") val expiresIn: Int,
  @Json(name = "token_type") val tokenType: String,
)

data class SpotifyPaginatedModel<T>(
  @Json(name = "href") val href: String,
  @Json(name = "items") val items: List<T>,
  @Json(name = "limit") val limit: Int,
  @Json(name = "next") val next: String?,
  @Json(name = "offset") val offset: Int,
  @Json(name = "previous") val previous: String?,
  @Json(name = "total") val total: Int,
)


data class SpotifyImage(
  @Json(name = "href") val href: String,
  @Json(name = "height") val height: Int?,
  @Json(name = "width") val width: Int?,
)

data class SpotifyCategory(
  @Json(name = "href") val href: String,
  @Json(name = "icons") val icons: List<SpotifyImage>,
  @Json(name = "id") val id: String,
  @Json(name = "name") val name: String,
)

