package com.musicmuse.app.utils

import retrofit2.http.*

interface SpotifyAuthApi {
  @POST("api/token")
  @FormUrlEncoded
  suspend fun getAuthToken(
    @HeaderMap headers: Map<String, String>,
    @FieldMap body: Map<String, String>
  ): SpotifyTokenResponse
}

interface SpotifyApi {
  @GET("browse/categories")
  suspend fun getCategories(
    @Header("Authorization") authorization: String
  ): SpotifyPaginatedModel<SpotifyCategory>
}
