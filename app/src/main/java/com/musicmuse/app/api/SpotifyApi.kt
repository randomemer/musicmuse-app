package com.musicmuse.app.api

import com.musicmuse.app.api.models.SpotifyCategoriesResponse
import com.musicmuse.app.api.models.SpotifyCategoryPlaylistsResponse
import com.musicmuse.app.api.models.SpotifyPlaylistResponse
import com.musicmuse.app.api.models.SpotifyTokenResponse
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
  suspend fun getCategories(): SpotifyCategoriesResponse

  @GET("browse/categories/{id}/playlists")
  suspend fun getCategoryPlaylists(@Path("id") id: String): SpotifyCategoryPlaylistsResponse

  @GET("playlists/{id}")
  suspend fun getPlaylist(@Path("id") id: String): SpotifyPlaylistResponse
}