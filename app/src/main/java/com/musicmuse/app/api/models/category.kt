package com.musicmuse.app.api.models

import com.squareup.moshi.Json

data class SpotifyCategory(
  @Json(name = "href") val href: String,
  @Json(name = "icons") val icons: List<SpotifyImage>,
  @Json(name = "id") val id: String,
  @Json(name = "name") val name: String,
)

data class SpotifyCategoriesResponse(
  @Json(name = "categories") val categories: SpotifyPaginatedModel<SpotifyCategory>
)
