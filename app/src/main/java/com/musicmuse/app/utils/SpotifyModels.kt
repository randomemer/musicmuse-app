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
  @Json(name = "url") val href: String,
  @Json(name = "height") val height: Int?,
  @Json(name = "width") val width: Int?,
)

data class SpotifyCategory(
  @Json(name = "href") val href: String,
  @Json(name = "icons") val icons: List<SpotifyImage>,
  @Json(name = "id") val id: String,
  @Json(name = "name") val name: String,
)

data class SpotifyCategoriesResponse(
  @Json(name = "categories") val categories: SpotifyPaginatedModel<SpotifyCategory>
)

data class SpotifyExternalUrls(@Json(name = "spotify") val spotify: String)

data class SpotifySimplifiedPlaylist(
  @Json(name = "collaborative") val collaborative: Boolean,
  @Json(name = "description") val description: String?,
  @Json(name = "external_urls") val externalUrls: SpotifyExternalUrls,
  @Json(name = "href") val href: String,
  @Json(name = "id") val id: String,
  @Json(name = "images") val images: List<SpotifyImage>,
  @Json(name = "name") val name: String,
  @Json(name = "public") val public: Boolean?,
  @Json(name = "snapshot_id") val snapshotId: String,
  @Json(name = "type") val type: String,
  @Json(name = "uri") val uri: String
)

data class SpotifyCategoryPlaylistsResponse(
  @Json(name = "message") val message: String?,
  @Json(name = "playlists") val playlists: SpotifyPaginatedModel<SpotifySimplifiedPlaylist>
)
