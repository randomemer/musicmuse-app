package com.musicmuse.app.utils

import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.HeaderMap
import retrofit2.http.POST

interface SpotifyAuthApi {
  @POST("api/token")
  @FormUrlEncoded
  suspend fun getAuthToken(
    @HeaderMap headers: Map<String, String>,
    @FieldMap body: Map<String, String>
  ): SpotifyTokenResponse
}

interface SpotifyApi {}
