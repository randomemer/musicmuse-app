package com.musicmuse.app.api.models

import com.squareup.moshi.Json

data class SpotifyTokenResponse(
  @Json(name = "access_token") val accessToken: String,
  @Json(name = "expires_in") val expiresIn: Int,
  @Json(name = "token_type") val tokenType: String,
)

data class SpotifyExternalUrls(@Json(name = "spotify") val spotify: String)

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
  @Json(name = "url") val href: String,
  @Json(name = "height") val height: Int?,
  @Json(name = "width") val width: Int?,
)

open class SpotifyResource(
  @Json(name = "href") open val href: String,
  @Json(name = "id") open val id: String,
  @Json(name = "type") open val type: String,
  @Json(name = "uri") open val uri: String
)
