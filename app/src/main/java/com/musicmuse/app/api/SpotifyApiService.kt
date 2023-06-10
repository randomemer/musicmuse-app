package com.musicmuse.app.api

import android.util.Base64
import com.musicmuse.app.api.models.SpotifyEpisode
import com.musicmuse.app.api.models.SpotifyResource
import com.musicmuse.app.api.models.SpotifyTokenResponse
import com.musicmuse.app.api.models.SpotifyTrack
import com.musicmuse.app.utils.GlobalData
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.runBlocking
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class SpotifyAuthApiService {
  private val api: SpotifyAuthApi by lazy {
    createSpotifyAuthApi()
  }

  private fun createSpotifyAuthApi(): SpotifyAuthApi {
    val moshi = Moshi.Builder()
      .add(KotlinJsonAdapterFactory())
      .build()

    val retrofit = Retrofit.Builder().baseUrl("https://accounts.spotify.com")
      .addConverterFactory(MoshiConverterFactory.create(moshi)).build()

    return retrofit.create(SpotifyAuthApi::class.java)
  }

  suspend fun getAuthToken(): SpotifyTokenResponse {
    val clientId = GlobalData.get<String>("client_id")
    val clientSecret = GlobalData.get<String>("client_secret")

    val originalString = "$clientId:$clientSecret"
    val encodedString =
      Base64.encodeToString(originalString.toByteArray(), Base64.NO_WRAP)

    val headers =
      mapOf(
        "Authorization" to "Basic $encodedString",
        "Content-Type" to "application/x-www-form-urlencoded"
      )
    val form = mapOf("grant_type" to "client_credentials")

    return api.getAuthToken(headers, form)
  }
}

class SpotifyApiInterceptor : Interceptor {
  override fun intercept(chain: Interceptor.Chain): Response = chain.run {
    val token = GlobalData.get<String>("spotify_token")
    proceed(
      request().newBuilder().addHeader("Authorization", "Bearer $token").build()
    )
  }
}

class SpotifyApiAuthenticator : Authenticator {
  override fun authenticate(route: Route?, response: Response): Request? {
    var newRequest: Request?

    runBlocking {
      val tokenResp = SpotifyAuthApiService().getAuthToken()
      GlobalData.put("spotify_token", tokenResp.accessToken)
      newRequest = response.request.newBuilder()
        .header("Authorization", "Bearer ${tokenResp.accessToken}")
        .build()
    }

    return newRequest
  }
}

class SpotifyApiService {
  private val _api: SpotifyApi by lazy {
    createSpotifyApi()
  }

  val api: SpotifyApi get() = _api

  private fun createSpotifyApi(): SpotifyApi {
    val trackAdapterFactory =
      PolymorphicJsonAdapterFactory.of(SpotifyResource::class.java, "type")
        .withSubtype(SpotifyTrack::class.java, "track")
        .withSubtype(SpotifyEpisode::class.java, "episode")

    val moshi = Moshi.Builder()
      .add(trackAdapterFactory)
      .add(KotlinJsonAdapterFactory())
      .build()

    val client = OkHttpClient.Builder()
      .addInterceptor(SpotifyApiInterceptor())
      .authenticator(SpotifyApiAuthenticator())
      .build()

    val retrofit = Retrofit.Builder()
      .baseUrl("https://api.spotify.com/v1/")
      .client(client)
      .addConverterFactory(MoshiConverterFactory.create(moshi))
      .build()

    return retrofit.create(SpotifyApi::class.java)
  }
}
