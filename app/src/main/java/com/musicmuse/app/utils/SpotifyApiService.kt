package com.musicmuse.app.utils

import android.util.Base64
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


public class SpotifyAuthApiService {
  private val api: SpotifyAuthApi by lazy {
    createSpotifyAuthApi()
  }

  private fun createSpotifyAuthApi(): SpotifyAuthApi {
    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    val retrofit = Retrofit.Builder().baseUrl("https://accounts.spotify.com")
      .addConverterFactory(MoshiConverterFactory.create(moshi)).build()

    return retrofit.create(SpotifyAuthApi::class.java)
  }

  suspend fun getAuthToken(clientId: String, clientSecret: String): SpotifyTokenResponse {
    val originalString = "$clientId:$clientSecret"
    val encodedString = Base64.encodeToString(originalString.toByteArray(), Base64.NO_WRAP)

    val headers =
      mapOf(
        "Authorization" to "Basic $encodedString",
        "Content-Type" to "application/x-www-form-urlencoded"
      )
    val form = mapOf("grant_type" to "client_credentials")

    return api.getAuthToken(headers, form)
  }
}

class SpotifyApiService {
}
