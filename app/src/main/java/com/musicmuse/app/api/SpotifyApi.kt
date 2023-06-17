package com.musicmuse.app.api

import com.musicmuse.app.api.models.*
import retrofit2.http.*
import java.util.*

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
    @Query("limit") limit: Int = 50,
    @Query("offset") offset: Int = 0,
  ): SpotifyCategoriesResponse

  @GET("browse/categories/{id}/playlists")
  suspend fun getCategoryPlaylists(
    @Path("id") id: String,
    @Query("limit") limit: Int = 50
  ): SpotifyCategoryPlaylistsResponse

  @GET("playlists/{id}")
  suspend fun getPlaylist(@Path("id") id: String): SpotifyPlaylistResponse

  @GET("search")
  suspend fun search(
    @Query("q") query: String,
    @Query("type") type: String = "track,playlist",
    @Query("limit") limit: Int = 50
  ): SpotifySearchResponse
}
